package com.example.shopapplication.Web;


import com.example.shopapplication.Model.User;
import com.example.shopapplication.Payload.Request.LoginRequest;
import com.example.shopapplication.Payload.Request.SignupRequest;
import com.example.shopapplication.Payload.Response.JwtResponse;
import com.example.shopapplication.Services.MapValidationErrorService;
import com.example.shopapplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if(errorMap != null) return errorMap;

        return userService.createNewUser(signupRequest);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        return new ResponseEntity<JwtResponse>(userService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/loadUser")
    public ResponseEntity<?> loadUser(@Valid @RequestHeader("authorization") String jwtToken){
        UserDetails user = userService.loadUser(jwtToken);

        if(user == null) return new ResponseEntity<String>("Invalid token", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> getAllUsers(){
        System.out.println("hello from function ADMIN");
        for(User user : userService.findAllUsers()){
            System.out.println(user.toString());
        }
        return userService.findAllUsers();
    }

    @PostMapping("/savePhoto")
    public ResponseEntity<String> savePhoto(@RequestParam("imageFile") MultipartFile multipartFile, Principal principal) throws IOException {

        userService.savePhoto(multipartFile, principal.getName());

        return new ResponseEntity<String>("Photo saved", HttpStatus.OK);
    }

    @PostMapping("/changeAccount")
    public ResponseEntity<?> changeAccount(@RequestBody Map<String, String> mapAccount, Principal principal) {

        return userService.changeAccount(mapAccount, principal.getName());
    }
}
