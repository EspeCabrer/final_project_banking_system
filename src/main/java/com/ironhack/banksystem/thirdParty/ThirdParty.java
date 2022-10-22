package com.ironhack.banksystem.thirdParty;

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
public class ThirdPartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hashedKey;

    @Column(unique = true)
    private String name;

    public ThirdPartyEntity(String hashedKey, String name) {
        this.hashedKey = hashedKey;
        this.name = name;
    }
}
