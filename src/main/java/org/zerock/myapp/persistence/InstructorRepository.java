package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Instructor;


//@Repository("UpfileDao")	// *Not Required.
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
	
	// Query Methods With Variational Conditions.
	
} // end interface
