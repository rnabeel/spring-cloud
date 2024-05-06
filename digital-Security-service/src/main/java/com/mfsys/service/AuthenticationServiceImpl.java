package com.mfsys.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsys.comm.constant.EurekaRegisteredMicroServicesURI;
import com.mfsys.comm.util.DateTimeUtil;
import com.mfsys.config.JwtService;
import com.mfsys.dto.*;
import com.mfsys.exception.ApplicationPendingException;
import com.mfsys.exception.PasswordIncorrectException;
import com.mfsys.exception.UserAlreadyRegisteredException;
import com.mfsys.exception.UserNotFoundException;
import com.mfsys.repository.UserRepository;
import com.mfsys.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final WebclientUcoService webclientUcoService;



    public AuthenticationServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserServiceImpl userService, WebclientUcoService webclientUcoService) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.webclientUcoService = webclientUcoService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        var optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isPending()) {
                user = createUserFromRequest(request);
                userRepository.save(user);
                sendOtpToUser(request);
                throw new ApplicationPendingException();
            } else {
                throw new UserAlreadyRegisteredException(request.getPorOrgacode());
            }
        }
        sendOtpToUser(request);
        User newUser = createUserFromRequest(request);
        userRepository.save(newUser);
    }

    private void sendOtpToUser(RegisterRequest request) {
        OtpRequest otpRequest = OtpRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .userRole(request.getUserRole())
                .channelCode(request.getChannelCode())
                .porOrgacode(request.getPorOrgacode())
                .pinType("REG")
                .build();
        userService.sendOtp(otpRequest);
    }

    private User createUserFromRequest(RegisterRequest request) {
        return User.builder()
                .porOrgacode(request.getPorOrgacode())
                .phone(request.getPhone())
                .userName(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getUserRole())
                .previousPasswords(null)
                .pending(true)
                .build();
    }


    @Override
    public void authenticateAndSendLoginOTP(AuthenticationRequest request) {
        var user = findUserByEmail(request.getPorOrgacode(), request.getEmail());
        authenticateUser(request.getPorOrgacode(), request.getEmail(), request.getPassword());
        userService.sendOtp(OtpRequest.builder().username(user.getUsername()).email(request.getEmail())
                .phone(user.getPhone()).userRole(user.getRole()).channelCode(request.getChannelCode()).porOrgacode(request.getPorOrgacode())
                .pinType("LOGIN")
                .isOtpRequired(request.isOtpRequired())
                .build());
    }

    @Override
    public AuthenticationResponse authenticate(VerifyLoginOtpRequest request) {
        findUserByEmail(request.getPorOrgacode(), request.getEmail());
        authenticateUser(request.getPorOrgacode(), request.getEmail(), request.getPassword());
        userService.verifyLoginOtp(request);
        var user = findUserByEmail(request.getPorOrgacode(), request.getEmail());
        var jwtToken = jwtService.generateToken(user, false);
        var refreshToken = jwtService.generateRefreshToken(user);
       Map customerData =  fetchCustomerData(request.getPorOrgacode(), request.getEmail());
       user.setLastLogin(DateTimeUtil.ucolocalDateAndTime());
       userRepository.save(user);
        return AuthenticationResponse.builder().jwtToken(jwtToken).cmpCustcode(String.valueOf(customerData.get("cmpCustcode"))).isKycVerified((Boolean) customerData.get("cmpIsKycVerified")).cmpCustlastlogin(user.getLastLogin())
                .name(String.valueOf(customerData.get("cmpName"))).userRole(user.getRole())
                .cmpCuststatus("active")
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user, false);
                var authResponse = RefreshToken.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public User findUserByEmail(String porOrgacode, String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(porOrgacode));
    }

    @Override
    public void authenticateUser(String porOrgacode, String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            if (e.getMessage().equals("Bad credentials")) {
                throw new PasswordIncorrectException(email);
            }
        }
    }

    public Map fetchCustomerData(String porOrgacode, String email){
        String url= "/uco/fetchlogindata?porOrgacode="+porOrgacode+"&email="+email;
       return (Map) webclientUcoService.getCustomeProfiledataOnEmail(url,porOrgacode);

    }

}
