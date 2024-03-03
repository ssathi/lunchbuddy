package sg.gov.lunchbuddy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sg.gov.lunchbuddy.domain.Session;
import sg.gov.lunchbuddy.domain.SessionInvite;
import sg.gov.lunchbuddy.domain.SessionStatus;
import sg.gov.lunchbuddy.exception.InvalidOperationException;
import sg.gov.lunchbuddy.exception.NotFoundException;
import sg.gov.lunchbuddy.exception.UnauthorizedException;
import sg.gov.lunchbuddy.service.SessionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SessionServiceTest {


    @Autowired
    private SessionService service;

    @Test
    public void testNotEmptyInvites() {

        var session = new Session();

        var exception = assertThrows(
                InvalidOperationException.class,
                () -> service.start(session, "Admin")
        );
        assertEquals("No one was invited!", exception.getMessage());
    }

    @Test
    public void testStartingSession() {

        var session = new Session();

        SessionInvite invite = new SessionInvite();
        invite.setUsername("Satthi");
        session.setSessionInvites(List.of(invite));

        Session created = service.start(session, "Admin");

        assertNotNull(created);
        assertNotNull(created.getId());
    }


    @Test
    public void testJoiningSession() {

        var session = new Session();
        session.setCreated("Admin");


        SessionInvite invite = new SessionInvite();
        invite.setUsername("Satthi");
        session.setSessionInvites(new ArrayList<>(List.of(invite)));

        Session created = service.start(session, "Admin");


        var joined = service.join(created.getId(), "Satthi");

        assertEquals(joined.getSessionUsers().size(), 1);

    }

    @Test
    public void testVotingInvalidSession() {

        var exception = assertThrows(
                NotFoundException.class,
                () -> service.getUserSession(3L, "Satthi")
        );
        assertEquals("Session does not exists!", exception.getMessage());
    }


    @Test
    public void testVotingSession() {

        var session = new Session();
        session.setCreated("Admin");


        SessionInvite invite = new SessionInvite();
        invite.setUsername("Satthi");
        session.setSessionInvites(new ArrayList<>(List.of(invite)));

        Session created = service.start(session, "Admin");


        var joined = service.join(created.getId(), "Satthi");


        var voted = service.vote(joined.getId(), 1L, "Satthi");

        assertEquals(voted.getSessionVotes().size(), 1);
    }

    @Test
    public void testEndingSession() {

        var session = new Session();
        session.setCreated("Admin");


        SessionInvite invite = new SessionInvite();
        invite.setUsername("Satthi");
        session.setSessionInvites(new ArrayList<>(List.of(invite)));

        Session created = service.start(session, "Admin");


        var joined = service.join(created.getId(), "Satthi");


        var voted = service.vote(joined.getId(), 1L, "Satthi");


        var exception = assertThrows(
                UnauthorizedException.class,
                () -> service.end(voted.getId(), "Satthi")
        );
        assertEquals("User not permitted to end the session!", exception.getMessage());


        var ended = service.end(voted.getId(), "Admin");

        assertEquals(ended.getStatus(), SessionStatus.CLOSED);
        assertNotNull(ended.getSelectedRestaurant());
    }

}
