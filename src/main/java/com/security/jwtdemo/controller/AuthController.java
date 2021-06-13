package com.security.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.security.jwtdemo.model.AuthenticationRequest;
import com.security.jwtdemo.model.AuthenticationResponse;
import com.security.jwtdemo.service.MyUserDetailsService;
import com.security.jwtdemo.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request) {

    try {
      // auth manager will call userdetails to auth
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

      // user authenticated
      // fetch username
      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

      String jwt = JwtUtil.generateToken(userDetails);
      return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

  }

}
