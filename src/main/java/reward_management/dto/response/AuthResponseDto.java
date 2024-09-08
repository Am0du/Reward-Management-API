package reward_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthResponseDto<T> {
    private int statusCode;
    private boolean isSuccessful;
    private String accessToken;
    private T data;

}
