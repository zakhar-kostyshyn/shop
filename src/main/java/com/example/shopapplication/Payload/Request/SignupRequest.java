package com.example.shopapplication.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

    @NotBlank(message = "Field 'username' must be filled")
    @Size(min = 3, max = 20, message = "username must be at least 3 characters long")
    private String username;

    @NotBlank(message = "Field 'email' must be filled")
    @Size(max = 50)
    @Email(message = "This is not email, please enter correct data")
    private String email;


    @NotBlank(message = "Field 'first_name' must be filled")
    private String firstName;

    @NotBlank(message = "Field 'last_name' must be filled")
    private String lastName;

    private Set<String> role;

    @NotBlank(message = "Field 'password' must be filled")
    @Size(min = 6, max = 40, message = "password must be at least 6 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
