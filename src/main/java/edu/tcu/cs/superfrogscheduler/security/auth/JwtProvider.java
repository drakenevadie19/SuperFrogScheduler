package edu.tcu.cs.superfrogscheduler.security.auth;

import edu.tcu.cs.superfrogscheduler.user.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    public JwtProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long expireIn = 2; // 2 hrs

        // prepare claim called authorities
        // convert granted authorities collections into a string of role
        String authorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));// Must be space delimited

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expireIn, ChronoUnit.HOURS))
                // config so that the claim 'sub' in jwt payload has value of userId
                // Then when spring security authenticate a jwt, userId can be extracted by using
                // authentication.getName()
                .subject(((UserPrincipal)authentication.getPrincipal()).getUserId())
                // custom claim
                .claim("authorities", authorities)
                .build();


        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
