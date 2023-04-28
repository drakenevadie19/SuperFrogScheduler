package edu.tcu.cs.superfrogscheduler.reports.service;

import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.repository.PerformanceFormRepository;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    private UserService userService;

    private UserRepository userRepository;

    private RequestRepository requestRepository;

    private PerformanceFormRepository performanceFormRepository;

    public PerformanceService(UserService userService, UserRepository userRepository, RequestRepository requestRepository, PerformanceFormRepository performanceFormRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.performanceFormRepository = performanceFormRepository;
    }

    public List<PerformanceForm> getAllPerformanceForms() {
        return performanceFormRepository.findAll();
    }

    public List<PerformanceForm> generatePerformanceForms(List<String> IdList, Period period) {
        List<Request> requests = this.requestRepository.findByAssignedSuperFrogStudentIn(IdList);

        Map<SuperFrogUser, List<Request>> studentRequestsMap = groupRequestsBySuperFrogStudent(requests);

        // For each SuperFrogStudent, generate a payment form, and then collect the payment forms into a list.
        List<PerformanceForm> performanceForms = studentRequestsMap.entrySet().stream()
                .map(entry -> entry.getKey().generatePerformanceForm(entry.getValue(), period))
                .collect(Collectors.toList());

        // Persist the generated payment forms and then return them.
        return this.performanceFormRepository.saveAll(performanceForms);
    }


    private Map<SuperFrogUser, List<Request>> groupRequestsBySuperFrogStudent(List<Request> selectedRequests) {
        Map<SuperFrogUser, List<Request>> studentRequestsMap = selectedRequests.stream()
                .collect(Collectors.groupingBy(Request::getSuperFrogUser));
        return studentRequestsMap;
    }
}
