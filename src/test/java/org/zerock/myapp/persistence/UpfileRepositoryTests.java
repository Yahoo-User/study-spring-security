package org.zerock.myapp.persistence;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import org.zerock.myapp.entity.Trainee;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.util.RandomNumberGenerator;
import org.zerock.myapp.util.UUIDGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional
@SpringBootTest
public class UpfileRepositoryTests {
	@Autowired private CourseRepository courseDao;
	@Autowired private InstructorRepository instructorDao;
	@Autowired private TraineeRepository traineeDao;
	@Autowired private UpfileRepository upfileDao;
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		log.info("\t+ this.courseDao: {}", this.courseDao);
		log.info("\t+ this.instructorDao: {}", this.instructorDao);
		log.info("\t+ this.traineeDao: {}", this.traineeDao);
	}
	
	@Disabled
	@Tag("UpFile-Tests")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("contextLoads")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked.");
		
	}
	
	
//	@Disabled
	@Tag("UpFile-Tests")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareData() {
		log.debug("prepareData() invoked.");
		
		// course : 101 ~ 200, instructor: 200 - 298, trainee: 100 ~ 2569
		final String dirPath = "C:/temp/upload/";
		
		IntStream.rangeClosed(101, 200).forEachOrdered(courseId -> {	// course
			try {
				final String uuid = UUIDGenerator.generateUniqueKeysWithUUIDAndMessageDigest().toLowerCase().substring(0, 11);
				final String original = "Course_%03d.pdf".formatted(courseId);
				final String path = dirPath + original;
				
				Upfile file = new Upfile();
				
				file.setOriginal(original);
				file.setUuid(uuid);
				file.setPath(path);
				file.setEnabled(true);

				Optional<Course> optional = this.courseDao.findById(courseId);					
				optional.ifPresent(foundCourse -> file.setCourse(foundCourse));	// .ifPresent
				
				this.upfileDao.save(file);
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException _ignored) {}
		});	// .forEachOrdered.
		
		// ---------------
		
		IntStream.rangeClosed(200, 298).forEachOrdered(instructorId -> {	// instructor
			try {
				int randomNumber = RandomNumberGenerator.generateInt(1, 7);
				
				if(randomNumber == 1 || randomNumber == 3 || randomNumber == 7) {
					final String uuid = UUIDGenerator.generateUniqueKeysWithUUIDAndMessageDigest().toLowerCase().substring(0, 11);
					final String original = "instructor_%03d.ppt".formatted(instructorId);
					final String path = dirPath + original;
					
					Upfile file = new Upfile();
					
					file.setOriginal(original);
					file.setUuid(uuid);
					file.setPath(path);
					file.setEnabled(true);
					
					Optional<Instructor> optional = this.instructorDao.findById(instructorId);					
					optional.ifPresent(foundInstructor -> file.setInstructor(foundInstructor));	// .ifPresent
					
					this.upfileDao.save(file);
				} // if
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException _ignored) {}
		});	// .forEachOrdered.
		
		// ---------------
		
		IntStream.rangeClosed(100, 2569).forEachOrdered(traineeId -> {		// trainee
			try {
				int randomNumber = RandomNumberGenerator.generateInt(1, 7);
				
				if(randomNumber == 2 || randomNumber == 5 || randomNumber == 6) {
					final String uuid = UUIDGenerator.generateUniqueKeysWithUUIDAndMessageDigest().toLowerCase().substring(0, 11);
					final String original = "trainee_%03d.zip".formatted(traineeId);
					final String path = dirPath + original;
					
					Upfile file = new Upfile();
					
					file.setOriginal(original);
					file.setUuid(uuid);
					file.setPath(path);
					file.setEnabled(true);

					Optional<Trainee> optional = this.traineeDao.findById(traineeId);					
					optional.ifPresent(foundTrainee -> file.setTrainee(foundTrainee));	// .ifPresent

					this.upfileDao.save(file);
				} // if
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException _ignored) {}
		});	// .forEachOrdered.
	}
	

} // end class
