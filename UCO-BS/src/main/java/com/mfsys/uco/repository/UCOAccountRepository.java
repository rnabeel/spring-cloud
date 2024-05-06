package com.mfsys.uco.repository;

import com.mfsys.uco.model.AccountId;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.CustomerProfileId;
import com.mfsys.uco.model.UcoAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UCOAccountRepository extends JpaRepository<UcoAccount, AccountId> {

    @Query("SELECT c FROM BN_MS_UC_UCOACCOUNT c WHERE c.id.porOrgacode =:porOrgacode and c.cmpCustcode = :cmpCustcode")
    UcoAccount findUcoAccountByCmpCustcode(String porOrgacode,String cmpCustcode);
}
