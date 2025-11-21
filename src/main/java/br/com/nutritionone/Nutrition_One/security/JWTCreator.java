package br.com.nutritionone.Nutrition_One.security;

import io.jsonwebtoken.*;
import java.util.List;
import java.security.Key;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORIZATION = "authorities";

    public static String create(String prefix, Key key, JWTObject jwtObject) {
        String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssueAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORIZATION, checkRoles(jwtObject.getRoles()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, Key key)
            throws ExpiredJwtException, UnsupportedJwtException, SecurityException {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "").trim();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssueAt(claims.getIssuedAt());
        object.setRoles((List<String>) claims.get(ROLES_AUTHORIZATION));
        return object;
    }

    private static List<String> checkRoles(List<String> roles) {
        return roles.stream()
                .map(s -> "ROLE_".concat(s.replaceAll("ROLE_", "")))
                .toList();
    }
}
