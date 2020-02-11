package com.example.shopapplication.Security;

import com.example.shopapplication.Exceptions.ForbiddenDeniedException;
import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ForbiddenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        ForbiddenDeniedException forbidden = new ForbiddenDeniedException();
        String jsonLoginResponse = new Gson().toJson(forbidden);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().print(jsonLoginResponse);

    }
}
