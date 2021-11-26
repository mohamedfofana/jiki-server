package com.kodakro.jiki.security.jwt;

import static com.kodakro.jiki.security.SecurityConstants.HEADER_STRING;
import static com.kodakro.jiki.security.SecurityConstants.SECRET;
import static com.kodakro.jiki.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    
    /**
     * This method reads the JWT from the Authorization header, and then uses Jwtsto validate the token. 
     * If everything is in place, we set the user in the SecurityContext and allow the request to move on.
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
        	try {
	            String user = Jwts.parser()
	                    .setSigningKey(SECRET)
	                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
	                    .getBody()
	                    .getSubject();
	            if (user != null) {
	                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	            }
        	} catch(ExpiredJwtException e){
        		log.info("User session expired !");
        	}
            return null;
        }
        return null;
    }
}