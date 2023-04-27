package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //transaction for methods
public class RequestService {

    private final RequestRepository requestRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository, UserService userService, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //UC 6 - view all requests
    public List<Request> getAllRequests() { //UC 6 - return a list of all Requests
        return requestRepository.findAll();
    }

    //UC 6 - find by status
    public List<Request> findByStatus(RequestStatus status){
        return this.requestRepository.findByRequestStatus(status);
    }

    //UC 4 - update status
    public Request updateStatus(String id, RequestStatus status){
        return this.requestRepository.findById(id).
                map(oldRequest ->{
                    oldRequest.setRequestStatus(status);
                    return this.requestRepository.save(oldRequest);

                })
                .orElseThrow(()-> new ObjectNotFoundException("Request", id));
    }



    //UC 7 - return a Request by id
    public Request getRequestById(String id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Request", id));
    }

    public Request save(Request newRequest){
        return this.requestRepository.save(newRequest);
    }

    public Request createRequest(Request request) {
        request.setRequestStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    //update, pass request, requestRepository.save(request)
    public Request update(String requestId, Request update){
        return this.requestRepository.findById(requestId)
                .map(oldRequest ->{
                    oldRequest.setEventDate(update.getEventDate());
                    oldRequest.setEventDescription(update.getEventDescription());
                    oldRequest.setEventTitle(update.getEventTitle());
                    oldRequest.setCustomerFirstName(update.getCustomerFirstName());
                    oldRequest.setCustomerLastName(update.getCustomerLastName());
                    oldRequest.setRequestStatus(update.getRequestStatus());
                    oldRequest.setAssignedSuperFrogStudent(update.getAssignedSuperFrogStudent());
                    oldRequest.setCustomerPhoneNumber(update.getCustomerPhoneNumber());
                    oldRequest.setCustomerEmail(update.getCustomerEmail());
                    return this.requestRepository.save(oldRequest);

                })
                .orElseThrow(()-> new ObjectNotFoundException("Request",requestId));
    }

    //delete, pass id
    public void delete(String requestId){
        this.requestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Request",requestId));
        this.requestRepository.deleteById(requestId);
    }



    //UC 22: allows SuperFrog students to sign up for a specific appearance request.
    public Request signupForRequest(String requestId, String superFrogId){
        Request request = getRequestById(requestId); //grab the request @ id



        //If request is not approved, cant accept
        if(request.getRequestStatus()!= RequestStatus.APPROVED){
            throw new ObjectNotFoundException("Request with Approval",requestId);
        }

        //if there's already a super frog signed up, cant accept
        if(request.getAssignedSuperFrogStudent() != null){
            throw new ObjectNotFoundException("Superfrog", superFrogId);
        }

        //assign the super frog to the request, then update
        request.setAssignedSuperFrogStudent(superFrogId);
        request.setRequestStatus(RequestStatus.ASSIGNED);


        return update(requestId,request);//updateRequest(requestId);
    }





    //UC 23: allows SuperFrog students to cancel their sign-up for a specific appearance request.
    public Request cancelSignupForRequest(String requestId, String superFrogId){


        Request updateRequest = getRequestById(requestId); // grab the request @ id



        // check if assignedSuperFrogStudent field matches the passed superFrogId
        if (updateRequest.getAssignedSuperFrogStudent().equals(superFrogId)) {
            // set the assigned SuperFrog to null and update
            updateRequest.setAssignedSuperFrogStudent(null);
            updateRequest.setRequestStatus(RequestStatus.APPROVED);


        }
        else {
            throw new ObjectAlreadyExistedException("Super Frog ",updateRequest.getAssignedSuperFrogStudent());//IllegalArgumentException("SuperFrog not assigned to this request.");
        }


        return update(requestId, updateRequest);
        //set the assigned SuperFrog to null and update

    }



    //UC 24: allows SuperFrog students to mark a specific appearance request as completed.
    public Request markRequestAsCompleted(String requestId, String superFrogId){
        Request request = getRequestById(requestId); //grab the request @ id

        // check if assignedSuperFrogStudent field matches the passed superFrogId
        if (request.getAssignedSuperFrogStudent().equals(superFrogId)) {
            request.setRequestStatus(RequestStatus.COMPLETED);
        }
        else {
            throw new ObjectAlreadyExistedException("Super Frog ",request.getAssignedSuperFrogStudent());//IllegalArgumentException("SuperFrog not assigned to this request.");
        }

        //set request to completed and update the status.

        return update(requestId,request);
    }

}