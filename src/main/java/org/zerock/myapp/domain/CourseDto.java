package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class CourseDto implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private Integer id;	
	private Integer type = 4;					// 과정구분(1=NCS, 2=KDT, 3=산대특, 4=미정)
	private String name;
	private Integer capacity = 0;
	private String detail;
	private Boolean enabled = true;		// 삭제여부(1=유효,0=삭제)
	private String startDate;
	private String endDate;
	private Integer status = 1;					// 상태(등록=1,진행중=2,폐지=3,종료=4)
	
	
	
} // end class

