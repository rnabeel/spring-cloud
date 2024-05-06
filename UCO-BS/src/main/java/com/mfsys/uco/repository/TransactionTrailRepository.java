package com.mfsys.uco.repository;

import com.mfsys.uco.model.TransactionTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionTrailRepository extends JpaRepository<TransactionTrail,Integer> {

    @Query("SELECT t FROM BN_MS_UA_UCOACCOUNTTRAIL t WHERE t.porOrgacode =:porOrgacode and t.crMbmBkmsnumber = :crMbmBkmsnumber and t.batAcnttranReceived = false")
    Optional<List<TransactionTrail>> findByPorOrgacodeAndCrMbmBkmsnumberAndBatAcnttranReceivedFalse(String porOrgacode, String crMbmBkmsnumber);


//    @Query("SELECT t FROM BN_MS_UA_UCOACCOUNTTRAIL t WHERE t.porOrgacode =:porOrgacode and t.drMbmBkmsnumber = :mbmBkmsnumber or(t.crMbmBkmsnumber = :mbmBkmsnumber and t.batAcnttranReceived = false)")
    @Query(value = "SELECT * " +
            "FROM bn_ms_ua_ucoaccounttrail " +
            "WHERE (dr_mbm_bkmsnumber = :mbmBkmsnumber AND (bat_acnttransent = TRUE OR bat_acnttranreceived = TRUE)) " +
            " OR (cr_mbm_bkmsnumber = :mbmBkmsnumber  AND (bat_acnttransent = TRUE AND bat_acnttranreceived = TRUE))",nativeQuery = true)
    Optional<List<TransactionTrail>> fetchDepositAccountStatement(String mbmBkmsnumber);

}
