package br.com.romulo.apimedico.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração de segurança da aplicação.
 * Responsável por definir as políticas de segurança, autenticação e autorização.
 * Utiliza Spring Security para implementar as regras de segurança.
 * 
 * processo de autenticacao eh via token, e não por sessao
 * 
 * @author Romulo A. Neves 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	@Autowired private SecurityFilter securityFilter;
	   
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	            .csrf(csrf -> csrf.disable())
	            .sessionManagement(session -> 
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers(HttpMethod.POST, "/requisitar-token").permitAll() //barra tudo para pedir token, execeto requisitar token
	                .anyRequest().authenticated()
	            )
	            .addFilterBefore(securityFilter, 
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
	            .build();
	}
                
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
