package com.security.jwtdemo.config.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.security.jwtdemo.service.MyUserDetailsService;
import com.security.jwtdemo.util.JwtUtil;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private MyUserDetailsService myUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String auth = request.getHeader("Authorization");
    String jwt = null;
    String user = null;
    if (auth != null) {
      jwt = auth.substring(7);
      user = JwtUtil.extractUser(jwt);
    }


    if (null != user && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = myUserDetailsService.loadUserByUsername(user);

      if (JwtUtil.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }

}
