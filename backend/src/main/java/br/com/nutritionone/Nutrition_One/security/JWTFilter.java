package br.com.nutritionone.Nutrition_One.security;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        try {
            if (token != null && !token.isBlank()) {
                JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);
                List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                        tokenObject.getSubject(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(userToken);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            logger.error("JWT validation error", e);
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }

    private List<SimpleGrantedAuthority> authorities(List<String> roles) {
        return roles == null ? List.of() :
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
