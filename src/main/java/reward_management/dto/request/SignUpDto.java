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
public class SignUpDto {

    @NotBlank(message = "firstName cannot be blank")
    @NotNull(message = "firstName cannot be null")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @NotNull(message = "lastName cannot be null")
    private String lastName;

    @NotBlank(message = "email cannot be blank")
    @NotNull(message = "email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;
}