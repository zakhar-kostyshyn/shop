package com.example.shopapplication.Model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users",
       uniqueConstraints = {
       @UniqueConstraint(columnNames = "username"),
       @UniqueConstraint(columnNames = "password")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field 'first_name' must be filled")
    private String firstName;

    @NotBlank(message = "Field 'last_name' must be filled")
    private String lastName;

    @NotBlank(message = "Field 'username' must be filled")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "Field 'email' must be filled")
    @Size(max = 40)
    @Email(message = "This is not email, please enter correct data")
    private String email;

    @NotBlank(message = "Field 'password' must be filled")
    @Size(min = 8,max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(){}

    public User(String username,String firstName, String lastName,String email,String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
