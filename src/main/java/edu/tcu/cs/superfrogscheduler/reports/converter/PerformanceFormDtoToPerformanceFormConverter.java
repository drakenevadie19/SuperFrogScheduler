package edu.tcu.cs.superfrogscheduler.reports.converter;

import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.dto.PerformanceFormDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PerformanceFormDtoToPerformanceFormConverter implements Converter<PerformanceFormDto, PerformanceForm> {

    @Override
    public PerformanceForm convert(PerformanceFormDto source) {
        PerformanceForm performanceForm = new PerformanceForm();
        performanceForm.setPerformanceFormId(source.performanceFormId());
        performanceForm.setPerformancePeriod(source.performancePeriod());
        performanceForm.setFirstName(source.firstName());
        performanceForm.setLastName(source.lastName());
        performanceForm.setStudentId(source.studentId());
        performanceForm.setCompletedRequests(source.completedRequests());
        return performanceForm;
    }
}
