package com.ironhack.demo.User;

import com.ironhack.demo.Address.Address;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Optional;

@Entity
public class AccountHolder extends User {
    private Date dateBirth;
    @Embedded
    private Address primaryAddress;
    @Embedded
    private Optional<Address> mailingAddress;
}
