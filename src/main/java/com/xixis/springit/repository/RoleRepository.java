package com.xixis.springit.repository;

import com.xixis.springit.domain.Role;
import com.xixis.springit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
