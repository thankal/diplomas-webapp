package com.example.thesisapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="thesis")
public class Thesis{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="thesis_id")
	private Long id;
	
	@Column(name="title")
	private String title;

	@Column(name="objectives")
	private String objectives;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "prof_fk", referencedColumnName = "prof_id")
	private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objecives) {
        this.objectives = objecives;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

	
	
}


