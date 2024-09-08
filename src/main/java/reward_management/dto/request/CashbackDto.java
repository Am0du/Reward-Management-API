package reward_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CashbackDto {
    @NotBlank(message = "amount cannot be blank")
    @NotNull(message = "amount cannot be null")
    private double amount;

    @NotBlank(message = "description cannot be blank")
    @NotNull(message = "description cannot be null")
    private String description;
}
