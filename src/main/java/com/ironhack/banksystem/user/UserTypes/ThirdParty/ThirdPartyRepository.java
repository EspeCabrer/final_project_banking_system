package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long > {

    Optional<ThirdParty> findByHashedKey(String string);
    Optional<ThirdParty> findByName(String string);
}
