package com.xixis.springit.controller;

import com.xixis.springit.config.JWTTokenUtil;
import com.xixis.springit.domain.JWTRequest;
import com.xixis.springit.domain.JWTResponse;
import com.xixis.springit.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JWTAuthenticationController {

  private AuthenticationManager authenticationManager;
  private JWTTokenUtil jWTTokenUtil;
  private UserDetailsServiceImpl userDetailsServiceImpl;

  public JWTAuthenticationController(AuthenticationManager authenticationManager,
                                     JWTTokenUtil jWTTokenUtil,
                                     UserDetailsServiceImpl userDetailsServiceImpl){
    this.authenticationManager = authenticationManager;
    this.jWTTokenUtil = jWTTokenUtil;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {

    return authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword(), authenticationRequest);

  }

  private ResponseEntity<?> authenticate(String username, String password, JWTRequest jWTRequest) {
    try {

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jWTRequest.getUsername());
      final String token = jWTTokenUtil.generateToken(userDetails);
      return ResponseEntity.ok(new JWTResponse(token, "Login successful", ""));

    } catch(UsernameNotFoundException e){

      return new ResponseEntity<>(new JWTResponse(e.getMessage(), "User does not exist"), HttpStatus.NOT_FOUND);

    } catch (DisabledException e) {

      // throw new Exception("USER_DISABLED", e);
      return new ResponseEntity<>(new JWTResponse(e.getMessage(), "User account is disabled"), HttpStatus.LOCKED);

    } catch (BadCredentialsException e) {

      // throw new Exception("INVALID_CREDENTIALS", e);
      return new ResponseEntity<>(new JWTResponse(e.getMessage(), "Wrong credentials provided"), HttpStatus.UNPROCESSABLE_ENTITY);

    }

  }

}

