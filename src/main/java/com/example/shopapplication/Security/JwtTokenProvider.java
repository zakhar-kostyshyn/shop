package com.example.shopapplication.Security;

import com.example.shopapplication.Exceptions.InvalidTokenException;
import com.example.shopapplication.Security.Services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static com.example.shopapplication.Security.SecurityConstants.AUTHORITIES_KEY;
import static com.example.shopapplication.Security.SecurityConstants.EXPIRATION_TIME;
import static com.example.shopapplication.Security.SecurityConstants.SECRET;


@Component
public class JwtTokenProvider {

    //Generate the token

    public String generateToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userUsername = userPrincipal.getUsername();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userUsername)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .claim(AUTHORITIES_KEY, authorities)
                .compact();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        }catch(SignatureException ex){
            //System.out.println("Invalid Jwt Signature");
            throw new InvalidTokenException("Invalid Jwt Signature");
        }catch(MalformedJwtException ex){
            //System.out.println("Invalid Jwt token");
            throw new InvalidTokenException("Invalid JWT toke");
        }catch(ExpiredJwtException ex){
            //System.out.println("Expired jwt token");
            throw new InvalidTokenException("Expired jwt token");
        }catch(UnsupportedJwtException ex){
            //System.out.println("Unsupported Jwt token");
            throw new InvalidTokenException("Unsupported Jwt token");
        }catch(IllegalArgumentException ex){
            //System.out.println("Jwt claims string is empty");
            throw new InvalidTokenException("Jwt claims string is empty");
        }
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}
