package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class TraineeDto implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String tel;
	private Integer status;
	private Boolean enabled;

	
} // end class

