package sg.gov.lunchbuddy.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class TokenIssuer {

    public String issue(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC256("secret"));
    }
}
