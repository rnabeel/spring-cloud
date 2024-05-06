package com.mfsys.uco.service;

import com.mfsys.uco.dto.webclientdto.AccountDetail;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.repository.CustomerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerProfileService {
    private final CustomerProfileRepository customerProfileRepository;
    public CustomerProfile fetchCustcodeBasedOnEmail(String porOrgacode, String email) {
       return customerProfileRepository.findbyEmail(porOrgacode,email);
    }
}
