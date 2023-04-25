package edu.tcu.cs.superfrogscheduler.request;

import edu.tcu.cs.superfrogscheduler.request.converter.RequestDtoToRequestConverter;
import edu.tcu.cs.superfrogscheduler.request.converter.RequestToRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/requests")
public class RequestController {


    private final RequestService requestService;
    private final RequestToRequestDtoConverter requestToRequestDtoConverter;

    private final RequestDtoToRequestConverter requestDtoToRequestConverter;

    public RequestController(RequestService requestService, RequestToRequestDtoConverter requestToRequestDtoConverter, RequestDtoToRequestConverter requestDtoToRequestConverter) {
        this.requestService = requestService;
        this.requestToRequestDtoConverter = requestToRequestDtoConverter;
        this.requestDtoToRequestConverter = requestDtoToRequestConverter;
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


    //UC 6, Get list of appearance at id
    @GetMapping("/{id}")
    public Result getRequestById(@PathVariable("id") String id) {
        Request foundRequest = this.requestService.getRequestById(id);
        RequestDto requestDto = this.requestToRequestDtoConverter.convert(foundRequest);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", requestDto);
    } //Dto done




    //UC 22, SuperFrog Student signs up for an appearance
    @PostMapping("/{id}/signup")
    public Result signUpForRequest(@PathVariable String id, @RequestBody String superFrogId){
        // Authenticate the superfrog somehow


        Request signedUpRequest = this.requestService.signupForRequest(id, superFrogId);
        RequestDto signedUpRequestDto = this.requestToRequestDtoConverter.convert(signedUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Sign Up Success", signedUpRequestDto);
    }

    //UC 23, SuperFrog Student cancels a signs up appearance
    @DeleteMapping("/{id}/signup")
    public Result cancelSignUpForRequest(@PathVariable String id, @RequestBody String superFrogId){
        // Authenticate the superfrog somehow


        Request canceledSignUpRequest = this.requestService.cancelSignupForRequest(id, superFrogId);
        RequestDto canceledSignUpRequestDto = this.requestToRequestDtoConverter.convert(canceledSignUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Cancel Sign Up Success", canceledSignUpRequestDto);


    }

    @PutMapping("/{id}/completed")
    public Result markRequestAsCompleted(@PathVariable String id, @RequestBody String superFrogId){ //UC 24, SuperFrog Student marks an appearance as completed

        Request completedRequest = this.requestService.markRequestAsCompleted(id, superFrogId);
        RequestDto completedRequestDto = this.requestToRequestDtoConverter.convert(completedRequest);
        return new Result(true, StatusCode.SUCCESS, "Mark as Completed Success", completedRequestDto);


    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody RequestDto requestDto) {
        Request newRequest = this.requestDtoToRequestConverter.convert(requestDto);
        Request savedRequest = this.requestService.save(newRequest);
        RequestDto savedRequestDto = this.requestToRequestDtoConverter.convert(savedRequest);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedRequestDto);
    }

    @PutMapping("/{id}")
    public Result updateRequest(@PathVariable String id, @Valid @RequestBody RequestDto requestDto) {
        Request updatedRequest = this.requestDtoToRequestConverter.convert(requestDto);
        Request savedRequest = this.requestService.update(id, updatedRequest);
        RequestDto savedRequestDto = this.requestToRequestDtoConverter.convert(savedRequest);
        return new Result(true, StatusCode.SUCCESS, "Update Success", savedRequestDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteRequest(@PathVariable String id) {
        this.requestService.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}