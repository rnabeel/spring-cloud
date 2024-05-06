package com.mfsys.uco.repository;

import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.CustomerProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, CustomerProfileId> {
    @Query("SELECT c FROM BN_CS_MP_CUSTOMERPROFILE c WHERE c.porOrgacode =:porOrgacode and c.padAdrsmobphone = :phone")
    CustomerProfile findProfilesByMobilePhone(String porOrgacode,String phone);
    @Query("SELECT c FROM BN_CS_MP_CUSTOMERPROFILE c WHERE c.porOrgacode =:porOrgacode and c.cmpEmail = :email")
    CustomerProfile findbyEmail(String porOrgacode, String email);
}
