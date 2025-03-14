package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class UpfileDto implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private Integer id;
	private String original;
	private String uuid;
	private String path;
	private Boolean enabled;

	
} // end class
