package edu.tcu.cs.superfrogscheduler.request;


import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @Mock //simulate below obj
    RequestRepository requestRepository;

    @Mock
    UserRepository userRepository;


   @InjectMocks
    RequestService requestService;




    List<Request> requests;



    @BeforeEach
    void setUp(){
        Request request1 = new Request();
        request1.setId("1001");
        request1.setEventTitle("Request 1");
        request1.setEventDate(LocalDate.parse("2023-12-12"));
        request1.setEventDescription("Request 1 description");
        request1.setCustomerFirstName("Bob");
        request1.setCustomerLastName("McDonald");
        request1.setCustomerPhoneNumber("1231221234");
        request1.setRequestStatus(RequestStatus.APPROVED);
        request1.setCustomerEmail("bobmcdonald@gmail.com");




        Request request2 = new Request();
        request2.setId("1002");
        request2.setEventTitle("Request 2");
        request2.setEventDate(LocalDate.parse("2023-01-02"));
        request2.setEventDescription("Request 2 description");
        request2.setCustomerFirstName("Jeff");
        request2.setCustomerLastName("Whataburger");
        request2.setCustomerPhoneNumber("9879879876");
        request2.setCustomerEmail("jeffwhataburger@gmail.com");
        request2.setAssignedSuperFrogStudent("SF123");

        this.requests = new ArrayList<>();
        this.requests.add(request1);
        this.requests.add(request2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetRequestByIdSuccess() {

        Request a = new Request();
        a.setId("1250808601744904192");
        a.setEventTitle("Request 1");
        a.setEventDescription("An event where an super frog wears an invisibility cloak, which is used to make the wearer invisible.");
        a.setEventDate(LocalDate.parse("3023-12-12"));
        a.setCustomerFirstName("Jeff");
        a.setCustomerLastName("Whataburger");
        a.setCustomerPhoneNumber("9879879876");
        a.setCustomerEmail("jeffwhataburger@gmail.com");
        a.setAssignedSuperFrogStudent("SF123");


        SuperFrogUser w = new SuperFrogUser();
        w.setId("SF123");

        a.setAssignedSuperFrogStudent(w.getId());


        given(requestRepository.findById("1250808601744904192")).willReturn(Optional.of(a)); //define mock obj behavior


        //when, act on the target behavior, method to be testing
        Request returnedRequest = requestService.getRequestById("1250808601744904192");

        //then, compare when with expected result, assert expected outcomes
        assertThat(returnedRequest.getId()).isEqualTo(a.getId());
        assertThat(returnedRequest.getEventTitle()).isEqualTo(a.getEventTitle());
        assertThat(returnedRequest.getEventDescription()).isEqualTo(a.getEventDescription());
        assertThat(returnedRequest.getId()).isEqualTo(a.getId());
        assertThat(returnedRequest.getAssignedSuperFrogStudent()).isEqualTo(a.getAssignedSuperFrogStudent());
        assertThat(returnedRequest.getCustomerEmail()).isEqualTo(a.getCustomerEmail());
        verify(requestRepository,times(1)).findById("1250808601744904192");

    }

    @Test
    void testGetRequestByIdNotFound(){
        //given
        given(requestRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());



        //when
        Throwable thrown = catchThrowable(()->{
            Request returnedRequest = requestService.getRequestById("1250808601744904192");



        });


        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find Request with Id 1250808601744904192 :(");
        verify(requestRepository,times(1)).findById("1250808601744904192");


    }

    @Test
    void testFindAllSuccess(){
        //given
        given(requestRepository.findAll()).willReturn(this.requests);


        //when
        List<Request> actualRequests = requestService.getAllRequests();

        //then
        assertThat(actualRequests.size()).isEqualTo(this.requests.size());
        verify(requestRepository, times(1)).findAll();


    }


    @Test
    void testSignupForRequestSuccess() {
        // given
        Request request = new Request();
        request.setId("1001");
        request.setEventTitle("Request 1");
        request.setEventDescription("Request 1 description");
        request.setCustomerFirstName("Bob");
        request.setCustomerLastName("McDonald");
        request.setCustomerPhoneNumber("1231221234");
        request.setCustomerEmail("bobmcdonald@gmail.com");
        request.setRequestStatus(RequestStatus.APPROVED);

        SuperFrogUser superFrogUser = new SuperFrogUser();
        superFrogUser.setId("SF123");
        given(requestRepository.findById(request.getId())).willReturn(Optional.of(request));
        given(requestRepository.save(request)).willReturn(request);

        // when
        Request signedUpRequest = requestService.signupForRequest(request.getId(), "SF123");

        // then
        assertThat(signedUpRequest.getId()).isEqualTo(request.getId());
        assertThat(signedUpRequest.getAssignedSuperFrogStudent()).isEqualTo(superFrogUser.getId());

    }

    @Test
    void testCancelSignupForRequestSuccess() {
        // given
        Request request = new Request();
        request.setId("1001");
        request.setEventTitle("Request 1");
        request.setEventDescription("Request 1 description");
        request.setCustomerFirstName("Bob");
        request.setCustomerLastName("McDonald");
        request.setCustomerPhoneNumber("1231221234");
        request.setCustomerEmail("bobmcdonald@gmail.com");
        request.setRequestStatus(RequestStatus.APPROVED);

        SuperFrogUser superFrogUser = new SuperFrogUser();
        superFrogUser.setId("SF123");
        request.setAssignedSuperFrogStudent(superFrogUser.getId());
        given(requestRepository.findById(request.getId())).willReturn(Optional.of(request));
        given(requestRepository.save(request)).willReturn(request);

        // when
        Request signedUpRequest = requestService.cancelSignupForRequest(request.getId(), "SF123");

        // then
        assertThat(signedUpRequest.getId()).isEqualTo(request.getId());
        assertThat(signedUpRequest.getAssignedSuperFrogStudent()).isNull();

    }


    @Test
    void testMarkRequestAsCompletedSuccess() {
        // given
        Request request = new Request();
        request.setId("1001");
        request.setEventTitle("Request 1");
        request.setEventDescription("Request 1 description");
        request.setCustomerFirstName("Bob");
        request.setCustomerLastName("McDonald");
        request.setCustomerPhoneNumber("1231221234");
        request.setCustomerEmail("bobmcdonald@gmail.com");
        request.setRequestStatus(RequestStatus.APPROVED);


        SuperFrogUser superFrogUser = new SuperFrogUser();
        superFrogUser.setId("SF123");
        request.setAssignedSuperFrogStudent(superFrogUser.getId());

        given(requestRepository.findById(request.getId())).willReturn(Optional.of(request));
        given(requestRepository.save(request)).willReturn(request);

        // when
        Request completedRequest = requestService.markRequestAsCompleted(request.getId(), "SF123");

        // then
        assertThat(completedRequest.getId()).isEqualTo(request.getId());
        assertThat(completedRequest.getRequestStatus()).isEqualTo(RequestStatus.COMPLETED);

    }








    @Test
    void testSaveSuccess(){
        //given
        Request a = new Request();
        a.setId("33333");
        a.setEventTitle("Request 3");
        a.setEventDescription("An event where an super frog fights a student");
        a.setEventDate(LocalDate.parse("3123-12-12"));
        a.setCustomerFirstName("Jeff");
        a.setCustomerLastName("Whataburger");
        a.setCustomerPhoneNumber("9879879876");
        a.setCustomerEmail("jeffwhataburger@gmail.com");

        given(requestRepository.save(a)).willReturn(a);




        //when
        Request savedRequest = requestService.save(a);

        //then

        assertThat(savedRequest.getId()).isEqualTo("33333");
        assertThat(savedRequest.getEventTitle()).isEqualTo(a.getEventTitle());
        assertThat(savedRequest.getEventDescription()).isEqualTo(a.getEventDescription());
        assertThat(savedRequest.getCustomerEmail()).isEqualTo(a.getCustomerEmail());
        verify(requestRepository, times(1)).save(a);


    }

    @Test
    void testUpdateSuccess(){
        //given
        Request oldRequest = new Request();
        oldRequest.setId("1250808601744904191");
        oldRequest.setEventTitle("Request 40: Destroy Pigeons");
        oldRequest.setEventDescription("Super Frog needs to help the locals destroy evil pigeons");
        oldRequest.setCustomerEmail("bobperson@gmail.com");

        Request update = new Request();
        update.setId("1250808601744904191");
        update.setEventTitle("Request 40: Help Pigeons");
        update.setEventDescription("Super Frog has to help save the pigeons");

        given(requestRepository.findById("1250808601744904191")).willReturn(Optional.of(oldRequest));
        given(requestRepository.save(oldRequest)).willReturn(oldRequest);




        //when
        Request updatedArtifact = requestService.update("1250808601744904191", update);


        //then
        assertThat(updatedArtifact.getId()).isEqualTo("1250808601744904191");
        assertThat(updatedArtifact.getEventDescription()).isEqualTo(update.getEventDescription());
        verify(requestRepository, times(1)).findById("1250808601744904191");
        verify(requestRepository, times(1)).save(oldRequest);






    }

    @Test
    void testUpdateNotFound(){
        //given
        Request update = new Request();
        update.setId("1250808601744904191");
        update.setEventTitle("Request 40: Help Pigeons");
        update.setEventDescription("Super Frog has to help save the pigeons");

        given(requestRepository.findById("1250808601744904191")).willReturn(Optional.empty());


        // when
        assertThrows(ObjectNotFoundException.class, () ->{
            requestService.update("1250808601744904191", update);
        });


        //then
        verify(requestRepository, times(1)).findById("1250808601744904191");


    }

    @Test
    void testDeleteSuccess(){
        //given
        Request request = new Request();
        request.setId("1250808601744904191");
        request.setEventTitle("Request 40: Destroy Pigeons");
        request.setEventDescription("Super Frog needs to help the locals destroy evil pigeons");
        request.setCustomerEmail("bobperson@gmail.com");

        given(requestRepository.findById("1250808601744904191")).willReturn(Optional.of(request));
        doNothing().when(requestRepository).deleteById("1250808601744904191");

        //when
        requestService.delete("1250808601744904191");


        //then
        verify(requestRepository, times(1)).deleteById("1250808601744904191");


    }


    @Test
    void testDeleteNotFound(){
        //given

        given(requestRepository.findById("1250808601744904191")).willReturn(Optional.empty());

        //when
        assertThrows(ObjectNotFoundException.class, () ->{
            requestService.delete("1250808601744904191");

        });

        //then
        verify(requestRepository, times(1)).findById("1250808601744904191");


    }


    @Test
    void findByStatus() {

    }

    @Test
    void updateStatus() {
        
    }
}
