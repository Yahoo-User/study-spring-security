package org.zerock.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.CourseDto;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.CourseRepository;
import org.zerock.myapp.persistence.spec.CourseSearchCriteria;
import org.zerock.myapp.persistence.spec.CourseSpecifications;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/courses")

@Transactional
@RestController
public class CourseController {		// Course Managment
	@Autowired private CourseRepository courseDao;
	
	
	@PostConstruct
	void postConstruct() {
		log.debug("postConstructor() invoked.");
		log.info("\t+ this.courseDao: {}", this.courseDao);
	}

	@GetMapping								// LIST ( + SEARCH)
	ResponseEntity< Page<Course> > list(
		CourseSearchCriteria criteria,				// DTO to collect dynamic search conditions. (***)
		
		@RequestParam(defaultValue = "1", required = false) 	Integer currPage,
		@RequestParam(defaultValue = "15", required = false) 	Integer pageSize
	) {
		log.debug(">>>>>>> list({}, {}, {}) invoked.", criteria, currPage, pageSize);

		Pageable paging = PageRequest.of(currPage - 1, pageSize, Sort.Direction.DESC, "id");
		
		// =================================
		// (1) Find All Course Entities Matched With a Instructor's Name Property
		// =================================
		if(criteria.getInstructorName() != null && criteria.getInstructorName().length() > 0) {
			// Page<Course > foundPage = this.courseDao.findCoursesByInstructorName('%' + criteria.getInstructorName() + '%', paging);		// 1. 1f using JPQL
			Page<Course > foundPage = this.courseDao.findCoursesByInstructorName(criteria.getInstructorName(), paging);						// 2. If using Native SQL
			
			return ResponseEntity.ok(foundPage);
		}

		// =================================
		// (2) Search By Dynamic Complex Condition With Spring Dat JPA Specification Spec.
		// =================================
		Specification<Course> spec = Specification.where(null);

		boolean hasDynamicConditions = false;
		
		// -- 1. Append a new specification by `enabled` criteria of `Course` entity.
		if(criteria.getEnabled() != null) {
			log.info("\t+ Create a specification for enabled property: {}", criteria.getEnabled());
			
			spec = spec.and( CourseSpecifications.isEnabled(criteria.getEnabled()) );
			hasDynamicConditions = true;
		}
		// -- 2. Append a new specification by `name` criteria of `Course` entity.
		if(criteria.getName() != null && criteria.getName().length() > 0) {
			spec = spec.and( CourseSpecifications.hasName(criteria.getName()) );
			hasDynamicConditions = true;
		}
		// -- 3. Append a new specification by `name` status` of `Course` entity.
		if(criteria.getStatus() != null) {
			spec = spec.and( CourseSpecifications.hasStatus(criteria.getStatus()) );
			hasDynamicConditions = true;
		}
		// -- 4. Append a new specification by `name` type` of `Course` entity.
		if(criteria.getType() != null) {
			spec = spec.and( CourseSpecifications.hasType(criteria.getType()) );
			hasDynamicConditions = true;
		}

		Page<Course> foundPage = null;	
		
		// 1st. find all Course entities by `findAll(Specification, Pageable)` of JPARepository with specifications.
		if(hasDynamicConditions) 	foundPage = this.courseDao.findAll(spec, paging);
		
		// 2nd. find all Course entities by `findAll(Pagable)` of JPARepository<T,ID> interface
		if(!hasDynamicConditions)	foundPage = this.courseDao.findAll(paging);

		return ResponseEntity.ok(foundPage);
	}

	@PutMapping								// CREATE - PUT Method
	Course register(CourseDto dto) {
		log.debug(">>>>>>> register({}) invoked.", dto);
		
		Course transientCourse = new Course();
		
		transientCourse.setType(dto.getType());
		transientCourse.setName(dto.getName());
		transientCourse.setCapacity(dto.getCapacity());
		transientCourse.setDetail(dto.getDetail());
		transientCourse.setEnabled(dto.getEnabled());
		transientCourse.setStartDate(dto.getStartDate());
		transientCourse.setEndDate(dto.getEndDate());
		transientCourse.setStatus(dto.getStatus());
		
		this.courseDao.save(transientCourse);

		return transientCourse;		// Returns a registered new course.
	}

	@GetMapping("/{id}")				// READ - GET method
	Course read(@PathVariable Integer id) {
		log.debug(">>>>>>> read({}) invoked.", id);
		
		Optional<Course> optional = this.courseDao.findById(id);
		optional.ifPresent(c -> log.info("\t+ foundCourse: {}", c));
		
		return optional.orElseThrow(() -> {
			throw new IllegalArgumentException("No Course ID: %s Found".formatted(id));
		});		// .orElseThrow
	}

	@PostMapping("/{id}")			// UPDATE - POST method
	Course update(@PathVariable Integer id, CourseDto dto) {
		log.debug(">>>>>>> update({}, {}) invoked.", id, dto);
		
		Optional<Course> optional = this.courseDao.findById(id);
		
		Course foundCourse = optional.orElseThrow(() -> {
			throw new IllegalArgumentException("No Course ID: %s Found".formatted(id));
		});		// .orElseThrow

		// 과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
		if(dto.getType() != null && dto.getType() != foundCourse.getType())	foundCourse.setType(dto.getType());
		
		if(dto.getName() != null && ! foundCourse.getName().equals(dto.getName()))	foundCourse.setName(dto.getName());
		if(dto.getCapacity() != null && foundCourse.getCapacity() != dto.getCapacity()) foundCourse.setCapacity(dto.getCapacity());
		if(dto.getDetail() != null && ! foundCourse.getName().equals(dto.getDetail()))	foundCourse.setDetail(dto.getDetail());

		if(dto.getStartDate() != null && ! foundCourse.getStartDate().equals(dto.getStartDate()))	foundCourse.setStartDate(dto.getStartDate());
		if(dto.getEndDate() != null && ! foundCourse.getEndDate().equals(dto.getEndDate()))	foundCourse.setEndDate(dto.getEndDate());
		
		// 상태(등록=1,진행중=2,폐지=3,종료=4)
		if(dto.getStatus() != null && dto.getStatus() != foundCourse.getStatus())	foundCourse.setStatus(dto.getStatus());		
		if(dto.getEnabled() != null && dto.getEnabled() != foundCourse.getEnabled())	foundCourse.setEnabled(dto.getEnabled());
		
		return foundCourse;		// Returns a updated course.
	}

	@DeleteMapping("/{id}")			// DELETE - DELETE method
	Course delete(@PathVariable(required = true) Integer id) {								
		log.debug(">>>>>>> delete({}) invoked.", id);
		
		Course foundCourse = this.courseDao.findById(id).get();
		log.info("\t+ foundCourse: {}", foundCourse);

		// Set parent and all associated children entities as disabled. (***) 
		if(foundCourse != null) {
			// Step1. Set Course as disabled.
			foundCourse.setEnabled(false);
			
			// Step2. Set the instructor as disabled.
			Instructor instructor = foundCourse.getInstructor();
			if(instructor != null) instructor.setEnabled(false);

			// Step3. Set all trainees as disabled.
			List<Trainee> trainees = foundCourse.getTrainees();
			trainees.forEach(t -> t.setEnabled(false));

			// Step4. Set all upfiles as disabled.
			List<Upfile> upfiles = foundCourse.getFiles();
			upfiles.forEach(f -> f.setEnabled(false));
		} // if
		
		return foundCourse;
	}

} // end class








