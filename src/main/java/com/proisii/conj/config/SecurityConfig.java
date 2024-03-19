package com.proisii.conj.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll()  // Permitir acceso a todas las rutas sin autenticación
                )
                .formLogin().disable()  // Deshabilitar el formulario de login automático
                .httpBasic().disable()  // Opcional: deshabilitar la autenticación básica HTTP si también se desea
                .csrf().disable();  // Opcional: deshabilitar CSRF si no lo necesitas, pero ten en cuenta las implicaciones de seguridad

        return http.build();
    }
}
