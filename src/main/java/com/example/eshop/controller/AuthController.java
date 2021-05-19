package com.example.eshop.controller;

import com.example.eshop.model.user.Role;
import com.example.eshop.model.user.RoleName;
import com.example.eshop.model.user.User;
import com.example.eshop.payload.request.LoginRequest;
import com.example.eshop.payload.request.SignUpRequest;
import com.example.eshop.payload.response.ApiResponse;
import com.example.eshop.payload.response.JwtAuthenticationResponse;
import com.example.eshop.repository.RoleRepository;
import com.example.eshop.repository.UserRepository;
import com.example.eshop.security.JwtTokenProvider;
import com.example.eshop.security.UserPrincipal;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()
        )
    );

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    int id = Math.toIntExact(userPrincipal.getId());

    List<GrantedAuthority> authorities = (List<GrantedAuthority>) userPrincipal.getAuthorities();
    String role = authorities.get(0).getAuthority();

    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, id, role));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
          HttpStatus.BAD_REQUEST);
    }

    // Creating admin's account
    User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
        signUpRequest.getEmail(), signUpRequest.getPassword());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);

    if(!userRole.isPresent()){
      roleRepository.save(new Role(1, RoleName.ROLE_ADMIN));
      userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
    }

    user.setRoles(Collections.singleton(userRole.get()));

    User result = userRepository.save(user);

    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
  }


}
