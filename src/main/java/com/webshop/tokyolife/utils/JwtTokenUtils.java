package com.webshop.tokyolife.utils;

import com.webshop.tokyolife.dto.user.TokenPayload;
import com.webshop.tokyolife.model.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {
    @Value("${jwt.my-secret-key}")
    private String secretKey;
    @Value("${jwt.expired}")
    private Long expiredDate;
    @Value("${jwt.name}")
    private String name;
    public String generateToken(UsersEntity usersEntity){
        Map<String,Object> claims = new HashMap<>();
        TokenPayload tokenPayload = TokenPayload.builder().uuid(usersEntity.getUuid().toString())
                .email(usersEntity.getEmail()).build();
        claims.put(name,tokenPayload);
        Long issuedAt = System.currentTimeMillis();
        return Jwts.builder().setClaims(claims)
                .claim("roles",usersEntity.getRoles().toString())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration( new Date(issuedAt+expiredDate*1000))
                .signWith(SignatureAlgorithm.HS512, Base64.encodeBase64(secretKey.getBytes()))
                .compact();


    }

    public TokenPayload getTokenPayload(String token) {
        return getClaimsFromToken(token,
                (Claims claims) -> {
                    Map<String, Object> mapResult = (Map<String, Object>) claims.get(name);
                    return TokenPayload.builder().uuid((String) mapResult.get("uuid"))
                            .email((String) mapResult.get("email")).build();
                });
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = Jwts.parser().setSigningKey(Base64.encodeBase64(secretKey.getBytes())).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    public boolean validate(String token, UsersEntity usersEntity) {
        Date expiredDate = getClaimsFromToken(token, Claims::getExpiration);
        return expiredDate.after(new Date());
    }
}
