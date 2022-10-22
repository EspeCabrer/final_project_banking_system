package com.ironhack.banksystem.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.banksystem.user.UserEntity;
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
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumRole name;
    //private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserEntity> users = new HashSet<>();


    public RoleEntity(EnumRole role, Set<UserEntity> users) {
        this.name = role;
        this.users = users;
    }

    public RoleEntity(EnumRole role) {
        this.name = role;
    }
}
