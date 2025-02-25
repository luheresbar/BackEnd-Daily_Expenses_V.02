package com.daily_expenses.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // EndPoints publicos
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();

                    // EndPoints Privados
                    // User
                    http.requestMatchers(HttpMethod.GET, "/api/user/find/all").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.GET, "/api/user/find/id/{id}").hasAnyRole("DEVELOPER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/user/find/email/{email}").hasAnyRole("DEVELOPER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/user/update/roles").hasAnyRole("DEVELOPER", "ADMIN");

                    // Role
                    http.requestMatchers(HttpMethod.GET, "/api/role/find/all").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.GET, "/api/role/find/name/{roleName}").hasAnyRole("DEVELOPER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/role/update").hasAnyAuthority("READ", "CREATE");

                    // Permission
                    http.requestMatchers(HttpMethod.GET, "/api/permission/find/all").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/api/permission/create").hasAnyAuthority("READ", "CREATE");

                    // HTTP Method
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
                    http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

                    // Configurar el resto de endpoint - NO ESPECIFICADOS (Permite acceso a todos los endpoints así no estén especificados)
//                    http.anyRequest().authenticated();

                    // Permite el acceso a todos los endpoints especificados (Más restrictivo)
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
