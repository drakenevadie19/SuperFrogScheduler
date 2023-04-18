package edu.tcu.cs.superfrogscheduler.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("")
    public List<Request> getAllRequests() { // UC 6, Get list of all appearances
        return requestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable("id") Long id) { //UC 6, Get list of appearance at id
        return requestService.getRequestById(id);
    }

    @PostMapping("/{id}/signup")
    public void signupForRequest(@PathVariable("id") Long id, @RequestBody SuperFrog superFrog) { //UC 22, Sign up the current student for the appearance request at {id}
        requestService.signupForRequest(id, superFrog);
    }

    @DeleteMapping("/{id}/signup")
    public void cancelSignupForRequest(@PathVariable("id") Long id, @RequestBody SuperFrog superFrog) { //UC 23, cancel the current student for the appearance request at {id}
        requestService.cancelSignupForRequest(id, superFrog);
    }

    @PutMapping("/{id}/completed")
    public void markRequestAsCompleted(@PathVariable("id") Long id, @RequestBody SuperFrog superFrog) { //UC 24, Mark the appearance request at {id} as completed by the current student.
        requestService.markRequestAsCompleted(id, superFrog);
    }
}

