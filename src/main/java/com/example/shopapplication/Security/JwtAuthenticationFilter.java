package com.example.shopapplication.Security;

import com.example.shopapplication.Security.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.shopapplication.Security.SecurityConstants.HEADER_STRING;
import static com.example.shopapplication.Security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(httpServletRequest);
            if(jwt != null && jwtTokenProvider.validateJwtToken(jwt)){
                String username = jwtTokenProvider.getUsernameFromToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch(Exception ex){
            System.out.println("cannot set user authentication");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader(HEADER_STRING);

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)){
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
