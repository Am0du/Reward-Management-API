package reward_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GeneralResponseDto<T> {
    private int statusCode;
    private boolean isSuccessful;
    private T data;
}
