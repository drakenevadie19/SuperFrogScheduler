package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.system.exception.BadRequestException;
import edu.tcu.cs.superfrogscheduler.system.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //transaction for methods
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() { //UC 6 - return a list of all Requests
        return requestRepository.findAll();
    }

    public Request getRequestById(Long id) { //UC 6 - return a Request by id
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request", "id", id));
    }

    public Request save(Request newRequest){
        return this.requestRepository.save(newRequest);
    }

    public Request createRequest(Request request) {
        request.setRequestStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    //update, pass request, requestRepository.save(request)
    public Request update(Long requestId, Request update){
        return this.requestRepository.findById(requestId)
                .map(oldRequest ->{
                    oldRequest.setEventDate(update.getEventDate());
                    oldRequest.setEventTitle(update.getEventTitle());
                    oldRequest.setCustomerFirstName(update.getCustomerFirstName());
                    oldRequest.setCustomerLastName(update.getCustomerLastName());
                    oldRequest.setRequestStatus(update.getRequestStatus());
                    oldRequest.setAssignedSuperFrogStudent(update.getAssignedSuperFrogStudent());
                    oldRequest.setCustomerPhoneNumber(update.getCustomerPhoneNumber());
                    oldRequest.setCustomerEmail(update.getCustomerEmail());
                    return this.requestRepository.save(oldRequest);

                })
                .orElseThrow(()-> new EntityNotFoundException(requestId));



    }
//    public Request updateRequest(Request request){
//        return requestRepository.save(request);
//    }

    //delete, pass id, request Request = getReqById(id),   requestRepository.delete(request)
    public void delete(long requestId){
        this.requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(requestId));
        this.requestRepository.deleteById(requestId);
    }

    public Request signupForRequest(Long requestId, String superFrogId){ //UC 22: allows SuperFrog students to sign up for a specific appearance request.
        Request updateRequest = getRequestById(requestId); //grab the request @ id

        //If request is not approved, cant accept
        if(updateRequest.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can sign up for it");
        }

        //if there's already a super frog signed up, cant accept
        if(updateRequest.getAssignedSuperFrogStudent() != null){
            throw new BadRequestException("This Request has a Super Frog already signed up for it");
        }

        //grab SuperFrog
        SuperFrog superFrog = this.superFrogService.getSuperFrogById(superFrogId);

        //assign the super frog to the request, then update
        updateRequest.setAssignedSuperFrogStudent(superFrog.getId());
        return update(requestId,updateRequest);//updateRequest(requestId);
    }

    public Request cancelSignupForRequest(Long requestId, String superFrogId){ //UC 23: allows SuperFrog students to cancel their sign-up for a specific appearance request.
        Request updateRequest = getRequestById(requestId); //grab the request @ id

        //grab SuperFrog
        SuperFrog superFrog = this.superFrogService.getSuperFrogById(superFrogId);


        //If request is not approved, cant cancel
        if(updateRequest.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can cancel it");
        }

        //If this superfrog hasnt signed up for this request, cant cancel
        if(!updateRequest.getAssignedSuperFrogStudent().equals(superFrog.getId())){
            throw new BadRequestException("This Request hasn't been assigned to this student");

        }



        //set the assigned SuperFrog to null and update
        updateRequest.setAssignedSuperFrogStudent(null);
        return update(requestId, updateRequest);
    }

    public Request markRequestAsCompleted(Long requestId, String superFrogId){ //UC 24: allows SuperFrog students to mark a specific appearance request as completed.
        Request updateRequest = getRequestById(requestId); //grab the request @ id
        SuperFrog superFrog = this.superFrogService.getSuperFrogById(superFrogId);

        //If request is not approved, cant be completed
        if(updateRequest.getRequestStatus()!= RequestStatus.APPROVED){
            throw new BadRequestException("This Request has to be approved before Super Frog can complete it");
        }

        //If this superfrog hasnt signed up for this request, cant complete
        if(!updateRequest.getAssignedSuperFrogStudent().equals(superFrog.getId())){
            throw new BadRequestException("This Request hasn't been assigned to this student, cannot complete");

        }

        //set request to completed and update the status.
        updateRequest.setRequestStatus(RequestStatus.COMPLETED);
        return update(requestId,updateRequest);
    }
}
