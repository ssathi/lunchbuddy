package sg.gov.lunchbuddy.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import sg.gov.lunchbuddy.domain.SessionInvite;
import sg.gov.lunchbuddy.domain.SessionUser;
import sg.gov.lunchbuddy.domain.SessionVote;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionDto {
        private String createdBy;
        private List<SessionInvite> invited;
        private List<SessionUser> joined;
        private List<SessionVote> voted;
}
