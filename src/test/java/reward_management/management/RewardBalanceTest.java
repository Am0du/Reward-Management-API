package reward_management.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reward_management.dto.response.GeneralResponseDto;
import reward_management.dto.response.RewardDto;
import reward_management.exceptions.NotFound;
import reward_management.management.entity.Reward;
import reward_management.management.repository.RewardsRepository;
import reward_management.management.service.ManagementServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RewardBalanceTest {

    @Mock
    private RewardsRepository rewardsRepository;

    @InjectMocks
    private ManagementServiceImpl managementService;

    @Test
    void testGetRewards_Success() {
        UUID userId = UUID.randomUUID();
        Reward reward = new Reward();
        reward.setTotalCashback(100.0);
        reward.setCurrentBalance(50.0);

        when(rewardsRepository.findByUserId(userId)).thenReturn(Optional.of(reward));

        GeneralResponseDto<?> response = managementService.getRewards(userId.toString());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());
        RewardDto rewardDto = (RewardDto) response.getData();
        assertEquals(100.0, rewardDto.getTotalCashback());
        assertEquals(50.0, rewardDto.getCurrentBalance());
    }

    @Test
    void testGetRewards_NotFound() {
        UUID userId = UUID.randomUUID();

        when(rewardsRepository.findByUserId(userId)).thenReturn(Optional.empty());

        NotFound exception = Assertions.assertThrows(NotFound.class, () -> {
            managementService.getRewards(userId.toString());
        });

        assertEquals("Reward balance not found", exception.getMessage());
    }
}
