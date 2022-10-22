package com.ironhack.banksystem.user.userTypes.admin;

import com.ironhack.banksystem.role.RoleEntity;
import com.ironhack.banksystem.user.UserEntity;

import javax.persistence.Entity;

@Entity

public class AdminEntity extends UserEntity {
    public AdminEntity(String username, String password, RoleEntity role) {
        super(username, password, role);
    }

    public AdminEntity() {
    }
}
