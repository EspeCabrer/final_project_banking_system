package com.ironhack.banksystem.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.banksystem.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users;

    public Role(String role, Set<User> users) {
        this.role = role;
        this.users = users;
    }
}
