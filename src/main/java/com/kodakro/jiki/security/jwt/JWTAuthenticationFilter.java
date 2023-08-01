package com.kodakro.jiki.security.jwt;

import static com.kodakro.jiki.security.SecurityConstants.EXPIRATION_TIME;
import static com.kodakro.jiki.security.SecurityConstants.HEADER_STRING;
import static com.kodakro.jiki.security.SecurityConstants.SECRET;
import static com.kodakro.jiki.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodakro.jiki.model.HttpAuthResponse;
import com.kodakro.jiki.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
    AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	User user = (User) auth.getPrincipal();
        final Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    	String token = Jwts.builder()
                 .setSubject((user).getUsername()+ '-'+(user).getEmail())
                 .setExpiration(expirationDate)
                 .signWith(SignatureAlgorithm.HS512, SECRET)
                 .compact();
    	 
    	 response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
         
    	 response.setContentType("application/json");
    	 response.setCharacterEncoding("UTF-8");
    	 response.getWriter().write(new HttpAuthResponse(true, token, user).toString());
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
    		AuthenticationException failed) throws IOException, ServletException {
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(new HttpAuthResponse(false, "", null).toString());
    }
    
    
    
}