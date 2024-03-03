package sg.gov.lunchbuddy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.gov.lunchbuddy.domain.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, Long> {

    @Query("select se from Session se left join SessionInvite inv on se.id = inv.session.id " +
            "where se.id = :id and (inv.username = :username or se.created = :username) ")
    Optional<Session> getUserSession(Long id, String username);

    @Query("select se from Session se left join SessionInvite inv on se.id = inv.session.id " +
            "where (inv.username = :username or se.created = :username) ")
    List<Session> getUserSessions(String username);
}
