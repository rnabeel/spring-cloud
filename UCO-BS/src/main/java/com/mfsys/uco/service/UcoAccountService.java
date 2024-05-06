package com.mfsys.uco.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsys.uco.constants.UCOURI;
import com.mfsys.uco.dto.AccountInquiryResponse;
import com.mfsys.uco.dto.SignupStep3RequestModel;
import com.mfsys.uco.dto.webclientdto.AccountDetail;
import com.mfsys.uco.exception.AccountDoesntExistsException;
import com.mfsys.uco.exception.UserAlreadyRegisteredException;
import com.mfsys.uco.model.AccountId;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.UcoAccount;
import com.mfsys.uco.repository.CustomerProfileRepository;
import com.mfsys.uco.repository.UCOAccountRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class UcoAccountService {

    private final CustomerProfileRepository customerProfileRepository;
    private final UCOAccountRepository ucoAccountRepository;
    private final CustomerProfileService customerProfileService;

    private final WebClientDepositService webClientDeposit;
    private final WebClientCrmService webClientCrmService;

    public UcoAccountService(CustomerProfileRepository customerProfileRepository, UCOAccountRepository ucoAccountRepository, CustomerProfileService customerProfileService, WebClientDepositService webClientDeposit, WebClientCrmService webClientCrmService) {
        this.customerProfileRepository = customerProfileRepository;
        this.ucoAccountRepository = ucoAccountRepository;
        this.customerProfileService = customerProfileService;
        this.webClientDeposit = webClientDeposit;
        this.webClientCrmService = webClientCrmService;
    }

    public AccountInquiryResponse fetchAccountTitile(String porOrgacode, String acntTypeCode, String acntTypeValue){
        if(acntTypeCode.equals("01")){
            CustomerProfile customerProfile = customerProfileRepository.findProfilesByMobilePhone(porOrgacode,acntTypeValue);
            if(Objects.isNull(customerProfile)){
                throw new AccountDoesntExistsException();
            }
            UcoAccount ucoAccount = ucoAccountRepository.findUcoAccountByCmpCustcode(porOrgacode,customerProfile.getCmpCustcode());
            return AccountInquiryResponse.builder().mbmBkmsnumber(ucoAccount.getId().getMbmBkmsnumber()).mbmBkmstitle(ucoAccount.getMbmBkmstitle()).build();
        }
        return null;
    }


    public Double fetchAccountBalance(String porOrgacode, String mbmBkmsNumber) {
        return (Double) getIndvAccountDetails(porOrgacode,mbmBkmsNumber);
    }
    public Object getIndvAccountDetails(String porOrgacode, String mbmBkmsnumber) {
        String url= UCOURI.GET_UCOACC_BALANCE+"?porOrgacode="+porOrgacode+"&mbmBkmsnumber="+mbmBkmsnumber;
        return webClientDeposit.getUcoAccountBalance(url,porOrgacode);
    }


     public void onBoardCustomer(SignupStep3RequestModel signupStep3RequestModel) {
        if(Objects.nonNull(customerProfileService.fetchCustcodeBasedOnEmail(signupStep3RequestModel.getPorOrgacode(),signupStep3RequestModel.getEmail()))){
            throw new UserAlreadyRegisteredException(null);
        }

        String porOrgacode = signupStep3RequestModel.getPorOrgacode();
            Map cmpCustcodeReturn = (Map) webClientCrmService.onboardCustomer(Map.of("CMP_FULLNAME",signupStep3RequestModel.getName()
                    ,"PIT_IDENCODE",signupStep3RequestModel.getIdentificationType(),"CIT_IDENVALUE",signupStep3RequestModel.getIdentificationNumber()
                    ,"PAD_ADRSMOBPHONE",signupStep3RequestModel.getPhone(),"POR_ORGACODE",signupStep3RequestModel.getPorOrgacode(),"SUS_USERCODE","01",
                    "PLC_LOCACODE","2003"), UCOURI.CUSTOMER_ONBOARDING,signupStep3RequestModel.getPorOrgacode());
            String cmpCustcode = String.valueOf(cmpCustcodeReturn.get("cmpCustcode"));
        AccountDetail accountDetail = fetchdepositAccountFromCiihive(porOrgacode,cmpCustcode);
        CustomerProfile customerProfile = CustomerProfile.builder().cmpCustcode(accountDetail.getCmpCustcode()).cmpEmail(signupStep3RequestModel.getEmail())
                .cmpName(signupStep3RequestModel.getName()).cmpIsKycVerified(signupStep3RequestModel.isKycAdded())
                .pitIdencode(signupStep3RequestModel.getIdentificationType()).pitIdenvalue(signupStep3RequestModel.getIdentificationNumber())
                .cmpUserName(signupStep3RequestModel.getName())
                .padAdrsmobphone(signupStep3RequestModel.getPhone())
                .cmpUserName(signupStep3RequestModel.getUsername()).porOrgacode(signupStep3RequestModel.getPorOrgacode())
                .build();
        customerProfileRepository.save(customerProfile);
        UcoAccount ucoAccount = UcoAccount.builder()
                 .id(new AccountId(accountDetail.getPorOrgacode(),accountDetail.getMbmBkmsnumber())) // Set the AccountId, assuming a method exists to create or retrieve it
                 .dmpProdcode(accountDetail.getDmpProdcode())
                 .mbmBkmstitle(accountDetail.getMbmBkmstitle())
                 .pcrCurrdesc(accountDetail.getPcrCurrdesc())
                 .cmpCustcode(accountDetail.getCmpCustcode())
                 .pcrCurrcode(accountDetail.getPcrCurrcode())
                 .mbmBkmsclosed(accountDetail.isMbmBkmsclosed())
                 .mbmBkmsopendate(LocalDate.now())
                 .sgtLasttrandate(LocalDate.now())
                         .build();
        ucoAccountRepository.save(ucoAccount);
        }


    public AccountDetail fetchdepositAccountFromCiihive(String porOrgacode,String cmpCustcode){
        String url= UCOURI.GET_CMP_UCOACCOUNTS+"?porOrgacode="+porOrgacode+"&cmpCustcode="+cmpCustcode;
       List<Object> map = (List<Object>) webClientDeposit.getCmpUcoAccounts(url,porOrgacode);
       ObjectMapper objectMapper = new ObjectMapper();

       return objectMapper.convertValue(map.get(0), AccountDetail.class);

    }

    public Object fetchExchangeRate(String porOrgacode){
        String url= UCOURI.FETCH_EXCHANGE_RATE+"?porOrgacode="+porOrgacode;
       return webClientDeposit.fetchExchangeRate(url,porOrgacode);
    }
    }

