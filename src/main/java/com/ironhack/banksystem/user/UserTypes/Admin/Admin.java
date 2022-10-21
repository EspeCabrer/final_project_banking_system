package com.ironhack.banksystem.user.UserTypes.Admin;

import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.user.User;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin(String username, String password, Role role) {
        super(username, password, role);
    }
}
