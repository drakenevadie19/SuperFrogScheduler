package edu.tcu.cs.superfrogscheduler.reports.dto;

public record PerformanceFormDto (
        Integer performanceFormId,
        Period performancePeriod,
        String firstName,
        String lastName,
        String studentId,
        Integer completedRequests
) {
}
