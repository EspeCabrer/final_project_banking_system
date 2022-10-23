package com.ironhack.banksystem;

import com.ironhack.banksystem.account.accountTypes.savings.Savings;
import com.ironhack.banksystem.account.accountTypes.savings.SavingsRepository;
import com.ironhack.banksystem.address.Address;
import com.ironhack.banksystem.money.Money;
import com.ironhack.banksystem.role.EnumRole;
import com.ironhack.banksystem.role.Role;
import com.ironhack.banksystem.role.RoleRepository;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolder;
import com.ironhack.banksystem.user.userTypes.accountHolder.AccountHolderRepository;
import com.ironhack.banksystem.user.userTypes.admin.Admin;
import com.ironhack.banksystem.user.userTypes.admin.AdminRepository;
import com.ironhack.banksystem.thirdParty.ThirdParty;
import com.ironhack.banksystem.thirdParty.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	SavingsRepository savingsRepository;

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ThirdPartyRepository thirdPartyRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args)  {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		Address address = new Address("Roma n25", "Madrid", 06754);

		roleRepository.saveAll(List.of(new Role(EnumRole.ADMIN), new Role(EnumRole.ACCOUNT_HOLDER)));
		Role accountHolderRole = roleRepository.findByName(EnumRole.ACCOUNT_HOLDER).get();
		Role adminRole = roleRepository.findByName(EnumRole.ADMIN).get();
		adminRepository.save(new Admin("maria", passwordEncoder.encode("password"), adminRole ));
		AccountHolder user2 = accountHolderRepository.save(new AccountHolder("pepe", passwordEncoder.encode("password"), LocalDate.parse("1980-03-05"), address, null, accountHolderRole ));
		AccountHolder user3 = accountHolderRepository.save(new AccountHolder("antonia", passwordEncoder.encode("password"), LocalDate.parse("1980-03-05"), address, null, accountHolderRole ));
		savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(3000)), user2, null, null, null, passwordEncoder.encode("secretKey")));
		savingsRepository.save(new Savings(new Money(BigDecimal.valueOf(1500)), user3, null, null, null, passwordEncoder.encode("secretKey")));

		thirdPartyRepository.save(new ThirdParty(passwordEncoder.encode("thirdParty1"), "thirdParty1" ));
	}
}
