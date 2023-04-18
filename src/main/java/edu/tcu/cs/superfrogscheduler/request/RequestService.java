package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.system.exception.BadRequestException;
import edu.tcu.cs.superfrogscheduler.system.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public List<Request> getAllRequests() { //UC 6 - return a list of all Requests
        return requestRepository.findAll();
    }

    public Request getRequestById(Long id) { //UC 6 - return a Request by id
        return requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Request", "id", id));
    }

    public void createRequest(Request request) {
        request.setRequestStatus(RequestStatus.PENDING);
        requestRepository.save(request);
    }

    //update, pass request, requestRepository.save(request)
    public void updateRequest(Request request){
        requestRepository.save(request);
    }

    //delete, pass id, request Request = getReqById(id),   requestRepository.delete(request)
    public void delete(long id){
        Request request = getRequestById(id);
        requestRepository.delete(request);
    }

    public void signupForRequest(Long requestId, SuperFrog superFrog){ //pass a requestId and superFrog
        Request request = getRequestById(requestId); //grab the request @ id

        //If request is not approved, cant accept
        if(request.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can sign up for it");
        }

        //if there's already a super frog signed up, cant accept
        if(request.getAssignedSuperFrogStudent() != null){
            throw new BadRequestException("This Request has a Super Frog already signed up for it");
        }

        //assign the super frog to the request, then update
        request.setAssignedSuperFrogStudent(superFrog.getFirstName()+" "+superFrog.getLastName);
        updateRequest(request);
    }

    public void cancelSignupForRequest(Long requestId, SuperFrog superFrog){
        Request request = getRequestById(requestId); //grab the request @ id

        //If request is not approved, cant cancel
        if(request.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can cancel it");
        }

        //If this superfrog hasnt signed up for this request, cant cancel
        if(!request.getAssignedSuperFrogStudent().equals(superFrog.getFirstName()+" "+superFrog.getLastName())){
            throw new BadRequestException("This Request hasn't been assigned to this student");

        }

        //set the assigned S.F to null and update
        request.setAssignedSuperFrogStudent(null);
        updateRequest(request);


    }

    public void markRequestAsCompleted(Long requestId, SuperFrog superFrog){
        Request request = getRequestById(requestId); //grab the request @ id

        //If request is not approved, cant be completed
        if(request.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can complete it");
        }

        //If this superfrog hasnt signed up for this request, cant complete
        if(!request.getAssignedSuperFrogStudent().equals(superFrog.getFirstName()+" "+superFrog.getLastName())){
            throw new BadRequestException("This Request hasn't been assigned to this student, cannot complete");

        }

        //set request to completed and update the status.
        request.setRequestStatus(RequestStatus.COMPLETED);
        updateRequest(request);

    }








}
