package com.xixis.springit.service;

import com.xixis.springit.domain.User;
import com.xixis.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;
  private RoleService roleService;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final MailService mailService;

  public UserService(UserRepository userRepository, RoleService roleService, MailService mailService){
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.mailService = mailService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    if(!user.isPresent()){
      throw  new UsernameNotFoundException(email);
    }
    return user.get();

  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);

  }

  public User register(User user){

    // encrypt and save password
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

    // Add role to user
    user.addRole(roleService.findByName("ROLE_ADMIN"));

    // Disable user
    user.setEnabled(false);

    // Set activation code
    user.setActivationCode(UUID.randomUUID().toString());

    //save user
    save(user);

    //send activation email
    sendActivationEmail(user);

    return user;

  }

  public User save(User user){
    return userRepository.save(user);
  }

  public List<User> listAll(){
    return userRepository.findAll();
  }

  @Transactional
  public void saveUser(User... users){
    for(User user: users){
      userRepository.save(user);
    }
  }

  public void sendActivationEmail(User user){
    mailService.sendActivationEmail(user);
  }

  public void sendWelcomeemail(User user){
    mailService.sendWelcomeEmail(user);
  }

  public Optional<User> findByEmailAndActivationCode(String email, String activationCode){
    return userRepository.findByEmailAndActivationCode(email, activationCode);
  }
}
