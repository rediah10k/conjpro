package com.registro.usuarios.seguridad;

import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.TipoRol;
import com.registro.usuarios.servicio.CustomUserDetailsService;
import com.registro.usuarios.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Autowired
	private CustomUserDetailsService usuarioServicio;

	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioServicio);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	private PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();//new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Permitir todos los recursos estáticos sin autenticació
		// Configurar el acceso a la página de login
	return 	http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests((requests) -> requests
				.requestMatchers("/js/**", "/css/**", "/img/**").permitAll()
				.requestMatchers("/login").access("isAnonymous()")
				.requestMatchers("/registro").hasAuthority("ADMIN")
				.requestMatchers("/crearAsamblea").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST, "/crearAsamblea").hasAuthority("ADMIN")
				.anyRequest().authenticated()
		).formLogin(form -> form
				.loginPage("/login")
				.permitAll()
				.successHandler(((request, response, authentication) -> {
					if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(TipoRol.ADMIN.name()))) {
						response.sendRedirect("/indexAdmin");
					} else {
						response.sendRedirect("/index");
					}
				}))
		).logout((logout) -> logout
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
		).build();

	}
}






