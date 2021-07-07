package dennis.novi.livelyEvents;
import dennis.novi.livelyEvents.controller.UserNormalController;
import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import dennis.novi.livelyEvents.service.UserNormalService;
import dennis.novi.livelyEvents.service.UserNormalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        event.setTime("20:00");
    }



}
