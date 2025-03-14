package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Upfile;


//@Repository("UpfileDao")	// *Not Required.
public interface UpfileRepository extends JpaRepository<Upfile, Integer> {
	
	// Query Methods With Variational Conditions.
	
} // end interface


