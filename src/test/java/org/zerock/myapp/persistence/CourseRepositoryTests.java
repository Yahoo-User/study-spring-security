package org.zerock.myapp.persistence;

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
import org.zerock.myapp.util.RandomNumberGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional
@SpringBootTest
public class CourseRepositoryTests {
	@Autowired private CourseRepository courseDao;
	@Autowired private InstructorRepository instructorDao;
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		log.info("\t+ this.courseDao: {}", this.courseDao);
		log.info("\t+ this.instructorDao: {}", this.instructorDao);
	}
	
	@Disabled
	@Tag("Course-Tests")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("contextLoads")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked.");
		
	}
	
	
//	@Disabled
	@Tag("Course-Tests")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareData() {
		log.debug("prepareData() invoked.");
		
		IntStream.rangeClosed(1, 100).forEach(seq -> {
			Course course = new Course();
			
			int type = RandomNumberGenerator.generateInt(1, 5);
			course.setType(type);
			
			course.setName("Name-%03d".formatted(seq));
			course.setCapacity(RandomNumberGenerator.generateInt(10, 40));
			course.setDetail("Detail-%03d".formatted(seq));
			
			int startYear = RandomNumberGenerator.generateInt(1990, 2026);
			int endYear = RandomNumberGenerator.generateInt(1990, 2026);
			
			int startMonth = RandomNumberGenerator.generateInt(1, 13);
			int endMonth = RandomNumberGenerator.generateInt(1, 13);
			
			int startDate = RandomNumberGenerator.generateInt(1, 32);
			int endDate = RandomNumberGenerator.generateInt(1, 32);
			
			course.setStartDate("%04d-%02d-%02d".formatted(startYear, startMonth, startDate));
			course.setEndDate("%04d-%02d-%02d".formatted(endYear, endMonth, endDate));
			
			course.setStatus(RandomNumberGenerator.generateInt(1, 5));
			course.setEnabled(true);

			this.courseDao.save(course);
		});	// .forEach
	}
	

} // end class
