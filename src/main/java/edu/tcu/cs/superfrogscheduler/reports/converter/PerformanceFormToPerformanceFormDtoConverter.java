package edu.tcu.cs.superfrogscheduler.reports.converter;

import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.dto.PerformanceFormDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PerformanceFormToPerformanceFormDtoConverter implements Converter<PerformanceForm, PerformanceFormDto> {

    @Override
    public PerformanceFormDto convert(PerformanceForm source) {
        PerformanceFormDto performanceFormDto = new PerformanceFormDto(
                source.getPerformanceFormId(),
                source.getPerformancePeriod(),
                source.getFirstName(),
                source.getLastName(),
                source.getStudentId(),
                source.getCompletedRequests()
        );
        return performanceFormDto;
    }
}
