package com.viniciusfk.bankApi.security;

import com.viniciusfk.bankApi.model.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenUtil {
    private static final int ONE_DAY = 24*60*60*1000;

    private static final long EXPIRATION = ONE_DAY;
    private static final String SECRET_KEY = "Epic32DigitSafeSecretKeyIndeedYe"; //System.getenv("BANK_API_TOKEN_KEY");
    private static final String ISSUER = "VinSys";

    public static String createToken(User user){
        Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private static boolean isExpirationDateValid(Date expiration){
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    private static boolean isIssuerValid(String emissor){
        return emissor.equals(ISSUER);
    }

    private static boolean isSubjectValid(String username){
        return username != null && !username.isEmpty();
    }

    private static Jws<Claims> decrypt(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
    }

    public static Boolean validateToken(String token){
        Jws<Claims> jwsClaims = decrypt(token);

        String username = jwsClaims.getBody().getSubject();
        String issuer = jwsClaims.getBody().getIssuer();
        Date expiration = jwsClaims.getBody().getExpiration();

        return isSubjectValid(username) && isIssuerValid(issuer) && isExpirationDateValid(expiration);
    }

    public static Authentication validateAuthentication(String token){
        Jws<Claims> jwsClaims = decrypt(token);

        String username = jwsClaims.getBody().getSubject();

        if(validateToken(token))
            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

        return null;
    }

    public static String getSubject(String token){
        Jws<Claims> jwsClaims = decrypt(token);
        System.out.println(jwsClaims.getBody().getSubject());
        String subject = jwsClaims.getBody().getSubject();
        System.out.println(subject);
        return subject;
    }
}
