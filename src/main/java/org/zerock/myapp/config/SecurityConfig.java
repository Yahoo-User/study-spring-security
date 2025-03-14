package org.zerock.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Configuration(proxyBeanMethods = true)	// CGLIB & Singleton
public class SecurityConfig  {
	@Autowired private UserDetailsService service;
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.debug(">>>>>>>>>> securityFilterChain({}) invoked.", http);
				
		// --- 1 ----------------
		http.csrf(customizr -> customizr.disable());

		
		// --- 2 ----------------
		http.formLogin(
			customizer -> 
				customizer
					.loginPage("/login.html")
				
					// (1) Equals to .defaultSuccessUrl("/security/login-success", false)
					.defaultSuccessUrl("/security/login-success")
				
					// (2) alwaysUse true if the defaultSuccessUrl should be used after authentication
					// 	   despite if a protected page had been previously visited
//							.defaultSuccessUrl("/security/login-success", true)
								 
					.failureUrl("/security/login-failure"));
		
		
		// --- 3 ----------------
		http.logout(customizer ->
			customizer
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.logoutUrl("/security/logout")
				.logoutSuccessUrl("/security/logout-success"));
		
		
		// --- 4 ----------------
		http.authorizeHttpRequests(
				customizr -> 
					customizr
//						.requestMatchers(antMatcher("/security/user")).anonymous()
//					    .requestMatchers(antMatcher("/security/")).authenticated()
				   
					    // (1) Important: AuthorizedUrl.hasRole() or hasAnyRole() : It handles permission settings for *already *authenticated users.
//					    .requestMatchers(antMatcher("/security/member")).hasAnyAuthority("ROLE_MEMBER", "ROLE_MANAGER", "ROLE_ADMIN")
//					    .requestMatchers(antMatcher("/security/manager")).hasAnyRole("MANAGER", "ADMIN")
//					    .requestMatchers(antMatcher("/security/admin")).hasAuthority("ROLE_ADMIN")
		
						// (2) Important: If the code below is *Not included, all the previous permission settings for each path pattern will not be applied, 
						//					  	   thus access will be denied for all paths.
						.anyRequest().permitAll()
		);	// .authorizeHttpRequests
		
		
		// --- 5 ----------------
		// The following `authenticationProvider` method.
		
		
		// --- 6 ----------------
        http.sessionManagement(
    		customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)   	// 1st. method: default
//    		customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)				// 2nd. method: If RESTful API Service,
        );	// .sessionManagement
		
        
		// --- 7 ----------------
        
        /**
         * -----------------------------
         * http.exceptionHandling()
         * -----------------------------
         * It is primarily used in conjunction with `@ControllerAdvice` and `@ExceptionHandler` to manage exception handling centrally.
         * 
         * This method is used within Spring Security configuration to handle various exception handling settings,
         * such as handling unauthenticated access or insufficient permissions.
         * 
         */
        
        http.exceptionHandling(customizer -> customizer.accessDeniedPage("/security/access-denied"));
        

		// --- 8 ----------------
		return http.build();
	} // securityFilterChain

	
  @Bean
	DaoAuthenticationProvider authenticationProvider() {		// @Since v2.7.x and v3.4.x
		log.debug(">>>>>>>>>> authenticationProvider() invoked.");
		
		// An AuthenticationProvider implementation that retrieves user details from a UserDetailsService.
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
		authProvider.setUserDetailsService(service);
		
		return authProvider;
	} // authenticationProvider
	

	
} // end class
