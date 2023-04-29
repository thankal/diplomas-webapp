package com.example.thesisapp.model;

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
@Table(name="students")
public class Student{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	private Long id;
	
	@Column(name="first_name")
	private String first_name;

	@Column(name="last_name")
	private String last_name;

	@Column(name="year_of_studies")
	private int year_of_studies;

	@Column(name="average_grade")
	private double average_grade;
	
	@Column(name="remaining_courses")
	private int remaining_courses;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getYear_of_studies() {
		return year_of_studies;
	}

	public void setYear_of_studies(int year_of_studies) {
		this.year_of_studies = year_of_studies;
	}

	public double getAverage_grade() {
		return average_grade;
	}

	public void setAverage_grade(double average_grade) {
		this.average_grade = average_grade;
	}

	public int getRemaining_courses() {
		return remaining_courses;
	}

	public void setRemaining_courses(int remaining_courses) {
		this.remaining_courses = remaining_courses;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

