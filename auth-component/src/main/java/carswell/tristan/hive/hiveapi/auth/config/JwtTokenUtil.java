package carswell.tristan.hive.hiveapi.auth.config;

import carswell.tristan.hive.hiveapi.auth.models.users.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Component
@PropertySource("classpath:auth.properties")
public class JwtTokenUtil {

//    @Value("${auth.jwt.secret}")
    private String jwtSecret;
//    @Value("${auth.jwt.issuer}")
    private String jwtIssuer;

    private static final LocalDateTime PLUS_ONE_WEEK = LocalDateTime.now().plusWeeks(1);

    public final String generateAccessToken(final User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(PLUS_ONE_WEEK.toInstant(ZoneOffset.UTC).toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public final String getUsername(final String token) {
        final var claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public final boolean validate(final String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
