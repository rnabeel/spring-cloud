package com.mfsys.repository;

import com.mfsys.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    @Query("SELECT p FROM DG_PN_PIN p WHERE p.userName = :username AND  p.pinstatus = 'Unverified' AND p.pinExpirydate > CURRENT_TIMESTAMP  ORDER BY p.pinserial DESC LIMIT 1")
    Optional<Pin> findLatestActiveOtpByUserName(String username);

}
