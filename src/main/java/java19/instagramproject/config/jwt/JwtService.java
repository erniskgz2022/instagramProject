package java19.instagramproject.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java19.instagramproject.entity.User;
import java19.instagramproject.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
/**
 * JWT токенди түзүү жана текшерүүчү сервис
 */
@Component
@RequiredArgsConstructor
public class JwtService {
    // User'ди база аркылуу табуу үчүн
    private final UserRepo userRepo;
    // application.properties'тен алынат
    @Value("${security.secret.key}")
    private String secretKey;
//    ТОкен тузуп баштайт
    public String generateToken(User user) {
//        HMAC256 алгоритми (secret key менен)
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                // Payload (маалыматтар)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                // Токен качан берилди
                .withIssuedAt(ZonedDateTime.now().toInstant())
//              Token 6 саат жашайт
                .withExpiresAt(ZonedDateTime.now().plusHours(6).toInstant())
//              Token кол коюлат
                .sign(algorithm);
    }


//     JWT токенди текшерүү
    public User verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

//        Token бузулган эмесби? текшеруу
        JWTVerifier verifier = JWT.require(algorithm).build();
        // Эгер токен бузук же мөөнөтү бүтсө → Exception
        DecodedJWT decodedJWT = verifier.verify(token);
        // Payload'дан email алабыз
        String email = decodedJWT.getClaim("email").asString();
        // Реалдуу user'ди база аркылуу алабыз
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email !"+ email));
    }

}
