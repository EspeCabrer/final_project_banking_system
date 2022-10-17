package com.ironhack.demo.User.UserTypes;

import com.ironhack.demo.Address.Address;
import com.ironhack.demo.User.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder extends User {

    @Column(nullable = false)
    private Date dateBirth;

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
}
