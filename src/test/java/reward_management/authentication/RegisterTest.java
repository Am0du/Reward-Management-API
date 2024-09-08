package reward_management.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reward_management.dto.response.AuthResponseDto;
import reward_management.dto.request.SignUpDto;
import reward_management.dto.response.UserDto;
import reward_management.exceptions.BadRequest;
import reward_management.management.entity.Reward;
import reward_management.security.JwtUtils;
import reward_management.user.Entity.User;
import reward_management.user.repository.UserRepository;
import reward_management.user.service.UserServiceImpl;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtils.createJwt = mock(Function.class);
    }

    @Test
    public void testRegisterUser_Success() {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("test@example.com");
        signUpDto.setFirstName("John");
        signUpDto.setLastName("Doe");
        signUpDto.setPassword("password");

        Reward reward = new Reward();
        reward.setTotalCashback(0);
        reward.setCurrentBalance(0);

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setReward(reward);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(signUpDto.getEmail());
        when(jwtUtils.createJwt.apply(any(UserDetails.class))).thenReturn("testToken");

        AuthResponseDto<UserDto> response = userService.registerUser(signUpDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());
        assertEquals("testToken", response.getAccessToken());
        assertEquals(signUpDto.getEmail(), response.getData().getEmail());
    }

    @Test
    void registerUser_invalidCredentials() {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("test@example.com");
        signUpDto.setFirstName("John");
        signUpDto.setPassword("password");

        when(passwordEncoder.encode(signUpDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadRequest("Invalid email or password"));

        assertThrows(BadRequest.class, () -> userService.registerUser(signUpDto));
    }
}
