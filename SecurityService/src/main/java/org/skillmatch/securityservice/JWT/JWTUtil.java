package org.skillmatch.securityservice.JWT;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.skillmatch.securityservice.DAO.Entity.PersonEntity;
import org.skillmatch.securityservice.Security.PersonAuthentication;
import org.skillmatch.securityservice.Services.SecurityPersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${expressTime}")
    private long expressTime;

    private final SecurityPersonService securityPersonService;

    private Key key;


    private Key getKey() {
        if (key == null) {
            byte[] keyBytes = new byte[32];
            new SecureRandom().nextBytes(keyBytes);
            key= new SecretKeySpec(keyBytes,"HmacSHA256");
        }
        return key;
    }

    public String generateJWT(PersonAuthentication person){
        JwtBuilder builder = Jwts.builder()
                .claims(new HashMap<String, Object>(){{
                    put("role", person.getRole());
                    put("username", person.getUserName());
                }})
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expressTime))
                .signWith(getKey());
        return builder.compact();
    }

    public boolean validateJWT(String token) {
        try {
            final String jwt = token.substring(7);
            return checkJWT(jwt);
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
    }

    protected boolean checkJWT(String jwt){
        try {
            JwtParser jwtParser =  Jwts.parser().setSigningKey(key).build();
            if(jwtParser.isSigned(jwt)){
                final Claims claims = jwtParser.parseClaimsJws(jwt).getPayload();
                final PersonEntity personEntity = securityPersonService.findPersonByUsername(
                        String.valueOf(claims.get("username"))
                );
                if(personEntity == null || !personEntity.getRole().equalsIgnoreCase(
                        String.valueOf(claims.get("role"))
                )){
                    return false;
                }else{
                    return true;
                }

            }
            return false;
        }catch (Exception e){
            return false;
        }
    }


}
