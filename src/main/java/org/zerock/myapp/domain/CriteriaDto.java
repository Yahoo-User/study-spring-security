package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data

/**
 * ==========================
 * (1) 과정 목록
 * ==========================
 * - 기본 조건: enabled = 1
 * - 첫번째 조건 : status (상태(등록=1,진행중=2,폐지=3,종료=4))
 * - 두번째 조건 : type   (과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정))
 * - 세번째 조건 : name(과정명) 또는 t_instructors.name (강사명) <--- ***
 * 
 * ==========================
 * (2) 강사 목록
 * ==========================
 * - 기본 조건: enabled = 1
 * - 첫번째 조건 : status   (상태(등록=1,강의중=2,퇴사=3))
 * - 두번째 조건 : name(강사명) 또는 tel(전번)
 * 
 * ==========================
 * (3) 훈련생 목록
 * ==========================
 * - 기본 조건: enabled = 1
 * - 첫번째 조건 : status   (상태(훈련중=1,중도탈락=2,중도포기=3,취업완료=4))
 * - 두번째 조건 : name(훈련생명) 또는 tel(전번)
 * 
 */
public class CriteriaDto implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private Integer page = 1;
	private Integer pageSize = 20;
	
	
	
	
} // end class

