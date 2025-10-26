package com.ctrlaltthink.ai_resume_parser.repository;

import com.ctrlaltthink.ai_resume_parser.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String roleUser);
}
