package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.entity.Role;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@SpringBootTest
public class MemberRepositoryTests {
	@Autowired(required = false) private MemberRepository dao;
	
	private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		
		assertNotNull(this.dao);
		assertNotNull(this.encoder);
		
		log.info("\t+ this.dao: {}", this.dao);
		log.info("\t+ this.encoder: {}", this.encoder);
	}
	
	@Disabled
	@Tag("MemberRepository-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("contextLoads")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked.");
		
	}
	
//	@Disabled
	@Tag("MemberRepository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testCreateMembers")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
//	@Commit		// @Rollback(false)
//	@Rollback
	void testCreateMembers() {
		log.debug("testCreateMembers() invoked.");
		
		final String[] user 	= { "member", "manager", "admin" };
		final String[] pass 	= { "member", "manager", "admin" };
		final String[] name 	= { "MEMBER", "MANAGER", "ADMIN" };
		final Role[] role 		= { Role.ROLE_MEMBER, Role.ROLE_MANAGER, Role.ROLE_ADMIN };
		final Boolean[] enabled = { true, true, true };

		IntStream.rangeClosed(0, 2).forEachOrdered(index -> {
	        Member transientMember = new Member();
	        
	        transientMember.setUser(user[index]);
	        transientMember.setPass(this.encoder.encode(pass[index]));
	        transientMember.setName(name[index]);
	        transientMember.setRole(role[index]);
	        transientMember.setEnabled(enabled[index]);

	        this.dao.save(transientMember);
	        
	        log.info("\t+ transientMember: {}", transientMember);
		});
	}
	

} // end class
