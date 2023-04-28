package edu.tcu.cs.superfrogscheduler.reports.payment;

import edu.tcu.cs.superfrogscheduler.reports.dto.EventType;
import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import edu.tcu.cs.superfrogscheduler.reports.entity.PaymentForm;
import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.repository.PaymentFormRepository;
import edu.tcu.cs.superfrogscheduler.reports.service.PaymentService;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock //simulate below obj
    PaymentFormRepository paymentFormRepository;

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    PaymentService paymentService;

    List<PaymentForm> paymentForms;

    @BeforeEach
    void setUp() {
        Period period1 = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));
        PaymentForm paymentForm1 = new PaymentForm(
                "John",
                "Doe",
                "123",
                period1,
                BigDecimal.valueOf(900.75)
        );

        Period period2 = new Period(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 28));
        PaymentForm paymentForm2 = new PaymentForm(
                "Susan",
                "Doe",
                "124",
                period2,
                BigDecimal.valueOf(1200.0)
        );
        this.paymentForms = new ArrayList<>();
        this.paymentForms.add(paymentForm1);
        this.paymentForms.add(paymentForm2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        //given
        given(paymentFormRepository.findAll()).willReturn(this.paymentForms);


        //when
        List<PaymentForm> actualPaymentForms = paymentService.getAllPaymentForms();

        //then
        assertThat(actualPaymentForms.size()).isEqualTo(this.paymentForms.size());
        verify(paymentFormRepository, times(1)).findAll();
    }

    @Test
    public void should_generate_payment_forms_for_SuperFrog_students() {
        // Given
        List<String> appearanceRequestIdList = List.of("5", "6", "12", "16", "17", "20", "22"); // Assume the Spirit Director has selected 7 finished requests for April.

        SuperFrogUser student1 = new SuperFrogUser("Jane", "Smith", "1001"); // First name, last name, and Id

        SuperFrogUser student2 = new SuperFrogUser("John", "Doe", "1004");

        SuperFrogUser student3 = new SuperFrogUser("Tim", "Johnson", "1012");

        List<Request> requests = List.of(
                new Request(
                        "5",
                        EventType.TCU,                                   // The type of the event
                        "Event address 1",                               // Physical address of the event
                        1.4,                                             // Distance between TCU and the event address
                        LocalDate.of(2023, 4, 6),   // Event's date
                        LocalTime.of(13, 0),                  // Event's start time
                        LocalTime.of(15, 30),                 // Event's end time
                        RequestStatus.COMPLETED,                          // Event status
                        student1),                                       // The SuperFrog Student who signed up for the event
                new Request(
                        "6",
                        EventType.NONPROFIT,
                        "Event address 2",
                        2.0,
                        LocalDate.of(2023, 4, 9),
                        LocalTime.of(9, 0),
                        LocalTime.of(12, 0),
                        RequestStatus.COMPLETED,
                        student1),
                new Request(
                        "12",
                        EventType.PRIVATE,
                        "Event address 3",
                        99.0,
                        LocalDate.of(2023, 4, 16),
                        LocalTime.of(19, 30),
                        LocalTime.of(21, 30),
                        RequestStatus.COMPLETED,
                        student1),
                new Request(
                        "16",
                        EventType.PRIVATE,
                        "Event address 4",
                        18.0,
                        LocalDate.of(2023, 4, 17),
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        RequestStatus.COMPLETED,
                        student2),
                new Request(
                        "17",
                        EventType.NONPROFIT,
                        "Event address 5",
                        25.0,
                        LocalDate.of(2023, 4, 19),
                        LocalTime.of(14, 30),
                        LocalTime.of(15, 30),
                        RequestStatus.COMPLETED,
                        student2),
                new Request(
                        "20",
                        EventType.PRIVATE,
                        "Event address 6",
                        50.0,
                        LocalDate.of(2023, 4, 22),
                        LocalTime.of(9, 30),
                        LocalTime.of(14, 30),
                        RequestStatus.COMPLETED,
                        student2),
                new Request(
                        "22",
                        EventType.TCU,
                        "Event address 7",
                        0.6,
                        LocalDate.of(2023, 4, 26),
                        LocalTime.of(17, 0),
                        LocalTime.of(19, 0),
                        RequestStatus.COMPLETED,
                        student3)
        );

        given(this.requestRepository.findByIdIn(appearanceRequestIdList)).willReturn(requests);

        given(this.paymentFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period paymentPeriod = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PaymentForm> paymentForms = this.paymentService.generatePaymentForms(appearanceRequestIdList, paymentPeriod);

        // Then
        assertThat(paymentForms).isNotNull();
        assertThat(paymentForms.size()).isEqualTo(3);
        verify(this.requestRepository, times(1)).findByIdIn(appearanceRequestIdList);
        verify(this.paymentFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void should_generate_payment_forms_for_SuperFrog_students_with_empty_request_list() {
        // Given
        List<String> appearanceRequestIdList = List.of(); // Assume the Spirit Director has selected 0 completed requests for April.

        List<Request> requests = List.of();

        given(this.requestRepository.findByIdIn(appearanceRequestIdList)).willReturn(requests);

        given(this.paymentFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period paymentPeriod = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PaymentForm> paymentForms = this.paymentService.generatePaymentForms(appearanceRequestIdList, paymentPeriod);

        // Then
        assertThat(paymentForms).isNotNull();
        assertThat(paymentForms.size()).isEqualTo(0);
        verify(this.requestRepository, times(1)).findByIdIn(appearanceRequestIdList);
        verify(this.paymentFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void should_generate_payment_forms_for_SuperFrog_students_with_only_one_request() {
        // Given
        List<String> appearanceRequestIdList = List.of("5"); // Assume the Spirit Director has selected only 1 completed request for April.

        SuperFrogUser student1 = new SuperFrogUser("Jane", "Smith", "1001"); // First name, last name, and ID

        List<Request> requests = List.of(
                new Request(
                        "5",
                        EventType.TCU,                                   // The type of the event
                        "Event address 1",                               // Physical address of the event
                        1.4,                                             // Distance between TCU and the event address
                        LocalDate.of(2023, 4, 6),   // Event's date
                        LocalTime.of(13, 0),                  // Event's start time
                        LocalTime.of(15, 30),                 // Event's end time
                        RequestStatus.COMPLETED,                          // Event status
                        student1)                                        // The SuperFrog Student who signed up for the event
        );

        given(this.requestRepository.findByIdIn(appearanceRequestIdList)).willReturn(requests);

        given(this.paymentFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period paymentPeriod = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PaymentForm> paymentForms = this.paymentService.generatePaymentForms(appearanceRequestIdList, paymentPeriod);

        // Then
        assertThat(paymentForms).isNotNull();
        assertThat(paymentForms.size()).isEqualTo(1);
        verify(this.requestRepository, times(1)).findByIdIn(appearanceRequestIdList);
        verify(this.paymentFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }

}

