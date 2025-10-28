package com.ctrlaltthink.ai_resume_parser;

import com.ctrlaltthink.ai_resume_parser.entity.Role;
import com.ctrlaltthink.ai_resume_parser.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class AiResumeParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiResumeParserApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final RoleRepository roleRepository) {
		return args -> {
			final Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
			if (userRole.isEmpty()) {
				final Role role = new Role();
				role.setName("ROLE_USER");
				role.setCreatedBy("SYSTEM");
				roleRepository.save(role);
			}
		};
	}


}
