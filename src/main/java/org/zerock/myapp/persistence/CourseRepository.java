package org.zerock.myapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.myapp.entity.Course;


//@Repository("UpfileDao")	// *Not Required.
public interface CourseRepository extends JpaRepository<Course, Integer>, 
	// Add This For Dynamic Complex Search Conditions. (***)
	JpaSpecificationExecutor<Course>  {
	
	// Query Methods With Variational Conditions.
	
	// ===============================
	// 1st. method: with JPQL
	// ===============================
	
/**
	// 1st. method: If an association mapped, join condition could be abbreviated. (***)
	final String jpql1 = """
			SELECT i.course
			FROM Instructor i JOIN i.course 
			WHERE i.course.enabled = 1 	AND i.name LIKE :instructorName
			""";
	
	// 2nd. method : even if an association mapped, it's good to set join condition definitely. (***)
	final String jpql2 = """
			SELECT c
			FROM Course c JOIN FETCH Instructor i ON c.id = i.course.id
			WHERE c.enabled = 1 AND i.name LIKE :instructorName 
			""";
	
	@Query(jpql1)
	@Query(jpql2)
 */

	// ===============================
	// 2nd. method: With Native SQL
	// ===============================
	
	final String nativeSQL = """
			SELECT c.* 
			FROM t_courses c JOIN t_instructors i ON c.id = i.crs_id 
			WHERE c.enabled = 1 AND i.name LIKE '%' || :instructorName || '%'
			""";
	
	@Query(value = nativeSQL, nativeQuery = true)
	Page<Course> findCoursesByInstructorName(@Param("instructorName") String name, Pageable paging);
	
	
} // end interface
