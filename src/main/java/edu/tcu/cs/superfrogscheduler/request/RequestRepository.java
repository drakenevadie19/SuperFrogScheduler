package edu.tcu.cs.superfrogscheduler.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

    //List<Request> findByRequestId(List<Integer> appearanceRequestIdList);

    List<Request> findByRequestStatus (RequestStatus requestStatus);  //changed from findByStatus to findByRequestStatus to match Request entity (error)
}

