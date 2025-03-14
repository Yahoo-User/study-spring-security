package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Member;


//@Repository("UpfileDao")	// *Not Required.
public interface MemberRepository extends JpaRepository<Member, String> {
	;;
} // end interface



