package reward_management.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reward_management.dto.request.LoginDto;
import reward_management.dto.request.SignUpDto;
import reward_management.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto signUpDto){
        return new ResponseEntity<>(userService.registerUser(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);
    }
}
