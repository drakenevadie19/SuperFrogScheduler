package edu.tcu.cs.superfrogscheduler.reports.converter;

import edu.tcu.cs.superfrogscheduler.reports.entity.PaymentForm;
import edu.tcu.cs.superfrogscheduler.reports.dto.PaymentFormDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentFormToPaymentFormDtoConverter implements Converter<PaymentForm, PaymentFormDto> {
    @Override
    public PaymentFormDto convert(PaymentForm source) {
        PaymentFormDto paymentFormDto = new PaymentFormDto(
                source.getPaymentFormId(),
                source.getFirstName(),
                source.getLastName(),
                source.getStudentId(),
                source.getPaymentPeriod(),
                source.getAmount()
        );
        return paymentFormDto;
    }
}
