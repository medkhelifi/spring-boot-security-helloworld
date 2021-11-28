package com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt;

import com.medkhelifi.tutorials.springsecurityhelloworld.exceptions.InvalidJwtAuthenticationException;
import com.medkhelifi.tutorials.springsecurityhelloworld.models.Role;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.configuration.SecurityPropertiesExtension;
import io.jsonwebtoken.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


/**
 * <p>Json Web Token provider
 * This component is responsible to create authenticate and validate a Web Json Token after user authentication</p>
 *
 * <p> Because this component use a {@link UserDetailsService} interface,
 * you have absolutely add an implementation to this service in your program</p>
 *
 * @author medkhelifi mohamedchrif.khelifi@gmail.com
 *
 * @since 1.0
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Setter
    private SecurityPropertiesExtension spe;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(SecurityPropertiesExtension securityPropertiesExtension, UserDetailsService userDetailsService){
        this.spe = securityPropertiesExtension;
        this.userDetailsService = userDetailsService;
    }
    /**
     * create a json token from username and role list
     *
     * @param username the username
     * @param roles    the roles
     * @return token string
     */
    public String createToken (String username, List<Role> roles){
        return createToken(username, roles, new Date());
    }

    /**
     * create a json token by specifying the IssuedAt date
     * @param username
     * @param roles
     * @param date
     * @return
     */
    public String createToken(String username, List<Role> roles, Date date){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = date;
        Date validity = new Date(now.getTime() + spe.getJwt().getExpiration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(spe.getJwt().getSecret().getBytes()))
                .compact();
    }

    /**
     * Get authentication authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Gets username.
     *
     * @param token the token
     * @return the username
     */
    public String getUsername(String token) {
        return this.getClaims(token).getBody().getSubject();
    }

    /**
     * Resolve token string.
     *
     * @param req the req
     * @return the string
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(spe.getJwt().getHeader());
        if (bearerToken != null && bearerToken.startsWith(spe.getJwt().getHeaderPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = this.getClaims(token);
            log.debug("[JwtTokenProvider - tokenExpiration]: "+claims.getBody().getExpiration().toString());

            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException  | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token", e);
        }
    }

    private Jws<Claims> getClaims(String token){
        return Jwts.parser().setSigningKey(spe.getJwt().getSecret().getBytes()).parseClaimsJws(token);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token).getBody();
        return claimsResolver.apply(claims);
    }

}
