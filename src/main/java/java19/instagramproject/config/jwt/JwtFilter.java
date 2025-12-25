package java19.instagramproject.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java19.instagramproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
                              //Ар бир HTTP request үчүн 1 жолу иштейт
public class JwtFilter extends OncePerRequestFilter {
    // JWT менен иштеген сервис
    private final JwtService jwtService;

//    Ар бир request үчүн аткарылуучу метод
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//                     URL текшерүү
        String path = request.getRequestURI();
//        Login,register,swagger учун JWT текшербейт дароо откорот
        if (path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }
        // Authorization header алабыз
        String header = request.getHeader("Authorization");
        // Header барбы жана Bearer менен башталабы?
        if (header != null && header.startsWith("Bearer ")) {
            // "Bearer " → 7 символ, ошондон кийинкиси токен
            String token = header.substring(7);

            try {
//                Токен текшеруу JWT бузулганбу? мооноту буттубу?
                // Токен текшерилет жана user алынат
                User user = jwtService.verifyToken(token);
//               // Spring Security үчүн Authentication объект түзөбүз
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
//               // SecurityContext'ке user'ди орнотобуз
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("""
             {
              "httpStatus": "UNAUTHORIZED",
                 "message": "Invalid or expired token"
         }
         """);
                return;
            }
        }
        // Кийинки фильтрге өткөрөбүз
        filterChain.doFilter(request, response);
    }
}
