package sg.gov.lunchbuddy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SessionUser {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Session session;
    private String username;


}
