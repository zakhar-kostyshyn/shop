package com.example.shopapplication.Services;

import com.example.shopapplication.Model.ERole;
import com.example.shopapplication.Model.Role;
import com.example.shopapplication.Model.User;
import com.example.shopapplication.Payload.Request.LoginRequest;
import com.example.shopapplication.Payload.Request.SignupRequest;
import com.example.shopapplication.Payload.Response.JwtResponse;
import com.example.shopapplication.Repositories.RoleRepository;
import com.example.shopapplication.Repositories.UserRepository;
import com.example.shopapplication.Security.JwtTokenProvider;
import com.example.shopapplication.Security.Services.UserDetailsImpl;
import com.example.shopapplication.Security.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.shopapplication.Security.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<?> createNewUser(SignupRequest signupRequest){

        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return new ResponseEntity<String>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<String>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        // Create new User's account

        User newUser = new User(signupRequest.getUsername(),
                                signupRequest.getFirstName(),
                                signupRequest.getLastName(),
                                signupRequest.getEmail(),
                                bCryptPasswordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);

        return new ResponseEntity<String>("User registered successfully", HttpStatus.OK);
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> ((GrantedAuthority) item).getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public UserDetails loadUser(String jwtToken){

        if(StringUtils.hasText(jwtToken) && jwtToken.startsWith(TOKEN_PREFIX)){
            jwtToken = jwtToken.substring(7, jwtToken.length());
        }

        if(!jwtTokenProvider.validateJwtToken(jwtToken)) return null;


        String username = jwtTokenProvider.getUsernameFromToken(jwtToken);

        UserDetails user = userDetailsService.loadUserByUsername(username);

        return user;
    }

    public Iterable<User> findAllUsers(){return userRepository.findAll();}
}
