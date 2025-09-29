package com.sp.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.sp.jwt.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	private JWTFilter jwtFilter;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(myUserDetailsService);
		return provider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors().and().csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PATCH, "/userApi/updateUser/**")
						.permitAll().requestMatchers(HttpMethod.POST, "/userApi/storeUser").permitAll()
						.requestMatchers(HttpMethod.POST, "/userApi/login").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
	
	 @Bean
	    public CorsFilter corsFilter() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http://localhost:4200"));  // ✅ Angular origin
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        config.setAllowCredentials(true);  // important for cookies/auth headers

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);  // Apply to all routes

	        return new CorsFilter(source);
	    }

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers(HttpMethod.PATCH, "/userApi/updateUser/**")
//				.requestMatchers(HttpMethod.POST, "/userApi/storeUser");
//	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.withDefaultPasswordEncoder().username("Santosh").password("Santy@123")
//				.roles("USER").build();
//
//		UserDetails userDetails1 = User.withDefaultPasswordEncoder().username("Priyanka").password("Priya@123")
//				.roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(userDetails, userDetails1);
//	}

	/**
	 * Method for JWT authentication
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
