package com.ironhack.banksystem.user.userTypes.accountHolder;

import com.ironhack.banksystem.account.AccountEntity;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.role.RoleEntity;
import com.ironhack.banksystem.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends UserEntity {

    @Column(nullable = false)
    private LocalDate dateBirth;

    @Embedded
    @Column(nullable = false)
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "postAddress", column = @Column(name = "post_address_mailing")),
            @AttributeOverride(name = "city", column = @Column(name = "city_mailing")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code_mailing"))
    })
    private Address mailingAddress;

    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private Set<AccountEntity> accountsByFirstOwner;

    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    private Set<AccountEntity> accountsBySecondOwner;


    public AccountHolder(String username, String password, LocalDate dateBirth, Address primaryAddress, Address mailingAddress, RoleEntity role) {
        super(username, password, role);
        this.dateBirth = dateBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }


}
