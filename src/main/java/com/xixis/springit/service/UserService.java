package com.xixis.springit.service;

import com.xixis.springit.domain.*;
import com.xixis.springit.repository.UserRepository;
import com.xixis.springit.validators.LinkValidator;
import com.xixis.springit.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    if(!user.isPresent()){
      throw  new UsernameNotFoundException(email);
    }
    return user.get();

  }

  public User register(User user){

    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return userRepository.save(user);

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

}
