package com.example.shopapplication.Security.Services;

import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.*;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String image;

    @JsonIgnore
    private ShoppingCart shoppingCart;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName, String username, String email, String image,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.image = image;
    }

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        byte[] encoded = null;

        String image = "";

        if(user.getImage() != null) {
            byte[] bytes = new byte[user.getImage().length];

            int count = 0;

            for (byte b : user.getImage()) bytes[count++] = b;

            encoded = Base64.getEncoder().encode(bytes);

            image = new String(encoded);
        }

        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                image,
                user.getPassword(),
                authorities);
    }

    public static File convertToFile(Byte[] bytes){
        File file = new File("");
        try{
            OutputStream os = new FileOutputStream(file);
            os.write(bytes.length);
            os.close();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getImage(){return image;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }
}
