package reward_management.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reward_management.dto.request.LoginDto;
import reward_management.dto.request.SignUpDto;
import reward_management.dto.response.AuthResponseDto;
import reward_management.dto.response.RewardDto;
import reward_management.dto.response.UserDto;
import reward_management.enums.RoleEnum;
import reward_management.exceptions.BadRequest;
import reward_management.exceptions.NotFound;
import reward_management.management.entity.Reward;
import reward_management.security.JwtUtils;
import reward_management.user.Entity.Role;
import reward_management.user.Entity.User;
import reward_management.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponseDto<UserDto> registerUser(SignUpDto signUpDto) {
        User user = new User();
        Role role = new Role();
        Reward reward = new Reward();

        user.setEmail(signUpDto.getEmail());
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        reward.setCurrentBalance(0.0);
        reward.setTotalCashback(0.0);

        role.setRole(RoleEnum.ROLE_USER.toString());
        user.addRoles(role);

        user.setReward(reward);

        User savedUser = userRepository.save(user);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpDto.getEmail(),
                            signUpDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.createJwt.apply(userDetails);

            return AuthResponseDto.<UserDto>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .isSuccessful(true)
                    .accessToken(token)
                    .data(loadData(savedUser))
                    .build();
        } catch (AuthenticationException e) {
            throw new BadRequest("Invalid email or password");
        }
    }

    @Override
    public AuthResponseDto<UserDto> loginUser(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.createJwt.apply(userDetails);

            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new NotFound("User not found"));

            return AuthResponseDto.<UserDto>builder()
                    .statusCode(HttpStatus.OK.value())
                    .isSuccessful(true)
                    .accessToken(token)
                    .data(loadData(user))
                    .build();
        } catch (AuthenticationException e) {
            throw new BadRequest("Invalid email or password");
        }

    }

    private UserDto loadData(User user){
        Reward savedReward = user.getReward();
        RewardDto rewardDto = RewardDto.builder()
                .currentBalance(savedReward.getCurrentBalance())
                .totalCashback(savedReward.getTotalCashback())
                .build();

       return  UserDto.builder()
                .userId(user.getId().toString())
                .firstname(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .reward(rewardDto)
                .build();
    }
}