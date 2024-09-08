package reward_management.user.service;

import reward_management.dto.response.AuthResponseDto;
import reward_management.dto.request.LoginDto;
import reward_management.dto.request.SignUpDto;
import reward_management.dto.response.UserDto;

public interface UserService {

    AuthResponseDto<UserDto> registerUser(SignUpDto userDto);
    AuthResponseDto<UserDto> loginUser(LoginDto loginDto);
}
