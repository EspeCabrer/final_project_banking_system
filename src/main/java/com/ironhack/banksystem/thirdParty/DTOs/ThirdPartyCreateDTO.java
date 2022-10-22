package com.ironhack.banksystem.thirdParty.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThirdPartyCreateDTO {
    @NotBlank
    private String name;
}
