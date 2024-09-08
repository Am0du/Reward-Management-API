package reward_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reward_management.dto.response.RewardDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private String userId;
    private String firstname;
    private String lastName;
    private String email;
    private RewardDto reward;
}