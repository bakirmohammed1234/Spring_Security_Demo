package com.bakir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  JwtService jwtService;
    @PostMapping("/encodePassword")
    public void saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password, @RequestParam Role role) {
         UserEntity user = new UserEntity();
         user.setUsername(username);
         user.setPassword(passwordEncoder.encode(password));
         user.setIsActive(true);
         user.setRole(role);
         userRepository.save(user);

    }

    @PostMapping("/authenticate")
    public  String authenticate(@RequestBody AuthRequest authRequest) {

      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              authRequest.getUsername(),
              authRequest.getPassword()));

      if (authentication.isAuthenticated()) {
          String role =
                  authentication.getAuthorities()
                          .iterator()
                          .next().getAuthority()
                          .replace("ROLE_", "");

           return jwtService.generateToken(authRequest.getUsername(),role);
      }
      return null;




    }

}
