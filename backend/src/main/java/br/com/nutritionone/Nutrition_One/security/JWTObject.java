package br.com.nutritionone.Nutrition_One.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class JWTObject {
    private String subject;
    private Date issueAt;
    private Date expiration;
    private List<String> roles;

}
