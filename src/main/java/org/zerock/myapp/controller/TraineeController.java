package org.zerock.myapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDto;
import org.zerock.myapp.domain.CriteriaDto;
import org.zerock.myapp.entity.Course;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/trainees")

@RestController
public class TraineeController {		// Trainee Managment
	

	@GetMapping								// LIST ( + SEARCH)
	Page<Course> list(CriteriaDto dto) {
		log.debug("findBy({}) invoked.", dto);

		return null;		// Return all the courses matching the specified page number and search criteria.
	}

	@PutMapping								// CREATE - PUT Method
	Course register(CourseDto dto) {
		log.debug("register({}) invoked.", dto);

		return null;		// Returns a registered new course.
	}

	@GetMapping("/{id}")				// READ - GET method
	Course read(@PathVariable Integer id) {
		log.debug("read({}) invoked.", id);
		
		return null;		// Returns a detailed course.
	}

	@PostMapping("/{id}")			// UPDATE - POST method
	Course update(@PathVariable Integer id, CourseDto dto) {
		log.debug("update({}, {}) invoked.", id, dto);
		
		return null;		// Returns a updated course.
	}

	@DeleteMapping("/{id}")			// DELETE - DELETE method
	Course delete(@PathVariable Integer id) {								
		log.debug("delete({}) invoked.", id);
		
		return null;		// Returns a deleted course.
	}

} // end class
