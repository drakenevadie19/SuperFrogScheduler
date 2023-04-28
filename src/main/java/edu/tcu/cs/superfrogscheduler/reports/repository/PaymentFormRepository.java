package edu.tcu.cs.superfrogscheduler.reports.repository;

import edu.tcu.cs.superfrogscheduler.reports.entity.PaymentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentFormRepository extends JpaRepository<PaymentForm, Integer> {
}
