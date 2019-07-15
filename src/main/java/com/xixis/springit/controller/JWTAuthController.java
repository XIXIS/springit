package com.xixis.springit.controller;

import com.xixis.springit.apiresponse.UserAPIResponse;
import com.xixis.springit.config.JWTTokenUtil;
import com.xixis.springit.domain.JWTRequest;
import com.xixis.springit.domain.JWTResponse;
import com.xixis.springit.domain.User;
import com.xixis.springit.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class JWTAuthController {

  private AuthenticationManager authenticationManager;
  private JWTTokenUtil jWTTokenUtil;
  private UserService userService;

  public JWTAuthController(AuthenticationManager authenticationManager,
                           JWTTokenUtil jWTTokenUtil,
                           UserService userService){
    this.authenticationManager = authenticationManager;
    this.jWTTokenUtil = jWTTokenUtil;
    this.userService = userService;
  }

  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {

    return authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword(), authenticationRequest);

  }


  @GetMapping("/activate/{email}/{activationCode}")
  public ResponseEntity<?> activateUser(@PathVariable String email, @PathVariable String activationCode){

    Optional<User> optionalUser = userService.findByEmailAndActivationCode(email, activationCode);
    if(optionalUser.isPresent()){
      User user = optionalUser.get();
      user.setEnabled(true);
      userService.save(user);
      userService.sendWelcomeemail(user);

      return new ResponseEntity<>(new UserAPIResponse("User successfully activated"), HttpStatus.OK);

    }

    return new ResponseEntity<>(
        new UserAPIResponse("Error activating user","User activation failed"),
        HttpStatus.OK
    );

  }


  private ResponseEntity<?> authenticate(String username, String password, JWTRequest jWTRequest) {
    try {

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      final UserDetails userDetails = userService.loadUserByUsername(jWTRequest.getUsername());
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

