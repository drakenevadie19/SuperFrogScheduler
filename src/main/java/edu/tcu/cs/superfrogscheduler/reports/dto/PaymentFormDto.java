package edu.tcu.cs.superfrogscheduler.reports.dto;


import java.math.BigDecimal;

public record PaymentFormDto (
    Integer id,
    String firstName,
    String lastName,
    String studentId,
    Period paymentPeriod,
    BigDecimal amount
    ) {
}
