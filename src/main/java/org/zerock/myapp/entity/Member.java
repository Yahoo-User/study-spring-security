package org.zerock.myapp.entity;

import org.zerock.myapp.util.EntityLifecycleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@Entity
@EntityListeners(EntityLifecycleListener.class)

@Table(name = "member")
public class Member {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private String user;

	@Column(name = "password", nullable = false)
	private String pass;
	
	@Basic(optional = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	private Role role;
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Basic(optional = false)
	private Boolean enabled;
	
	
	
} // end class

