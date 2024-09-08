package reward_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationError {
    private int statusCode;
    private String error;
    private Map<String, String> detail;
}
