package com.ironhack.banksystem.user.userTypes.admin;

import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.user.User;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin(String username, String password, Role role) {
        super(username, password, role);
    }
    public Admin() {
    }
}
