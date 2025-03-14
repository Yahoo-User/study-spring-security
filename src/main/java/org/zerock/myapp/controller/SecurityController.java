package org.zerock.myapp.controller;

import java.util.Objects;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;				// new: OK
//import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;		// old:   peprecated

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/security/")

/**
 * Important:	Spring Security Default Pages
 * 
	(1) https://localhost/login 	-> default login 	page	(***)
	(2) https://localhost/logout -> default logout 	page	(***)
	
 */
@Controller("SecurityController")
public class SecurityController {
	

    @GetMapping
    void home() {
        log.debug("home() invoked.");
    }

    @GetMapping("/user")
    void user() {
        log.debug("user() invoked.");
    }

    @GetMapping("/member")
    void member() {
        log.debug("member() invoked.");
        
        // -- 1 -----------------
        
        /**
         * Implementation Type: org.springframework.security.authentication.UsernamePasswordAuthenticationToken [
        		Principal=org.springframework.security.core.userdetails.User [ 
        			Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, 
        			CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]
        		],
        		Credentials=[PROTECTED],
        		Authenticated=true,
        		Details=WebAuthenticationDetails [ RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=00D7512BB21F1E1F5745113E7F19AA8B ],
        		Granted Authorities=[]
	        ]
         */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Objects.requireNonNull(auth);
        log.info("\t1. auth: {}", auth);

        
        // -- 2 -----------------
        // Implementation Type: java.util.Collections$UnmodifiableRandomAccessList
        
        log.info("\t2. authorities: {}", auth.getAuthorities());		// Granted Authorities=[]

        
        // -- 3 -----------------
        
        /**
         * Implementation Type: org.springframework.security.core.userdetails.User [
					Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, 
					CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]
				]
         */
        log.info("\t3. principal: {}", auth.getPrincipal());

        
        // -- 4 -----------------
        
        /**
         * Implementation Type: org.springframework.security.web.authentication.WebAuthenticationDetails [
         * 			RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=C445DAB4E2D2DDA571B2A5E8BDBBAB5A
         * 		]
         */
        log.info("\t4. details: {}", auth.getDetails());

        
        // -- 5 -----------------
        log.info("\t5. isAuthenticated: {}", auth.isAuthenticated());	// true
        log.info("\t6. credentials: {}", auth.getCredentials());			// null
        log.info("\t7. name: {}", auth.getName());								// user
    }

    @GetMapping("/manager")
    void manager(
    		/**
    		 * org.springframework.security.core.userdetails.User [
    		 * 		Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, 
    		 * 		CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]
    		 * ]
    		 */
//    		@AuthenticationPrincipal UserDetails user		// OK
    		@AuthenticationPrincipal User user					// OK
    ) {
        log.debug("manager({}) invoked.", user);
        
        if(user != null) {
	        log.debug("\t+ usermame: {}", user.getUsername());			// user
	        log.debug("\t+ password: {}", user.getPassword());			// null
	        log.debug("\t+ authorities: {}", user.getAuthorities());		// []
        }
    }

    @GetMapping("/admin")
    void admin(
//    		@AuthenticationPrincipal Principal principal		// principal: null
    		@AuthenticationPrincipal User principal			// principal: null
    	) {
        log.debug("admin({}) invoked.", principal);
        
    }

    @GetMapping("/login")
    void login() {
        log.debug("login() invoked.");
    }

    @RequestMapping(path = "/logout", method = { RequestMethod.GET, RequestMethod.POST})
    String logout(HttpSession session) {
        log.debug("logout({}) invoked.", session);
        session.invalidate();
        
        return "redirect:logout-success";
    }

    @GetMapping("/login-success")
    void loginSuccess() {
        log.debug("loginSuccess() invoked.");
    }

    @GetMapping("/logout-success")
    void logoutSuccess() {
        log.debug("logoutSuccess() invoked.");
    }

    @GetMapping("/login-failure")
    void loginFailure() {
        log.debug("loginFailure() invoked.");
    }

    @GetMapping("/access-denied")
    void accessDenied() {
        log.debug("accessDenied() invoked.");
    }

} // end class
