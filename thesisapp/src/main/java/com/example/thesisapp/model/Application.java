package com.example.thesisapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="application_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_fk", referencedColumnName = "student_id")
	private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thesis_fk", referencedColumnName = "thesis_id")
	private Thesis thesis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Thesis getThesis() {
        return thesis;
    }

    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }


	
}



