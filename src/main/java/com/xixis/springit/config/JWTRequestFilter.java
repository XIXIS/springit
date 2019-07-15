package com.xixis.springit.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xixis.springit.domain.JWTResponse;
import com.xixis.springit.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import io.jsonwebtoken.security.SignatureException;


@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  private UserService userService;
  private JWTTokenUtil jWTTokenUtil;

  public JWTRequestFilter(UserService userService, JWTTokenUtil jWTTokenUtil) {
    this.userService = userService;
    this.jWTTokenUtil = jWTTokenUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain
  ) throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jWTToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jWTToken = requestTokenHeader.substring(7);
      try {
        username = jWTTokenUtil.getUsernameFromToken(jWTToken);
      } catch (IllegalArgumentException e) {

        System.out.println("Unable to get JWT Token");

        JWTResponse errorResponse = new JWTResponse("Unable to get JWT Token", "Unauthorized Access");
        byte[] responseToSend = restResponseBytes(errorResponse);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);

        return;

      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");

        JWTResponse errorResponse = new JWTResponse("JWT Token has expired", "Unauthorized Access");
        byte[] responseToSend = restResponseBytes(errorResponse);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);

      } catch (SignatureException e) {

        logger.warn("JWT Signature in invalid");

        JWTResponse errorResponse = new JWTResponse(e.getMessage(), "Unauthorized Access");
        byte[] responseToSend = restResponseBytes(errorResponse);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);
      } catch(Exception e){
        logger.warn(e.getClass().getName()+ " Exception occurred");

        JWTResponse errorResponse = new JWTResponse(e.getMessage(), "Unauthorized Access");
        byte[] responseToSend = restResponseBytes(errorResponse);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);

      }

    } else {

      String requestURI = request.getRequestURI();
      logger.warn("JWT Token does not begin with Bearer String. Context path: " + request.getContextPath() + " URI: " + requestURI);

      if (!requestURI.contains("auth") && !requestURI.contains("info")
          && !requestURI.contains("home") && !requestURI.contains("h2")) {
        JWTResponse errorResponse = new JWTResponse("Invalid Token", "Unauthorized Access");
        byte[] responseToSend = restResponseBytes(errorResponse);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);
      }



    }

    // Once we get the token validate it.
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = userService.loadUserByUsername(username);

      // if token is valid configure Spring Security to manually set
      // authentication
      if (jWTTokenUtil.validateToken(jWTToken, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

      }

    }
    chain.doFilter(request, response);
  }

  private byte[] restResponseBytes(JWTResponse eErrorResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
    return serialized.getBytes();
  }
}


