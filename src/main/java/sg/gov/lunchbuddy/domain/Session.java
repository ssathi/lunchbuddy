package sg.gov.lunchbuddy.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue
    private Long id;
    private String created;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    @JsonManagedReference
    private List<SessionInvite> sessionInvites;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    @JsonManagedReference
    private List<SessionUser> sessionUsers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    @JsonManagedReference
    private List<SessionVote> sessionVotes;
    private String selectedRestaurant;
    private SessionStatus status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(created, session.created) && Objects.equals(selectedRestaurant, session.selectedRestaurant) && status == session.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, selectedRestaurant, status);
    }
}
