package org.zerock.myapp.persistence.spec;

import org.springframework.data.jpa.domain.Specification;
import org.zerock.myapp.entity.Course;

import lombok.extern.slf4j.Slf4j;


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

@Slf4j
public class CourseSpecifications {		// Generate Each Dynamic Search Specification. (***)

	/**
	 * =======================================
	 * org.springframework.data.jpa.domain.Specification:
	 * 		Predicate toPredicate(Root<T> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
	 * =======================================
	 */
	public static Specification<Course> isEnabled(Boolean enabled) {
		log.debug(">>> isEnabled({}) invoked.", enabled);
			
		/**
		Specification<Course> spec = (root, query, builder) -> {
			// root : 검색해야할 Course 엔티티 객체
//			root.get("엔티티속성명");		// 엔티티객체(root)에서, 지정된 속성값을 얻어냄
			
			// 조건식의 구성 : <기준컬럼: root.get(name)> <비교연산자: builder.비교메소드> <비교값: 매개변수값>
			return builder.equal(root.get("enabled"), enabled);
		};
		*/

		// SQL : FROM t_courses WHERE enabled = 1
		return (root, query, builder) -> builder.equal(root.get("enabled"), enabled);
	}
	
//					    하나의 check조건 (SQL로 치면, WHERE절에 추가될 하나의 조건식)
//						=================
	public static Specification<Course> hasName(String name) {
		log.debug(">>> hasName({}) invoked.", name);
		
		// SQL : FROM t_courses WHERE name LIKE '%' || "name" || '%'
		return (root, query, builder) -> builder.like(root.get("name"), '%' + name + '%');
	}
	
	public static Specification<Course> hasStatus(Integer status) {
		log.debug(">>> hasStatus({}) invoked.", status);
		
		// SQL : FROM t_courses WHERE status = 2
		return (root, query, builder) -> builder.equal(root.get("status"), status);
	}
	
	public static Specification<Course> hasType(Integer type) {
		log.debug(">>> hasType({}) invoked.", type);
		
		// SQL : FROM t_courses WHERE type  =  2(요청값인, type)
		return (root, query, builder) -> builder.equal(root.get("type"), type);
	}
	
} // end class


