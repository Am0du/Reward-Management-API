package reward_management.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import reward_management.dto.request.LoginDto;
import reward_management.dto.response.AuthResponseDto;
import reward_management.dto.response.UserDto;
import reward_management.exceptions.BadRequest;
import reward_management.exceptions.NotFound;
import reward_management.management.entity.Reward;
import reward_management.security.JwtUtils;
import reward_management.user.Entity.User;
import reward_management.user.repository.UserRepository;
import reward_management.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoginTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(null);
        jwtUtils.createJwt = mock(Function.class);
    }

    @Test
    void testLoginUser_Success() {

        String email = "test@example.com";
        String password = "password";
        UUID userId = UUID.randomUUID();
        LoginDto loginDto = new LoginDto(email, password);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        Reward reward = new Reward();
        reward.setTotalCashback(100.0);
        reward.setCurrentBalance(50.0);
        user.setReward(reward);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
        String token = "dummy-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtUtils.createJwt.apply(userDetails)).thenReturn(token);

        AuthResponseDto<UserDto> response = userService.loginUser(loginDto);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());
        assertEquals(token, response.getAccessToken());
    }

    @Test
    void testLoginUser_InvalidCredentials() {

        LoginDto loginDto = new LoginDto("test@example.com", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadRequest("Invalid email or password"));

        assertThrows(BadRequest.class, () -> userService.loginUser(loginDto));
    }

    @Test
    void testLoginUser_UserNotFound() {

        String email = "test@example.com";
        String password = "password";
        LoginDto loginDto = new LoginDto(email, password);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> userService.loginUser(loginDto));

    }

}
