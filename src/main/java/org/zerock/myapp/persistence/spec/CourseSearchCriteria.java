package org.zerock.myapp.persistence.spec;

import lombok.Data;


/**
 * ==========================
 * 과정 목록 복합조건 명세
 * ==========================
 * (1) 기본 조건: enabled = 1
 * (2) 첫번째 조건 : status (상태(등록=1,진행중=2,폐지=3,종료=4))
 * (3) 두번째 조건 : type   (과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정))
 * (3) 세번째 조건 : name (과정명) 또는 t_instructors.name (강사명) <--- ***
 * 
 */

@Data
public class CourseSearchCriteria {			// DTO For Dynamic Complex Search Conditions. (***)
	private Boolean enabled = true;			// Default Condition.
	
	private String 		name;	
	private Integer 	status;							// 1=등록, 2=진행중, 3=폐지,     4=종료
	private Integer 	type;								// 1=NCS,   2=KDT,       3=산대특, 4=미정
	
	private String 	instructorName;			// Instructor Entity
	
	

} // end class


