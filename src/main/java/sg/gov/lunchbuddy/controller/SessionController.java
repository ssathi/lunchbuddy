package sg.gov.lunchbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.gov.lunchbuddy.domain.Session;
import sg.gov.lunchbuddy.domain.SessionStatus;
import sg.gov.lunchbuddy.service.SessionService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("sessions")
public class SessionController {

    @Autowired
    private SessionService service;

    @PostMapping("/start")
    public Session create(@RequestBody Session session, Principal principal) {

        session.setCreated(principal.getName());

        return service.start(session, principal.getName());
    }

    @PostMapping("/{id}/join")
    public Session join(@PathVariable Long id, Principal principal) throws Exception {

        return service.join(id, principal.getName());
    }

    @PostMapping("/{id}/vote/{restaurantId}")
    public Session vote(@PathVariable Long id, @PathVariable Long restaurantId, Principal principal) throws Exception {

        return service.vote(id, restaurantId, principal.getName());
    }

    @PostMapping("/{id}/end")
    public Session end(@PathVariable Long id, Principal principal) throws Exception {

        return service.end(id, principal.getName());
    }

    @GetMapping("/")
    public List<Session> getAll(Principal principal) {
        return service.getUserSessions(principal.getName());
    }

    @GetMapping("/{id}")
    public Session get(@PathVariable Long id, Principal principal) {
        return service.getUserSession(id, principal.getName());
    }
}
