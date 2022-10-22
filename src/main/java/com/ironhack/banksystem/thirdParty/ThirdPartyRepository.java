package com.ironhack.banksystem.thirdParty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long > {

    Optional<ThirdParty> findByHashedKey(String string);
    Optional<ThirdParty> findByName(String string);
}
