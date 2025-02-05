package com.barcode.server.jwt;

import com.barcode.server.barcode.dao.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * PackageName : com.example.ssssksss_blog.security
 * FileName : JwtTokenManager
 * Author : 이 수 경
 * Date : 2022-04-23
 * Description :
 */
@Service
public class JwtTokenManager {

    private byte[] encodingKey = DatatypeConverter.parseBase64Binary("E2BBFD358D01113E9563FB3865582DA90D056C658099DEB46F2FFF95BC67C2EC");
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key = Keys.hmacShaKeyFor(encodingKey);
    private String jwtSecret;

    public String createAccessToken(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setIssuedAt(new Date())
                .setExpiration(date) // 토큰 만료 시간
                .setNotBefore(new Date()) // 토큰 활성 날짜
                .claim("email",user.getEmail()) //미등록 클레임
                .claim("role", user.getRole())
                .signWith(key)
                .compact(); // 설정끝
    }
    public String createRefreshToken(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMonths(1);
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        return Jwts.builder()
                .setHeaderParam("typ","JWT") //토큰 타입
                .setIssuedAt(new Date()) //토큰 발행 시간
                .setExpiration(date) // 토큰 만료 시간
                .setNotBefore(new Date(date.getTime())) // 토큰 활성 날짜
                .signWith(key)
                .compact(); // 설정끝
    }
    public Claims validRefreshTokenAndReturnBody(String token) {
        Claims claims = null;
        try {
            return claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |  IllegalArgumentException e) {
        }
        finally {
            return  claims;
        }
    }
    public Claims validAccessTokenAndReturnBody(String token) {
        Claims claims = null;

        try {
            return claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
        }
        finally {
            return  claims;
        }
    }
    //    만료된 액세스 토큰에서 데이터 추출
    public Claims expiredTokenAndReturnBody(String token) {
        Claims claims = null;

        try {
            return claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(UnsupportedJwtException | MalformedJwtException  | IllegalArgumentException e) {
        }
        finally {
            return  claims;
        }
    }

    // 토큰 만료
    public Boolean isTokenExpired(String token){
        Date expiration = validAccessTokenAndReturnBody(token).getExpiration();
        return expiration.before(new Date());
    }
}

