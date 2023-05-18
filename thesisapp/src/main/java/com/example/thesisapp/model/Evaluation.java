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
@Table(name="grades")
public class Evaluation{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="eval_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "assignment_fk", referencedColumnName = "assignment_id")
	private Assignment assignment;

    @Column(name="implementation")
    private double implementation;

    @Column(name="report")
    private double report;

    @Column(name="presentation")
    private double presentation;

    @Column(name="total")
    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public double getImplementation() {
        return implementation;
    }

    public void setImplementation(double implementation) {
        this.implementation = implementation;
    }

    public double getReport() {
        return report;
    }

    public void setReport(double report) {
        this.report = report;
    }

    public double getPresentation() {
        return presentation;
    }

    public void setPresentation(double presentation) {
        this.presentation = presentation;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


	
	
}


