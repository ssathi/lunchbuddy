package sg.gov.lunchbuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.gov.lunchbuddy.domain.*;
import sg.gov.lunchbuddy.exception.InvalidOperationException;
import sg.gov.lunchbuddy.exception.NotFoundException;
import sg.gov.lunchbuddy.exception.UnauthorizedException;
import sg.gov.lunchbuddy.repo.RestaurantRepo;
import sg.gov.lunchbuddy.repo.SessionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class SessionService {

    @Autowired
    private SessionRepo repo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    public Session start(Session session, String username) {

        session.setCreated(username);
        session.setStatus(SessionStatus.OPEN);

        if (isNull(session.getSessionInvites())  || session.getSessionInvites().isEmpty()) {
            throw new InvalidOperationException("No one was invited!");
        }

        for (SessionInvite invite : session.getSessionInvites()) {
            invite.setSession(session);
        }

        return repo.save(session);
    }

    public List<Session> getUserSessions(String username) {
        return repo.getUserSessions(username);
    }

    public Session getUserSession(Long id, String username) {
        return repo.getUserSession(id, username)
                .orElseThrow(() -> new NotFoundException("Session does not exists!"));
    }


    public Session join(Long id, String username) {

        /*
         * Users who were invited only can join the session
         * */

        var session = repo.getUserSession(id, username)
                .orElseThrow(() -> new UnauthorizedException("Session does not exists or you don't have access to it!"));

        if (session.getStatus() != SessionStatus.OPEN) {
            throw new InvalidOperationException("Session is not OPEN");
        }

        var user = new SessionUser();
        user.setSession(session);
        user.setUsername(username);

        var users = new ArrayList<SessionUser>();
        if (nonNull(session.getSessionUsers())) {
            users.addAll(session.getSessionUsers());
        }
        users.add(user);

        session.setSessionUsers(users);

        repo.save(session);

        return session;
    }

    public Session vote(Long id, Long restaurantId, String username) {

        var session = repo.getUserSession(id, username)
                .orElseThrow(() -> new UnauthorizedException("Session does not exists or you don't have access to it!"));

        if (session.getStatus() != SessionStatus.OPEN) {
            throw new InvalidOperationException("Session is not OPEN");
        }

        var restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant does not exists!"));

        /*
        * The owner or the users who joined the session can only can vote for the session
        *
        * */

        if (session.getSessionUsers().stream().map(SessionUser::getUsername)
                .anyMatch(user -> user.equals(username)) || session.getCreated().equals(username)) {
            var vote = new SessionVote();
            vote.setSession(session);
            vote.setUsername(username);
            vote.setRestaurant(restaurant);

            var votes = new ArrayList<SessionVote>();
            if (nonNull(session.getSessionVotes())) {
                votes.addAll(session.getSessionVotes());
            }
            votes.add(vote);

            session.setSessionVotes(votes);

            repo.save(session);

            return session;

        } else {
            throw new InvalidOperationException("User has to join the session before voting!");
        }
    }


    public Session end(Long id, String username) {

        var session = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Session does not exists!"));

        if (session.getStatus() == SessionStatus.CLOSED) {
            throw new InvalidOperationException("Session was ended already!");
        }

        /*
        * The user who started the session only can end the session
        * */

        if (session.getCreated().equals(username)) {

            if (session.getSessionVotes().isEmpty()) {
                throw new InvalidOperationException("No vote has been casted!");
            }

            var random = new Random();

            var selected = session.getSessionVotes().get(
                    random.nextInt(session.getSessionVotes().size())
            );

            session.setSelectedRestaurant(selected.getRestaurant().getName());
            session.setStatus(SessionStatus.CLOSED);

            repo.save(session);

            return session;
        } else {
            throw new UnauthorizedException("User not permitted to end the session!");
        }
    }
}
