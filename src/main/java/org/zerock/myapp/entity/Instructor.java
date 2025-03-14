package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data

@JsonIgnoreProperties({
	"course",
	"files",
	
	"insertTs",		// Legal audit property
	"updateTs",		// Legal audit property
})

@Entity
//@EntityListeners(EntityLifecycleListener.class)

@Table(name="t_instructors")
public class Instructor implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	
	// 2-1. General Properties
	@Column(nullable = false, length = 255)	// Not Null Constraint
	private String name;

	@Column(nullable = false, length = 50)		// Not Null Constraint
	private String tel;

	@Basic(optional = false)			// Not Null Constraint
	private Integer status = 1;		// Default is 1 (Registered)

	@Convert(converter = BooleanToIntegerConverter.class)
	@Basic(optional = false)			// Not Null Constraint
	private Boolean enabled = true;		// Default is true.

	// 2-2. Iegal Auditing Properties
//	@JsonIgnore
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="insert_ts", nullable=false)
	private Date insertTs;

//	@JsonIgnore
	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="update_ts")
	private Date updateTs;

	
	// =======================================
	// 3. Bi-directional Many-To-One Association
	// =======================================	
	@ToString.Exclude
	
	@OneToOne
	@JoinColumn(name="crs_id")
	private Course course;

	// =======================================
	// 4. Bi-directional One-To-Many Association
	// =======================================
	@ToString.Exclude
	
	@OneToMany(mappedBy="instructor")
	private List<Upfile> files = new Vector<>();

	
	public Upfile addUpfile(Upfile file) {
		this.files.add(file);
		file.setInstructor(this);

		return file;
	}

	public Upfile removeUpfile(Upfile file) {
		this.files.remove(file);
		file.setInstructor(null);

		return file;
	}

	
	
} // end class
