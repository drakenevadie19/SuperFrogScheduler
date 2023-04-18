package edu.tcu.cs.superfrogscheduler.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    //List<Request> findByRequestStatus(RequestStatus requestStatus);
}

