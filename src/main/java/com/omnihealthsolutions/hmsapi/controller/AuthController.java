package com.omnihealthsolutions.hmsapi.controller;

import com.omnihealthsolutions.hmsapi.entity.Role;
import com.omnihealthsolutions.hmsapi.entity.User;
import com.omnihealthsolutions.hmsapi.payload.LoginDto;
import com.omnihealthsolutions.hmsapi.payload.signupDto;
import com.omnihealthsolutions.hmsapi.repository.RoleRepository;
import com.omnihealthsolutions.hmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
@PostMapping("/signin")
    public ResponseEntity<?> AuthenticateUser(@RequestBody LoginDto loginDto){
    Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authenticate);
    return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }
@PostMapping("/signup")
public ResponseEntity<?> registerUser(@RequestBody signupDto signup){
 if(userRepository.existsByUsername(signup.getUsername())){
return new ResponseEntity<>("username ALready exist",HttpStatus.BAD_REQUEST);


 }
 if(userRepository.existsByEmail(signup.getEmail())){
     return new ResponseEntity<>("Email ALready exist",HttpStatus.BAD_REQUEST);
 }

 User user = new User();
 user.setEmail(signup.getEmail());
user.setName(signup.getName());
user.setPassword(passwordEncoder.encode(signup.getPassword()));

    Role role = roleRepository.findByName("ROLE_ADMIN").get();
user.setRoles(Collections.singleton(role));
    User save = userRepository.save(user);
return new ResponseEntity<>("user registered successfully",HttpStatus.CREATED);

}
}
