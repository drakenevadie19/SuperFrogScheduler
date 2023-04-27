package edu.tcu.cs.superfrogscheduler.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.superfrogscheduler.request.converter.RequestDtoToRequestConverter;
import edu.tcu.cs.superfrogscheduler.request.converter.RequestToRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @Mock
    private RequestToRequestDtoConverter requestToRequestDtoConverter;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private RequestDtoToRequestConverter requestDtoToRequestConverter;

    UserDto userDto;

    private List<Request> requests;

    @BeforeEach
    void setUp() {
        this.requests = new ArrayList<>();
        Request request1 = new Request();
        request1.setId("1001");
        request1.setEventTitle("Request 1");
        request1.setEventDate(LocalDate.parse("2023-12-12"));
        request1.setEventDescription("Request 1 description");
        request1.setCustomerFirstName("Bob");
        request1.setCustomerLastName("McDonald");
        request1.setCustomerPhoneNumber("1231221234");
        request1.setCustomerEmail("bobmcdonald@gmail.com");
        request1.setRequestStatus(RequestStatus.APPROVED);






        Request request2 = new Request();
        request2.setId("1002");
        request2.setEventTitle("Request 2");
        request2.setEventDate(LocalDate.parse("2023-01-02"));
        request2.setEventDescription("Request 2 description");
        request2.setCustomerFirstName("Jeff");
        request2.setCustomerLastName("Whataburger");
        request2.setCustomerPhoneNumber("9879879876");
        request2.setCustomerEmail("jeffwhataburger@gmail.com");




        SuperFrogUser superFrog = new SuperFrogUser();
        superFrog.setId("SF123");
        superFrog.setFirstName("Bob");
        superFrog.setLastName("Person");

        request2.setAssignedSuperFrogStudent(superFrog);


        this.requests.add(request1);
        this.requests.add(request2);


    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testGetRequestByIdSuccess() throws Exception {
        given(this.requestService.getRequestById("1001")).willReturn(this.requests.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get(this.baseUrl+"/requests/1001"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1001"))
                .andExpect(jsonPath("$.data.eventTitle").value("Request 1"));

    }

    @Test
    void testGetRequestByIdFailure() throws Exception {
        doThrow(new ObjectNotFoundException("Request", "1")).when(this.requestService).getRequestById("1");
        mockMvc.perform(MockMvcRequestBuilders.get(this.baseUrl+"/requests/1"))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Request with Id 1 :("));


    }



    @Test
    void testGetAllRequestsSuccess() throws Exception {
        //given
        given(this.requestService.getAllRequests()).willReturn(this.requests);


        //when and then
        this.mockMvc.perform(get(this.baseUrl+"/requests").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.requests.size())))
                .andExpect(jsonPath("$.data[0].id").value("1001"))
                .andExpect(jsonPath("$.data[0].eventTitle").value("Request 1"))
                .andExpect(jsonPath("$.data[1].id").value("1002"))
                .andExpect(jsonPath("$.data[1].eventTitle").value("Request 2"));




    }

    @Test
    void testSignUpForRequestSuccess() throws Exception {


        //A version of this.requests.get(0) that has the assignedSuperFrog
        SuperFrogUser superFrog = new SuperFrogUser();
        superFrog.setId("SF123");
        superFrog.setFirstName("Bob");
        superFrog.setLastName("Person");
        superFrog.setPhoneNumber(null);
        superFrog.setAddress(null);
        superFrog.setEmail(null);




        Request requestSignupSuccess = new Request();
        requestSignupSuccess.setId("1001");
        requestSignupSuccess.setEventTitle("Request 1");
        requestSignupSuccess.setEventDate(LocalDate.parse("2023-12-12"));
        requestSignupSuccess.setEventDescription("Request 1 description");
        requestSignupSuccess.setCustomerFirstName("Bob");
        requestSignupSuccess.setCustomerLastName("McDonald");
        requestSignupSuccess.setCustomerPhoneNumber("1231221234");
        requestSignupSuccess.setCustomerEmail("bobmcdonald@gmail.com");
        requestSignupSuccess.setRequestStatus(RequestStatus.APPROVED);
        requestSignupSuccess.setAssignedSuperFrogStudent(superFrog);
        //


        // given request this.requests.get(0) that does not have a superFrog assigned
        String requestId = "1001";
        String superFrogId = "SF123";
        Request request = this.requests.get(0);


        // Ensure that the request is approved
        request.setRequestStatus(RequestStatus.APPROVED);

        // Ensure that there is no assigned Super Frog
        assertNull(request.getAssignedSuperFrogStudent());

        //given the signupForRequest will return the successful request sign up
        given(this.requestService.signupForRequest(requestId, superFrogId)).willReturn(requestSignupSuccess);

        // when and then
        mockMvc.perform(post(this.baseUrl+"/requests/" + requestId + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(superFrogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                //.andExpect(jsonPath("$.data.assignedSuperFrogStudent").value(json))//superFrogId))
                .andExpect(jsonPath("$.message").value("Sign Up Success"));

        // Check if the assignedSuperFrogStudent property is set correctly in the returned Request object
        Request updatedRequest = requestService.signupForRequest(requestId, superFrogId);
        assertEquals(superFrogId, updatedRequest.getAssignedSuperFrogStudent().getId());
    }




//    @Test
//    void testSignUpForRequestFailureSFExists() throws Exception {
//        // given
//        String requestId = "1002";
//        String superFrogId = "SF123";
//        String currentSuperFrogId="SF000";
//
//        doThrow(new ObjectAlreadyExistedException("Super Frog", superFrogId)).when(this.requestService).signupForRequest(requestId,currentSuperFrogId);
//
//
//        // when and then
//        mockMvc.perform(post(this.baseUrl+"/requests/" + requestId + "/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(currentSuperFrogId))
//                .andExpect(jsonPath("$.flag").value(false))
//                .andExpect(jsonPath("$.code").value(StatusCode.CONFLICT))
//                .andExpect(jsonPath("$.message").value("Super Frog "+superFrogId+" is already existed!"));
//    }

    @Test
    void testCancelSignUpForRequestSuccess() throws Exception {
        // given
        String requestId = "1002";
        String superFrogId = "SF123";


        Request request = this.requests.get(1);

        given(this.requestService.cancelSignupForRequest(requestId, superFrogId)).willReturn(request);

        // when and then
        mockMvc.perform(delete(this.baseUrl+"/requests/" + requestId + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(superFrogId))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Cancel Sign Up Success"));


    }

    @Test
    void testCancelSignUpForRequestFailureSFExists() throws Exception {
        // given
        String requestId = "1002";
        String superFrogId = "SF123";
        String currentSuperFrogId="SF000";

        doThrow(new ObjectAlreadyExistedException("Super Frog", superFrogId)).when(this.requestService).cancelSignupForRequest(requestId,currentSuperFrogId);


        // when and then
        mockMvc.perform(delete(this.baseUrl+"/requests/" + requestId + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currentSuperFrogId))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.CONFLICT))
                .andExpect(jsonPath("$.message").value("Super Frog "+superFrogId+" is already existed!"));
    }

    @Test
    void testMarkRequestAsCompletedSuccess() throws Exception {
        // given
        String requestId = "1002";
        String superFrogId = "SF123";
        Request request = this.requests.get(1);
        given(this.requestService.markRequestAsCompleted(requestId, superFrogId)).willReturn(request);

        // when and then
        mockMvc.perform(put(this.baseUrl+"/requests/" + requestId + "/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(superFrogId))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Mark as Completed Success"))
                .andExpect(jsonPath("$.data").exists());
        verify(requestService, times(1)).markRequestAsCompleted(requestId, superFrogId);
    }


    @Test
    void testMarkRequestAsCompletedFailSFExists() throws Exception {
        // given
        String requestId = "1002";
        String superFrogId = "SF123";
        String currentSuperFrogId="SF000";

        doThrow(new ObjectAlreadyExistedException("Super Frog", superFrogId)).when(this.requestService).markRequestAsCompleted(requestId,currentSuperFrogId);


        // when and then
        mockMvc.perform(put(this.baseUrl+"/requests/" + requestId + "/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currentSuperFrogId))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.CONFLICT))
                .andExpect(jsonPath("$.message").value("Super Frog "+superFrogId+" is already existed!"));
        //verify(requestService, times(1)).markRequestAsCompleted(requestId, superFrogId);
    }


    @Test
    void getRequestByStatus() {
    }

    @Test
    void updateRequestStatus() {
    }
}
