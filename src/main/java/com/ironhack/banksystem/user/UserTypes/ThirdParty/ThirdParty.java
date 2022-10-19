package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import com.ironhack.banksystem.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty extends User {
    private String hashedKey;

}
