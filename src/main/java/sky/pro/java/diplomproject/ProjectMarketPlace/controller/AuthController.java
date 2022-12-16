package sky.pro.java.diplomproject.ProjectMarketPlace.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.LoginReqDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.RegisterReqDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.AuthService;

import static sky.pro.java.diplomproject.ProjectMarketPlace.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().body("Аутентификация прошла успешно!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto req) {
        LOGGER.info("Was invoked method of AuthController for register new user.");
        if (req.getRole() == null) {
            req.setRole(USER);
        }
        if (authService.register(req)) {
            return ResponseEntity.ok().body("Регистрация прошла успешно!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
