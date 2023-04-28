package edu.tcu.cs.superfrogscheduler.user.entity;

import edu.tcu.cs.superfrogscheduler.reports.EventType;
import edu.tcu.cs.superfrogscheduler.reports.PaymentForm;
import edu.tcu.cs.superfrogscheduler.reports.Period;
import edu.tcu.cs.superfrogscheduler.reports.TransportationFeeCalculator;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.PaymentPreference;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class SuperFrogUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "superFrogUser")
    private UserSecurity userSecurity;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "superFrogUser")
    private List<Request> requests;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean isInternationalStudent;

    @Enumerated(EnumType.STRING)
    private PaymentPreference paymentPreference;

    public SuperFrogUser() {
    }

    public SuperFrogUser(String firstName, String lastName, String id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String userDetailsId) {
        this.id = userDetailsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserSecurity getUserSecurity() {
        return userSecurity;
    }

    public void setUserSecurity(UserSecurity user) {
        this.userSecurity = user;
    }

    public Boolean getIsInternationalStudent() {
        return this.isInternationalStudent;
    }

    public void setIsInternationalStudent(Boolean internationalStudent) {
        this.isInternationalStudent = internationalStudent;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public PaymentPreference getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(PaymentPreference paymentPreference) {
        this.paymentPreference = paymentPreference;
    }

    public PaymentForm generatePaymentForm(List<Request> requests, Period paymentPeriod) {
        Map<EventType, Double> eventTypeHoursMap = requests.stream().collect(Collectors.groupingBy(request -> request.getEventType(),
                Collectors.mapping(request -> request.getStartTime().until(request.getEndTime(), ChronoUnit.MINUTES) / 60.0,
                        Collectors.reducing(0.0, Double::sum))));

        BigDecimal totalAppearanceFee = new BigDecimal(0.0);

        // Calculate the total appearance fee by going over the map.
        for (Map.Entry<EventType, Double> entry : eventTypeHoursMap.entrySet()) {
            totalAppearanceFee = totalAppearanceFee
                    .add(BigDecimal.valueOf(entry.getKey().getHourlyRate())
                            .multiply(BigDecimal.valueOf(entry.getValue())));
        }

        // We also need to consider transportation fee.
        BigDecimal transportationFee = TransportationFeeCalculator.INSTANCE.calculateTransportationFee(requests);

        BigDecimal totalAmount = totalAppearanceFee.add(transportationFee);

        return new PaymentForm(this.firstName, this.lastName, this.id, paymentPeriod, totalAmount);
    }
}
