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
@RequestMapping("/requests")
public class RequestController {


    private final RequestService requestService;
    private final RequestToRequestDtoConverter requestToRequestDtoConverter;

    private final RequestDtoToRequestConverter requestDtoToRequestConverter;

    public RequestController(RequestService requestService, RequestToRequestDtoConverter requestToRequestDtoConverter, RequestDtoToRequestConverter requestDtoToRequestConverter) {
        this.requestService = requestService;
        this.requestToRequestDtoConverter = requestToRequestDtoConverter;
        this.requestDtoToRequestConverter = requestDtoToRequestConverter;
    }

    @GetMapping("")
    public List<RequestDto> getAllRequests() { // UC 6, Get list of all appearances
        List<Request> requests = requestService.getAllRequests();
        return requests.stream()
                .map(this.requestToRequestDtoConverter::convert)
                .collect(Collectors.toList());
    } //Dto done


    @GetMapping("/{id}")
    public Result getRequestById(@PathVariable("id") Long id) { //UC 6, Get list of appearance at id
        Request foundRequest = this.requestService.getRequestById(id);
        RequestDto requestDto = this.requestToRequestDtoConverter.convert(foundRequest);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", requestDto);
    } //Dto done, double check this


    // Working on implementing Dto below


    @PostMapping("/{id}/signup")
    public Result signUpForRequest(@PathVariable Long id, @RequestBody String superFrogId){ //@RequestBody SuperFrog superFrog) {//SuperfrogDto superfrogDto) {
        // Authenticate the superfrog somehow


        Request signedUpRequest = this.requestService.signupForRequest(id, superFrogId);
        RequestDto signedUpRequestDto = this.requestToRequestDtoConverter.convert(signedUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Sign Up Success", signedUpRequestDto);
    }

    @DeleteMapping("/{id}/signup")
    public Result cancelSignUpForRequest(@PathVariable Long id, @RequestBody String superFrogId){ //@RequestBody SuperFrog superFrog) {//SuperfrogDto superfrogDto) {
        // Authenticate the superfrog somehow


        Request canceledSignUpRequest = this.requestService.cancelSignupForRequest(id, superFrogId);
        RequestDto canceledSignUpRequestDto = this.requestToRequestDtoConverter.convert(canceledSignUpRequest);
        return new Result(true, StatusCode.SUCCESS, "Cancel Sign Up Success", canceledSignUpRequestDto);
    }

    @PutMapping("/{id}/completed")
    public Result markRequestAsCompleted(@PathVariable Long id, @RequestBody String superFrogId){ //@RequestBody SuperFrog superFrog) {//SuperfrogDto superfrogDto) {
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
    public Result updateRequest(@PathVariable Long id, @Valid @RequestBody RequestDto requestDto) {
        Request updatedRequest = this.requestDtoToRequestConverter.convert(requestDto);
        Request savedRequest = this.requestService.update(id, updatedRequest);
        RequestDto savedRequestDto = this.requestToRequestDtoConverter.convert(savedRequest);
        return new Result(true, StatusCode.SUCCESS, "Update Success", savedRequestDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteRequest(@PathVariable Long id) {
        this.requestService.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}