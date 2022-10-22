package com.ironhack.banksystem.user.userTypes.accountHolder;

import com.ironhack.banksystem.address.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountHolderDTO {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "dateBirth is required")
    private String dateBirth;
    @NotNull(message = "primaryAddress is required")
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
