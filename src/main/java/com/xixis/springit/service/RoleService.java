package com.xixis.springit.service;

import com.xixis.springit.domain.Link;
import com.xixis.springit.domain.Role;
import com.xixis.springit.repository.LinkRepository;
import com.xixis.springit.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
  }

  public Role findByName(String name) {

    return roleRepository.findByName(name);

  }



}
