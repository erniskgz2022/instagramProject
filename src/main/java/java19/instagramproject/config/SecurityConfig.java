    package java19.instagramproject.config;
    import jakarta.servlet.http.HttpServletResponse;
    import java19.instagramproject.config.jwt.JwtFilter;
    import java19.instagramproject.repo.UserRepo;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {

        private final UserRepo  userRepo;
        // JWT токенди текшерүүчү фильтр
        private final JwtFilter jwtFilter;

        @Bean
        /**
         * UserDetailsService
         * Spring Security логин учурунда колдонуучуну
         * КАНТИП табыш керек экенин ушул жерден билет
         */
        public UserDetailsService userDetailsService() {
            return email -> userRepo.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found" + email));
        }

        @Bean
//        Бардык request Controller’ге жеткенче ушул жерден өтөт
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
//                   Эми кайсы URL ачык, кайсы жабык аныктайбыз
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(   "/api/auth/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/v3/api-docs/**")
                            .permitAll()
//                            КАЛГАНЫНЫН БААРЫ токен талап кылат
                            .anyRequest()
                            .authenticated()
                    )
//                    Token жок же жараксыз болгондо кастом жооп
                    .exceptionHandling(ex -> ex
                            .authenticationEntryPoint((req, res, e) -> {
                                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                res.getWriter().write("""
                 {
          "httpStatus": "UNAUTHORIZED",
          "exceptionClassName": "AuthenticationException",
          "message": "Please login first"
        }
        """);
                            })
                    )
                    // JWT фильтрди логин фильтрден АЛДЫНДА кошобуз
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            // SecurityFilterChain түзүп кайтарабыз
            return http.build();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
//        Login учурунда Email Password ушуну текшерет
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }
    }
