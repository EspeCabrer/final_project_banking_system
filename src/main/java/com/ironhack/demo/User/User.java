package com.ironhack.demo.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.demo.Role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    //@ManyToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(name="Role",
    joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @JsonIgnore
    private List<Role> roles;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
