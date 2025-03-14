package org.zerock.myapp;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

/**
 * You can configure the page serialization mode by using the `@EnableSpringDataWebSupport` annotation.
 * Set the `pageSerializationMode` property to `PageSerializationMode.VIA_DTO` to resolve the serialization issue of `PageImpl`.
 */
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)

@ServletComponentScan
@SpringBootApplication
public class StudySpringSecurityApp extends ServletInitializer {

	
	public static void main(String[] args) {
		log.debug("main({}) invoked.", Arrays.toString(args));
		
		SpringApplication app = new SpringApplication(StudySpringSecurityApp.class);
		app.addListeners(e -> log.info("=> {}", e.getClass().getSimpleName()));
		
		app.run(args);
	} // main

} // end class

