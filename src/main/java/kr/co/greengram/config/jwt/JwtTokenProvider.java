package kr.co.greengram.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.greengram.config.constants.ConstJwt;
import kr.co.greengram.config.model.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper; // Jackson 라이브러리 (JSON to Object, Object to JSON)
    private final ConstJwt constJwt;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, ConstJwt constJwt) {
        this.objectMapper = objectMapper;
        this.constJwt = constJwt;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(constJwt.getSecretKey()));
    }

    // JWT 토큰 생성 메소드
    public String generateToken(JwtUser jwtUser, long tokenValidityMilliSecends) {
        Date now = new Date(); // 기본 생성자로 Date 객체를 만들면 현재 일시 정보로 객체화
        return Jwts.builder()
                // header
                .header().type(constJwt.getBearerFormat())
                .and()

                // payload
                .issuer(constJwt.getIssuer())
                .issuedAt(now) // 발행 일시(토큰 생성 일시)
                .expiration(new Date(now.getTime() + tokenValidityMilliSecends)) // 만료 일시(토큰 만료 일시)
                .claim(constJwt.getClaimKey(), makeClaimByUserToJson(jwtUser))

                // signature
                .signWith(secretKey)
                .compact();
    }

    public String makeClaimByUserToJson(JwtUser jwtUser) {
        try {
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰 생성 에러가 발생하였습니다.");
        }
    }

    public JwtUser getJwtUserFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            String json = claims.get(constJwt.getClaimKey(), String.class);

            return objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰에 문제가 있습니다.");
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
