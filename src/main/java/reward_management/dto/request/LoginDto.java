package reward_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    @Email(message = "Email should be valid")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password is required")
    private String password;
}
