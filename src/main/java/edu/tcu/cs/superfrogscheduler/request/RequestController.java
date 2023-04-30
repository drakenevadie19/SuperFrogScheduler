package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.request.converter.RequestDtoToRequestConverter;
import edu.tcu.cs.superfrogscheduler.request.converter.RequestToRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.converter.Converter;
import edu.tcu.cs.superfrogscheduler.user.converter.dto_to_user.UserDtoToSuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class RequestController {


    private final RequestService requestService;
    private final RequestToRequestDtoConverter requestToRequestDtoConverter;
    private final Converter converter;

    private final RequestDtoToRequestConverter requestDtoToRequestConverter;

    private final UserDtoToSuperFrogUser userDtoToSuperFrogUser;

    public RequestController(RequestService requestService, RequestToRequestDtoConverter requestToRequestDtoConverter, Converter converter, RequestDtoToRequestConverter requestDtoToRequestConverter, UserDtoToSuperFrogUser userDtoToSuperFrogUser) {
        this.requestService = requestService;
        this.requestToRequestDtoConverter = requestToRequestDtoConverter;
        this.converter = converter;
        this.requestDtoToRequestConverter = requestDtoToRequestConverter;
        this.userDtoToSuperFrogUser = userDtoToSuperFrogUser;
    }

    // UC 6, Get list of all appearances
    @GetMapping("")
    public Result getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        List<RequestDto> requestDtos = requests.stream()
                .map(this.requestToRequestDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Find All Success", requestDtos );
    } //Dto done


    //UC 7, Get a request appearance at id
    @GetMapping("/{id}")
    public Result getRequestById(@PathVariable("id") String id) {
        Request foundRequest = this.requestService.getRequestById(id);
        RequestDto requestDto = this.requestToRequestDtoConverter.convert(foundRequest);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", requestDto);
    } //Dto done



    //UC 4, update request status
    @PutMapping("/{id}/status/{status}")
    public Result updateRequestStatus(@PathVariable String id,@PathVariable RequestStatus status) {

        Request updatedRequest = this.requestService.updateStatus(id, status);
        RequestDto updatedRequestDto = this.requestToRequestDtoConverter.convert(updatedRequest);
        return new Result(true,  StatusCode.SUCCESS, "Update Status Success", updatedRequestDto);


    }


    //UC 22, SuperFrog Student signs up for an appearance

    @PutMapping("/{requestId}/signup/{superFrogId}")
    public Result signUpForRequest(@PathVariable String requestId, @PathVariable String superFrogId){//@RequestBody SuperFrogUser superFrogUser){
        // Authenticate the superfrog somehow


        Request signedUpRequest = this.requestService.signupForRequest(requestId, superFrogId);
        RequestDto signedUpRequestDto = this.requestToRequestDtoConverter.convert(signedUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Sign Up Success", signedUpRequestDto);
    }




    //UC 23, SuperFrog Student cancels a signs up appearance
    @PutMapping("/{requestId}/cancel/{superFrogId}")
    //@PutMapping("/{requestId}/signup/{superFrogId}")
    public Result cancelSignUpForRequest(@PathVariable String requestId, @PathVariable String superFrogId){
        // Authenticate the superfrog somehow

        Request canceledSignUpRequest = this.requestService.cancelSignupForRequest(requestId, superFrogId);
        RequestDto canceledSignUpRequestDto = this.requestToRequestDtoConverter.convert(canceledSignUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Cancel Sign Up Success", canceledSignUpRequestDto);
    }

    @PutMapping("/{requestId}/completed/{superFrogId}")
    public Result markRequestAsCompleted(@PathVariable String requestId, @PathVariable String superFrogId){//@RequestBody UserDto userDto){//@RequestBody String superFrogId){ //UC 24, SuperFrog Student marks an appearance as completed

        //SuperFrogUser superFrogUser = this.userDtoToSuperFrogUser.convert(userDto);

        Request completedRequest = this.requestService.markRequestAsCompleted(requestId, superFrogId);
        RequestDto completedRequestDto = this.requestToRequestDtoConverter.convert(completedRequest);
        return new Result(true, StatusCode.SUCCESS, "Mark as Completed Success", completedRequestDto);


    }

    //UC 5 add a request
    @PostMapping
    public Result addRequest(@Valid @RequestBody RequestDto requestDto) {
        Request newRequest = this.requestDtoToRequestConverter.convert(requestDto);
        Request savedRequest = this.requestService.save(newRequest);
        RequestDto savedRequestDto = this.requestToRequestDtoConverter.convert(savedRequest);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedRequestDto);
    }

    //UC 8: edit request
    @PutMapping("/{id}")
    public Result updateRequest(@PathVariable String id, @Valid @RequestBody RequestDto requestDto) {
        Request updatedRequest = this.requestDtoToRequestConverter.convert(requestDto);
        Request savedRequest = this.requestService.update(id, updatedRequest);
        RequestDto savedRequestDto = this.requestToRequestDtoConverter.convert(savedRequest);
        return new Result(true, StatusCode.SUCCESS, "Update Success", savedRequestDto);
    }

    //UC 12: delete a request
    @DeleteMapping("/{id}")
    public Result deleteRequest(@PathVariable String id) {
        this.requestService.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }



}