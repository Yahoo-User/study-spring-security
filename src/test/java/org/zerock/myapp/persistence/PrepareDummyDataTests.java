package org.zerock.myapp.persistence;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
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
public class PrepareDummyDataTests {
	@Autowired private CourseRepository courseDao;
	@Autowired private InstructorRepository instructorDao;
	@Autowired private TraineeRepository traineeDao;
	@Autowired private UpfileRepository upfileDao;

	
	final String dirPath = "C:/temp/upload/";

	// (주의) 데이터 생성시마다, 아래의 각 데이터의 아이디 범위를 맞추어야 합니다. (***)
	final int courseStartId 		= 1, 		courseEndId 		= 100;
	final int instructorStartId 	= 1, 		instructorEndId = 100;
	final int traineeStartId 		= 1, 		traineeEndId 		= 2395;
	
	
	@BeforeAll
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		
		log.info("\t1. this.courseDao: {}", this.courseDao);
		log.info("\t2. this.instructorDao: {}", this.instructorDao);
		log.info("\t3. this.traineeDao: {}", this.traineeDao);
		log.info("\t4. this.upfileDao: {}", this.upfileDao);
	}
	
	
//	@Disabled
	@Tag("Create-Dummy-Data")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareDataOfCourse")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareDataOfCourse() {
		log.debug("prepareDataOfCourse() invoked.");
		
		IntStream.rangeClosed(courseStartId, courseEndId).forEach(seq -> {
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
			
			int randomNumber = RandomNumberGenerator.generateInt(0, 2);
			course.setEnabled(randomNumber == 1);

			this.courseDao.save(course);
		});	// .forEach
	}
	
	
//	@Disabled
	@Tag("Create-Dummy-Data")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. prepareDataOfInstructor")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareDataOfInstructor() {
		log.debug("prepareDataOfInstructor() invoked.");
		
		IntStream.rangeClosed(courseStartId, courseEndId).forEach(courseId -> {			
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
	
	
//	@Disabled
	@Tag("Create-Dummy-Data")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. prepareDataOfTrainee")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareDataOfTrainee() {
		log.debug("prepareDataOfTrainee() invoked.");
		
		IntStream.rangeClosed(courseStartId, courseEndId).forEach( courseId -> {
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
	
	
//	@Disabled
	@Tag("Create-Dummy-Data")
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. prepareDataOfUpFile")
	@Timeout(value=3L, unit=TimeUnit.MINUTES)
	@Rollback(false)
	void prepareDataOfUpFile() {
		log.debug("prepareDataOfUpFile() invoked.");
		
		
		IntStream.rangeClosed(courseStartId, courseEndId).forEachOrdered(courseId -> {	// course
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
		
		IntStream.rangeClosed(instructorStartId, instructorEndId).forEachOrdered(instructorId -> {	// instructor
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
		
		IntStream.rangeClosed(traineeStartId, traineeEndId).forEachOrdered(traineeId -> {		// trainee
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
