package edu.tcu.cs.superfrogscheduler.reports.performance;

import edu.tcu.cs.superfrogscheduler.reports.dto.EventType;
import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import edu.tcu.cs.superfrogscheduler.reports.repository.PerformanceFormRepository;
import edu.tcu.cs.superfrogscheduler.reports.service.PerformanceService;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
class PerformanceServiceTest {

    @Mock //simulate below obj
    PerformanceFormRepository performanceFormRepository;

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    PerformanceService performanceService;

    List<PerformanceForm> performanceForms;

    @BeforeEach
    void setUp() {
        Period period1 = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));
        PerformanceForm performanceForm1 = new PerformanceForm(
                period1,
                "John",
                "Doe",
                "123",
                7
        );

        Period period2 = new Period(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 28));
        PerformanceForm performanceForm2 = new PerformanceForm(
                period2,
                "Hanna",
                "Sigman",
                "124",
                3
        );
        this.performanceForms = new ArrayList<>();
        this.performanceForms.add(performanceForm1);
        this.performanceForms.add(performanceForm2);

        SuperFrogUser student = new SuperFrogUser("Hanna", "Sigman", "124");

        Request request1 = new Request(
                "5",
                EventType.TCU,
                "Event address 1",
                1.4,
                LocalDate.of(2023, 4, 6),
                LocalTime.of(13, 0),
                LocalTime.of(15, 30),
                RequestStatus.COMPLETED,
                student);
        Request request2 = new Request(
                "6",
                EventType.NONPROFIT,
                "Event address 2",
                2.0,
                LocalDate.of(2023, 4, 9),
                LocalTime.of(9, 0),
                LocalTime.of(12, 0),
                RequestStatus.COMPLETED,
                student);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        //given
        given(performanceFormRepository.findAll()).willReturn(this.performanceForms);

        //when
        List<PerformanceForm> actualPaymentForms = performanceService.getAllPerformanceForms();

        //then
        assertThat(actualPaymentForms.size()).isEqualTo(this.performanceForms.size());
        verify(performanceFormRepository, times(1)).findAll();
    }

    @Test
    public void should_generate_performance_forms_for_SuperFrog_students() {
        // Given
        List<String> studentIdList = List.of("1001", "1004", "1012"); // Assume the Spirit Director has selected 7 finished requests for April.

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

        given(this.requestRepository.findByAssignedSuperFrogStudentIn(studentIdList)).willReturn(requests);

        given(this.performanceFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period period = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PerformanceForm> performanceForms = this.performanceService.generatePerformanceForms(studentIdList, period);

        // Then
        assertThat(performanceForms).isNotNull();
        assertThat(performanceForms.size()).isEqualTo(3);
        verify(this.requestRepository, times(1)).findByAssignedSuperFrogStudentIn(studentIdList);
        verify(this.performanceFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void should_generate_payment_forms_for_SuperFrog_students_with_empty_request_list() {
        // Given
        List<String> studentIdList = List.of(); // Assume the Spirit Director has selected 0 completed requests for April.

        List<Request> requests = List.of();

        given(this.requestRepository.findByAssignedSuperFrogStudentIn(studentIdList)).willReturn(requests);

        given(this.performanceFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period period = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PerformanceForm> performanceForms = this.performanceService.generatePerformanceForms(studentIdList, period);

        // Then
        assertThat(performanceForms).isNotNull();
        assertThat(performanceForms.size()).isEqualTo(0);
        verify(this.requestRepository, times(1)).findByAssignedSuperFrogStudentIn(studentIdList);
        verify(this.performanceFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void should_generate_payment_forms_for_SuperFrog_students_with_only_one_request() {
        // Given
        List<String> studentIdList = List.of("1001"); // Assume the Spirit Director has selected only 1 completed request for April.

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

        given(this.requestRepository.findByAssignedSuperFrogStudentIn(studentIdList)).willReturn(requests);

        given(this.performanceFormRepository.saveAll(anyList())).will(returnsFirstArg()); // Return the input argument.

        Period period = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        // When
        List<PerformanceForm> performanceForms = this.performanceService.generatePerformanceForms(studentIdList, period);

        // Then
        assertThat(performanceForms).isNotNull();
        assertThat(performanceForms.size()).isEqualTo(1);
        verify(this.requestRepository, times(1)).findByAssignedSuperFrogStudentIn(studentIdList);
        verify(this.performanceFormRepository, times(1)).saveAll(Mockito.any(List.class));
    }
}

