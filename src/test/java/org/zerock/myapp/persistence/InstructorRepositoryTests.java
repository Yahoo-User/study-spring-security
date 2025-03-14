package org.zerock.myapp.persistence;

import java.util.Optional;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Instructor;
import org.zerock.myapp.util.RandomNumberGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional
@SpringBootTest
public class InstructorRepositoryTests {
	@Autowired private CourseRepository courseDao;
	@Autowired private InstructorRepository instructorDao;
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		
		log.info("\t+ this.courseDao: {}", this.courseDao);
		log.info("\t+ this.instructorDao: {}", this.instructorDao);
	}
	
	@Disabled
	@Tag("Instructor-Tests")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("contextLoads")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked.");
		
	}
	
	
//	@Disabled
	@Tag("Instructor-Tests")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareData() {
		log.debug("prepareData() invoked.");
		
		IntStream.rangeClosed(102, 200).forEach(courseId -> {			
			Instructor instructor = new Instructor();
			
			instructor.setName("Instructor-%d".formatted(courseId));
			
			int first = RandomNumberGenerator.generateInt(100, 300);
			int last = RandomNumberGenerator.generateInt(1000, 3000);
					
			instructor.setTel("010-%03d-%04d".formatted(first, last));
			instructor.setEnabled(true);
			
			int status = RandomNumberGenerator.generateInt(1, 4);
			instructor.setStatus(status);
			
			if(status == 2) {
				Optional<Course> optional = this.courseDao.findById(courseId);
				
				optional.ifPresent(foundCourse -> {
					instructor.setCourse(foundCourse);				
				});	// .ifPresent
			} // if
			
			this.instructorDao.save(instructor);
		});	// .forEach
	}
	

} // end class
