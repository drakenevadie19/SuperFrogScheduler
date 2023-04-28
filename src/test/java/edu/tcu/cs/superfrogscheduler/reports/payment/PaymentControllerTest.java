package edu.tcu.cs.superfrogscheduler.reports.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.superfrogscheduler.reports.dto.EventType;
import edu.tcu.cs.superfrogscheduler.reports.dto.Period;
import edu.tcu.cs.superfrogscheduler.reports.dto.RequestIds;
import edu.tcu.cs.superfrogscheduler.reports.entity.PaymentForm;
import edu.tcu.cs.superfrogscheduler.reports.service.PaymentService;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Payment APIs")
@Tag("integration")
public class PaymentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PaymentService paymentService;

    SuperFrogUser student1, student2, student3;

    Request request1, request2, request3, request4, request5, request6, request7;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

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
                BigDecimal.valueOf(900.75)
        );
        this.paymentForms = new ArrayList<>();
        this.paymentForms.add(paymentForm1);
        this.paymentForms.add(paymentForm2);

        student1 = new SuperFrogUser("Jane", "Smith", "1001");
        student2 = new SuperFrogUser("John", "Doe", "1004");
        student3 = new SuperFrogUser("Tim", "Johnson", "1012");

        request1 = new Request(
                "5",
                EventType.TCU,
                "Event address 1",
                1.4,
                LocalDate.of(2023, 4, 6),
                LocalTime.of(13, 0),
                LocalTime.of(15, 30),
                RequestStatus.COMPLETED,
                student1);
        request2 = new Request(
                "6",
                EventType.NONPROFIT,
                "Event address 2",
                2.0,
                LocalDate.of(2023, 4, 9),
                LocalTime.of(9, 0),
                LocalTime.of(12, 0),
                RequestStatus.COMPLETED,
                student1);
        request3 = new Request(
                "12",
                EventType.PRIVATE,
                "Event address 3",
                99.0,
                LocalDate.of(2023, 4, 16),
                LocalTime.of(19, 30),
                LocalTime.of(21, 30),
                RequestStatus.COMPLETED,
                student1);
        request4 = new Request(
                "16",
                EventType.PRIVATE,
                "Event address 4",
                18.0,
                LocalDate.of(2023, 4, 17),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                RequestStatus.COMPLETED,
                student2);
        request5 = new Request(
                "17",
                EventType.NONPROFIT,
                "Event address 5",
                25.0,
                LocalDate.of(2023, 4, 19),
                LocalTime.of(14, 30),
                LocalTime.of(15, 30),
                RequestStatus.COMPLETED,
                student2);
        request6 = new Request(
                "20",
                EventType.PRIVATE,
                "Event address 6",
                50.0,
                LocalDate.of(2023, 4, 22),
                LocalTime.of(9, 30),
                LocalTime.of(14, 30),
                RequestStatus.COMPLETED,
                student2);
        request7 = new Request(
                "22",
                EventType.TCU,
                "Event address 7",
                0.6,
                LocalDate.of(2023, 4, 26),
                LocalTime.of(17, 0),
                LocalTime.of(19, 0),
                RequestStatus.COMPLETED,
                student3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAllRequestsSuccess() throws Exception {
        //given
        given(this.paymentService.getAllPaymentForms()).willReturn(this.paymentForms);

        //when and then
        this.mockMvc.perform(get(this.baseUrl+"/payment-forms").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.paymentForms.size())))
                .andExpect(jsonPath("$.data[0].studentId").value("123"))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[1].studentId").value("124"))
                .andExpect(jsonPath("$.data[1].firstName").value("Susan"));
    }

    /* won't work unless there are actually users and reports saved in the database */
    @Test
    public void should_generate_payments_form_for_SuperFrog_students() throws Exception {
        // Given
        List<String> selectedAppearanceRequestIds = List.of("5", "6", "12", "16", "17", "20", "22"); // Assume the Spirit Director has selected 7 completed requests for April.

        Period paymentPeriod = new Period(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 30));

        RequestIds requestIds = new RequestIds(selectedAppearanceRequestIds, paymentPeriod);

        String json = this.objectMapper.writeValueAsString(requestIds);

        // When and then
        this.mockMvc.perform(post("/api/v1/payment-forms").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Payment forms are generated successfully."))
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

}
