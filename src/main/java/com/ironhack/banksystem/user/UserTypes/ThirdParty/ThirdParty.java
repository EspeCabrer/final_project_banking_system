package com.ironhack.banksystem.user.UserTypes.ThirdParty;

import com.ironhack.banksystem.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThirdParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hashedKey;

    @Column(unique = true)
    private String name;

    public ThirdParty(String hashedKey, String name) {
        this.hashedKey = hashedKey;
        this.name = name;
    }
}
