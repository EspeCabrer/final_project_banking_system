package com.ironhack.demo.user.UserTypes.AccountHolder;

import com.ironhack.demo.address.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountHolderDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    @NotNull
    private String dateBirth;
    @NotNull
    private Address primaryAddress;

    private Address mailingAddress;

    public AccountHolderDTO(String username, String password, String dateBirth, Address primaryAddress, Address mailingAddress) {
        this.username = username;
        this.password = password;
        this.dateBirth = dateBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }




}
