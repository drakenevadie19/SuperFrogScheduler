package edu.tcu.cs.superfrogscheduler.user.dto;

import edu.tcu.cs.superfrogscheduler.user.entity.utils.PaymentPreference;
import jakarta.validation.constraints.NotNull;

public class UserInfoDto extends UserDto {

    @NotNull(message = "isInternational is required")
    private Boolean isInternational;

    @NotNull(message = "Payment preference is required")
    private PaymentPreference paymentPreference;

    public Boolean getIsInternational() {
        return isInternational;
    }

    public void setIsInternational(boolean international) {
        this.isInternational = international;
    }

    public PaymentPreference getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(PaymentPreference paymentPreference) {
        this.paymentPreference = paymentPreference;
    }

}
