package sg.gov.lunchbuddy.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.util.StringUtils.hasText;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenDecoder decoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        getTokenValue(request)
                .map(decoder::decode)
                .map(DecodedJWT::getSubject)
                .ifPresent(username -> SecurityContextHolder.getContext().setAuthentication(
                        UsernamePasswordAuthenticationToken.authenticated(
                                User.builder().username(username).password("sls").build(),
                                null, Collections.emptyList()
                        )
                ));

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenValue(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return empty();
    }
}
