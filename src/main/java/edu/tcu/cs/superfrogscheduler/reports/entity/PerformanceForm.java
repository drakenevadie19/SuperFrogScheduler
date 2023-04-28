package edu.tcu.cs.superfrogscheduler.reports.entity;

import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PerformanceForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer performanceFormId;

    private Period performancePeriod;

    private String firstName;

    private String lastName;

    private String studentId;

    private Integer completedRequests;

    public PerformanceForm() {
    }

    public PerformanceForm(Period performancePeriod, String firstName, String lastName, String studentId, Integer completedRequests) {
        this.performancePeriod = performancePeriod;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.completedRequests = completedRequests;
    }

    public Integer getPerformanceFormId() {
        return performanceFormId;
    }

    public void setPerformanceFormId(Integer performanceFormId) {
        this.performanceFormId = performanceFormId;
    }

    public Period getPerformancePeriod() {
        return performancePeriod;
    }

    public void setPerformancePeriod(Period performancePeriod) {
        this.performancePeriod = performancePeriod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getCompletedRequests() {
        return completedRequests;
    }

    public void setCompletedRequests(Integer completedRequests) {
        this.completedRequests = completedRequests;
    }
}
