package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Trainee;


//@Repository("UpfileDao")	// *Not Required.
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
	
	// Query Methods With Variational Conditions.
	
} // end interface


