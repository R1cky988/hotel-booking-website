//package com.project.booking.components;
//
//import com.project.booking.exceptions.InvalidParamException;
//import com.project.booking.models.Users;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.io.Encoder;
//import io.jsonwebtoken.io.Encoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.InvalidParameterException;
//import java.security.Key;
//import java.security.SecureRandom;
//import java.security.Signature;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@RequiredArgsConstructor
//@Component
//public class JwtTokenUtil {
//    @Value("${jwt.expiration}")
//    private int expiration;
//
//    @Value("${jwt.secretKey}")
//    private String secretKey;
//
//    public String generateToken(Users users) throws Exception{
//        //properties => claims
//        Map<String, Object> claims  = new HashMap<>();
//       // this.generateSecretKey();
//        claims.put("email", users.getEmail());
//        try{
//            String token = Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(users.getEmail())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
//                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                    .compact();
//            return token;
//        } catch (Exception e) {
//            throw new InvalidParamException("Cannot create jwt token error: " +e.getMessage());
//            //return null;
//        }
//    }
//
//    private Key getSignInKey(){
//        byte[] bytes = Decoders.BASE64.decode(secretKey); //XCkpo1dyR3mz8V34lZxeN0xn9RUZYR74897vlLZWPu0=
//        return Keys.hmacShaKeyFor(bytes);
//    }
//
//    private String generateSecretKey(){
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }
//
//    private Claims extractAllClaims(String token){ //extract tat ca
//        return Jwts.parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJwt(token)
//                .getBody();
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){ //extract cu the 1 claim
//        final Claims claims = this.extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    //kiem tra expiration
//    public boolean isTokenExpired (String token){
//        Date expirationDate = this.extractClaim(token, Claims :: getExpiration);
//        return expirationDate.before(new Date());
//
//    }
//
//    public String extractEmail(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//}
