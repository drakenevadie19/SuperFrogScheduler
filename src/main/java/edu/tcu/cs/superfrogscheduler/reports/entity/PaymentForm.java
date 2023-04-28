package edu.tcu.cs.superfrogscheduler.reports.entity;

import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * A payment form needs to be generated for each SuperFrog Student so that
 * she can get paid by TCU Payroll.
 * <p>
 * The amount is calculated based on
 * 1. number of appearances done within a given start and end date
 * 2. event type
 * 3. hours
 * 4. mileage (distance between TCU and the event address, the first 2 miles are free of charge, then $0.75/mile)
 */
@Entity
public class PaymentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentFormId;

    private String firstName;

    private String lastName;

    private String studentId;

    @Embedded
    private Period paymentPeriod;

    private BigDecimal amount;


    public PaymentForm() {

    }

    public PaymentForm(String firstName, String lastName, String studentId, Period paymentPeriod, BigDecimal amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.paymentPeriod = paymentPeriod;
        this.amount = amount;
    }

    public Integer getPaymentFormId() {
        return paymentFormId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPaymentFormId(Integer paymentFormId) {
        this.paymentFormId = paymentFormId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPaymentPeriod(Period paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
