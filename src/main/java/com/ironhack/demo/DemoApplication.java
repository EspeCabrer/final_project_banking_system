package com.ironhack.demo;

import com.ironhack.demo.Role.Role;
import com.ironhack.demo.User.User;
import com.ironhack.demo.Role.RoleRepository;
import com.ironhack.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//User user = userRepository.save(new User("jose",passwordEncoder.encode("1234")));
		//roleRepository.save(new Role("USER",user));

	}
}
