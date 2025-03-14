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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data

@JsonIgnoreProperties({
//	"instructor",
//	"trainees",
//	"files",
	
	"insertTs",		// Legal audit property
	"updateTs"		// Legal audit property
})		// `@JsonIgnore` for each property to exclude JSON conversion.

@Entity
//@EntityListeners(EntityLifecycleListener.class)

@Table(name="t_courses")
public class Course implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	
	// 2-1. General Properties
	@Basic(optional = false)			// Not Null Constraint
	private Integer type = 4;		// Default is 4.

	@Column(nullable = false, length = 500)	// Not Null Constraint
	private String name;
	
	@Basic(optional = false)			// Not Null Constraint
	private Integer capacity = 0;	// Default is 0.

	@Column(nullable = false, length = 4000)	// Not Null Constraint
	private String detail;

	@Convert(converter = BooleanToIntegerConverter.class)
	@Basic(optional = false)			// Not Null Constraint
	private Boolean enabled = true;		// Default is true.

	@Column(nullable = false, length = 50)	// Not Null Constraint
	private String startDate;

	@Column(nullable = false, length = 50)	// Not Null Constraint
	private String endDate;

	@Basic(optional = false)			// Not Null Constraint
	private Integer status = 1;		// Default is 1 (Registered)

	// 2-2. Iegal Auditing Properties
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="insert_ts", nullable=false)
	private Date insertTs;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="update_ts")
	private Date updateTs;

	
	// =======================================
	// 3. Bi-directional One-To-One Association
	// =======================================
	@OneToOne(mappedBy="course", cascade = CascadeType.ALL)
	private Instructor instructor;
	
	// =======================================
	// 4. Bi-directional Many-To-One Association
	// =======================================
	@OneToMany(mappedBy="course", cascade = CascadeType.ALL)
	private List<Trainee> trainees = new Vector<>();

	@OneToMany(mappedBy="course", cascade = CascadeType.ALL)
	private List<Upfile> files = new Vector<>();
	

	// Association Mapping Convenient Method - 1
	public Trainee addTrainee(Trainee trainee) {
		this.trainees.add(trainee);
		trainee.setCourse(this);

		return trainee;
	}

	// Association Mapping Convenient Method - 1
	public Trainee removeTrainee(Trainee trainee) {
		this.trainees.remove(trainee);
		trainee.setCourse(null);

		return trainee;
	}	

	// Association Mapping Convenient Method - 2
	public Upfile addUpfile(Upfile file) {
		this.files.add(file);
		file.setCourse(this);

		return file;
	}

	// Association Mapping Convenient Method - 2
	public Upfile removeUpfile(Upfile file) {
		this.files.remove(file);
		file.setCourse(null);

		return file;
	}

	
	
} // end class

