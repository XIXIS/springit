package com.xixis.springit.repository;

import com.xixis.springit.domain.Role;
import com.xixis.springit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

}