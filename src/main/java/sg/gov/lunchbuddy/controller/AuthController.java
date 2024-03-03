package sg.gov.lunchbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.gov.lunchbuddy.config.TokenIssuer;

@RestController
@RequestMapping("/auth")
public class AuthController {

    record UserDto(String username, String password, String others) {}

    @Autowired
    private TokenIssuer issuer;

    @PostMapping("/login")
    public String getUser(@RequestBody UserDto dto) {

        return issuer.issue(dto.username);
    }


}
