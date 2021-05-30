package dennis.novi.livelyEvents;

import dennis.novi.livelyEvents.controller.ExceptionController;
import dennis.novi.livelyEvents.controller.UserNormalController;
import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import dennis.novi.livelyEvents.service.UserNormalService;
import dennis.novi.livelyEvents.service.UserNormalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UserNormalServiceImplTest {
    @InjectMocks
    private final UserNormalService userNormalService = new UserNormalServiceImpl();

    @Mock
    private UserNormalController userNormalController;

    @Mock
    private UserNormalRepository userNormalRepository;

    @Mock
    private UserNormal userNormal;

    @Mock
    private Event event;

    @Mock
    private BadRequestException badRequestException;

    @BeforeEach
    void setupUser() {
        userNormal = new UserNormal();
        userNormal.setUsername("Wim123");
        userNormal.setFirstName("Wim");
        userNormal.setLastName("Janssen");
        userNormal.setEmailAddress("wim.janssen@home.nl");
        userNormal.setPassword("password");
        userNormal.setRepeatedPassword("password");
    }
    @BeforeEach
    void setupEvent() {
        event = new Event();
        event.setId((long) 1);
        event.setName("Random event");
        event.setDate("15-05-2021");
        event.setTime("20:00");
    }

    @Test
    void averageRatingCalculationShouldReturn6(){
        Double responseEntity = userNormalService.calculateAverageRating(userNormal);

        Assertions.assertEquals(6.0, responseEntity.doubleValue());
    }


}
