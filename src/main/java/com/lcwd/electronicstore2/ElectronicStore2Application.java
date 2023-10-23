package com.lcwd.electronicstore2;

import com.lcwd.electronicstore2.entities.Role;
import com.lcwd.electronicstore2.repositories.RoleRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@SpringBootApplication()
@EnableWebMvc
public class ElectronicStore2Application implements CommandLineRunner {

	public static void main(String[] args){

		SpringApplication.run(ElectronicStore2Application.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepositories roleRepositories;

	@Value("${normal.role.id}")
	private String role_normal_id;

	@Value("${admin.role.id}")
	private String role_admin_id;


	@Override
	public void run(String... args) throws Exception{
		System.out.println(passwordEncoder.encode("13edd12"));

		try {

			Role role_Admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			Role role_Normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();

			roleRepositories.save(role_Admin);
			roleRepositories.save(role_Normal);

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
