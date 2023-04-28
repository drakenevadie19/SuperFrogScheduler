package edu.tcu.cs.superfrogscheduler.reports.controller;

import edu.tcu.cs.superfrogscheduler.reports.converter.PerformanceFormDtoToPerformanceFormConverter;
import edu.tcu.cs.superfrogscheduler.reports.converter.PerformanceFormToPerformanceFormDtoConverter;
import edu.tcu.cs.superfrogscheduler.reports.dto.PerformanceFormDto;
import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import edu.tcu.cs.superfrogscheduler.reports.dto.RequestIds;
import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.service.PerformanceService;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}")
public class PerformanceController {

    private PerformanceService performanceService;

    private final PerformanceFormToPerformanceFormDtoConverter performanceFormToPerformanceFormDtoConverter;

    private final PerformanceFormDtoToPerformanceFormConverter performanceFormDtoToPerformanceFormConverter;


    public PerformanceController(PerformanceService performanceService, PerformanceFormToPerformanceFormDtoConverter performanceFormToPerformanceFormDtoConverter, PerformanceFormDtoToPerformanceFormConverter performanceFormDtoToPerformanceFormConverter) {
        this.performanceService = performanceService;
        this.performanceFormToPerformanceFormDtoConverter = performanceFormToPerformanceFormDtoConverter;
        this.performanceFormDtoToPerformanceFormConverter = performanceFormDtoToPerformanceFormConverter;
    }

    @GetMapping("/performance-forms")
    public Result getAllRequests() {
        List<PerformanceForm> performanceForms = performanceService.getAllPerformanceForms();
        List<PerformanceFormDto> performanceFormDtos = performanceForms.stream()
                .map(this.performanceFormToPerformanceFormDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Find All Success", performanceFormDtos );
    }

    @PostMapping("/performance-forms")
    public Result generatePerformanceForms(@RequestBody RequestIds requestIds) {

        List<String> selectedStudentIds = requestIds.getRequestIds();

        Period performancePeriod = requestIds.getPeriod();

        List<PerformanceForm> performanceForms = this.performanceService.generatePerformanceForms(selectedStudentIds, performancePeriod);

        List<PerformanceFormDto> performanceFormDtos = performanceForms.stream()
                .map(this.performanceFormToPerformanceFormDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Performance forms are generated successfully.", performanceFormDtos);
    }
}
