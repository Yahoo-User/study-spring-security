package org.zerock.myapp.persistence;

import java.util.Arrays;
import java.util.List;
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
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.util.RandomNumberGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional
@SpringBootTest
public class TraineeRepositoryTests {
	@Autowired private CourseRepository courseDao;
	@Autowired private TraineeRepository traineeDao;
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		
		log.info("\t+ this.courseDao: {}", this.courseDao);
		log.info("\t+ this.traineeDao: {}", this.traineeDao);
	}
	
	@Disabled
	@Tag("Trainee-Tests")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("contextLoads")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked.");
		
	}
	
	
//	@Disabled
	@Tag("Trainee-Tests")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareData() {
		log.debug("prepareData() invoked.");
		
		IntStream.rangeClosed(101, 200).forEach( courseId -> {
			int totalTrainees = RandomNumberGenerator.generateInt(10, 40);
			
			for(int i = 1; i <= totalTrainees; i++) {
				Trainee trainee = new Trainee();
				
				trainee.setName("Trainee-%03d".formatted(i));
				
				int first = RandomNumberGenerator.generateInt(100, 300);
				int last = RandomNumberGenerator.generateInt(1000, 3000);
						
				trainee.setTel("010-%03d-%04d".formatted(first, last));
				
				int status = RandomNumberGenerator.generateInt(1, 5);
				trainee.setStatus(status);
				
				// if course status is in (1, 2, 4), set course as a FK.
				List<Integer> courseStatusList = Arrays.asList(1, 2, 4);
				
				if(courseStatusList.contains(status)) {
					Optional<Course> optional = this.courseDao.findById(courseId);					
					optional.ifPresent(foundCourse -> trainee.setCourse(foundCourse));	// .ifPresent
				} // if
				
				trainee.setEnabled(true);
				
				this.traineeDao.save(trainee);
			} // for
		});	// .forEach
	}
	

} // end class
