package com.lacontraloria.amasuapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //external access
                        .requestMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                        //persona
                        .requestMatchers(HttpMethod.POST, "/v1/persona/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/persona").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/persona/{personaId}").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/v1/persona/").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/persona/users").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.PUT, "/v1/persona/{personaId}").hasAnyRole("ADMIN", "COORD", "MONITOR", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/v1/persona/{personaId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/curso").hasAnyRole("ADMIN", "COORD", "MONITOR", "USER")
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/alerta").hasAnyRole("ADMIN", "COORD", "MONITOR", "USER")
                        //admin
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/administrador").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/v1/administrador/{administradorId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/v1/administrador").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v1/administrador/{administradorId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/administrador/{administradorId}").hasRole("ADMIN")
                        //coord
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/coordinador").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/coordinador/{coordinadorId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/coordinador").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.PUT, "/v1/coordinador/{coordinadorId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/coordinador/{coordinadorId}").hasAnyRole("ADMIN", "COORD")
                        //monitor
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/monitor").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/monitor/{monitorId}").hasAnyRole("ADMIN", "COORD", "MONITOR")
                        .requestMatchers(HttpMethod.GET, "/v1/monitor").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/monitor/distrito").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.PUT, "/v1/monitor/{monitorId}").hasAnyRole("ADMIN", "COORD", "MONITOR")
                        .requestMatchers(HttpMethod.DELETE, "/v1/monitor/{monitorId}").hasAnyRole("ADMIN", "COORD")
                        //curso
                        .requestMatchers(HttpMethod.POST, "/v1/curso").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/curso/{cursoId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/curso").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/curso/{cursoId}").hasAnyRole("ADMIN", "COORD")
                        //alerta
                        .requestMatchers(HttpMethod.POST, "/v1/alerta").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/alerta/{alertaId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/alerta").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.PUT, "/v1/alerta/{alertaId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/alerta/{alertaId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.POST, "/v1/alerta/{alertaId}/observacion").hasAnyRole("ADMIN", "COORD")
                        //imagen
                        .requestMatchers(HttpMethod.POST, "/v1/alerta/{alertaId}/imagen").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/alerta/{alertaId}/imagen/{imagenId}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/alerta/{alertaId}/imagen/{imagenId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/alerta/{alertaId}/imagen/{imagenId}").hasAnyRole("ADMIN", "COORD")
                        //observacion
                        .requestMatchers(HttpMethod.POST, "/v1/observacion").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/observacion/{observacionId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/observacion").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/observacion/{observacionId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/observacion/{observacionId}").hasAnyRole("ADMIN", "COORD")
                        //veeduria
                        .requestMatchers(HttpMethod.POST, "/v1/persona/{personaId}/veeduria").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/veedurias/{veeduriaId}").hasAnyRole("ADMIN", "COORD", "MONITOR")
                        .requestMatchers(HttpMethod.GET, "/v1/veedurias/{veeduriaId}/monitores").hasAnyRole("ADMIN", "COORD", "MONITOR")
                        .requestMatchers(HttpMethod.POST, "/v1/veedurias/{veeduriaId}/monitores/postulate").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.POST, "/v1/veedurias/{veeduriaId}/monitores/asignado").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.GET, "/v1/veedurias").hasAnyRole("ADMIN", "COORD", "MONITOR")
                        .requestMatchers(HttpMethod.PUT, "/v1/veedurias/{veeduriaId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/veedurias/{veeduriaId}/monitores/postulado/{monitorId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/veedurias/{veeduriaId}/monitores/asignado/{monitorId}").hasAnyRole("ADMIN", "COORD")
                        .requestMatchers(HttpMethod.DELETE, "/v1/veedurias/{veeduriaId}").hasAnyRole("ADMIN", "COORD")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
