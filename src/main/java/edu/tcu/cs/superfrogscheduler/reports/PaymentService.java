package edu.tcu.cs.superfrogscheduler.reports;

import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {


    private UserService userService;

    private RequestRepository requestRepository;

    private PaymentFormRepository paymentFormRepository;


    public PaymentService(UserService userService, RequestRepository requestRepository, PaymentFormRepository paymentFormRepository) {
        this.userService = userService;
        this.requestRepository = requestRepository;
        this.paymentFormRepository = paymentFormRepository;
    }

    public List<PaymentForm> getAllPaymentForms() {
        return paymentFormRepository.findAll();
    }

    public List<PaymentForm> generatePaymentForms(List<String> IdList, Period paymentPeriod) {
        List<Request> selectedRequests = this.requestRepository.findByIdIn(IdList);

        Map<String, List<Request>> studentRequestsMap = groupRequestsBySuperFrogStudent(selectedRequests);

        // For each SuperFrogStudent, generate a payment form, and then collect the payment forms into a list.
        List<PaymentForm> paymentForms = studentRequestsMap.entrySet().stream()
                .map(entry -> userService.findStudentById(entry.getKey()).generatePaymentForm(entry.getValue(), paymentPeriod))
                .collect(Collectors.toList());

        // Persist the generated payment forms and then return them.
        return this.paymentFormRepository.saveAll(paymentForms);
    }

    /**
     * Group the given requests by SuperFrogStudent who has finished this request.
     * The result is a Map<SuperFrogStudent, List<SuperFrogAppearanceRequest>>.
     * For example:
     *  Jane Smith -> request 5, request 6, request 12
     *  John Doe -> request 16, request 17, request 20
     *  Jane Smith -> request 22
     * @param selectedRequests A list of integer request ids.
     * @return A map that maps SuperFrogStudent to her requests
     */
    private Map<String, List<Request>> groupRequestsBySuperFrogStudent(List<Request> selectedRequests) {
        Map<String, List<Request>> studentRequestsMap = selectedRequests.stream()
                .collect(Collectors.groupingBy(Request::getAssignedSuperFrogStudent));
        return studentRequestsMap;
    }

}
