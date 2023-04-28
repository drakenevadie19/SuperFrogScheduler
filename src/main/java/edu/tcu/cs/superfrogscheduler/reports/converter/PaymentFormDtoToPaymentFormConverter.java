package edu.tcu.cs.superfrogscheduler.reports.converter;

import edu.tcu.cs.superfrogscheduler.reports.PaymentForm;
import edu.tcu.cs.superfrogscheduler.reports.dto.PaymentFormDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentFormDtoToPaymentFormConverter implements Converter<PaymentFormDto, PaymentForm> {
    @Override
    public PaymentForm convert(PaymentFormDto source) {
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setPaymentFormId(source.id());
        paymentForm.setFirstName(source.firstName());
        paymentForm.setLastName(source.lastName());
        paymentForm.setStudentId(source.studentId());
        paymentForm.setPaymentPeriod(source.paymentPeriod());
        paymentForm.setAmount(source.amount());
        return paymentForm;
    }
}
