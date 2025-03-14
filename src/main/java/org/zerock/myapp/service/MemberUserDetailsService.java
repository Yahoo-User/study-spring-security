package org.zerock.myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.persistence.MemberRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

/**
 * NOTE : This `userDetailsService` bean cannot be used with `authenticate(auth)` method. (@Since v2.7.x and v3.4.x)
 * 
 * Important : In the `securityFilterChain(HttpSecurity http)` method, 
 * 		even if you don't set the `UserDetailsService` by calling `http.userDetailsService(CustomUserDetailsService)`, 
 * 		if you register the `UserDetailsService` bean in the Spring Context simply by using the `@Service` annotation, 
 * 		Spring Security automatically use it.
 */

@Service
public class MemberUserDetailsService implements UserDetailsService {
	@Autowired private MemberRepository dao;

	
	@PostConstruct
	void postConstruct() {
		log.debug("postConstruct() invoked.");
		log.info("\t+ this.dao: {}", this.dao);
	} // postConstruct
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug(">>>>>>>>>> loadUserByUsername({}) invoked.", username);
		
		Optional<Member> optional = this.dao.findById(username);
		
		if(optional.isPresent()) {
			Member foundMember = optional.get();
			
			/**
			 * public class User implements UserDetails, CredentialsContainer
			 * public User(String username, String password, Collection<? extends GrantedAuthority> authorities)
			 * public User(String username, String password, boolean enabled, boolean accountNonExpired, 
			 * 					  boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
			 */
			return new User(foundMember.getUser(), foundMember.getPass(), AuthorityUtils.createAuthorityList(foundMember.getRole().toString()));
		} // if
		
		throw new UsernameNotFoundException("No %s found.".formatted(username));
	} // loadUserByUsername

} // end class


