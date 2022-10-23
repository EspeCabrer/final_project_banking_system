package com.ironhack.banksystem.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.banksystem.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumRole name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    public Role(EnumRole role, Set<User> users) {
        this.name = role;
        this.users = users;
    }

    public Role(EnumRole role) {
        this.name = role;
    }
}
