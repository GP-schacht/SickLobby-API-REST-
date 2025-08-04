package com.example.sickslobby.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/post/greet",
                                "/auth/**",
                                "/docs/**",
                                "/post/*/delete",
                                "/post/*/update",
                               //paciente
                                "/post/ListPaciente",
                                "/post/addPaciente",
                                //esoecialista
                                "/post/addEspecialista",
                                "/post/ListEspecialista",
                                //Citas
                                "/citas/addCita",
                                "/citas/list",
                                "/citas/id"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults()); // O usa JWT si estás usando tokens

        return http.build();
    }
}



