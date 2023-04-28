package edu.tcu.cs.superfrogscheduler.reports;

import edu.tcu.cs.superfrogscheduler.request.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock //simulate below obj
    PaymentFormRepository paymentFormRepository;

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
}

