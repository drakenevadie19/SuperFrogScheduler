package edu.tcu.cs.superfrogscheduler.reports.repository;

import edu.tcu.cs.superfrogscheduler.reports.entity.PerformanceForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceFormRepository extends JpaRepository<PerformanceForm, Integer> {
}
