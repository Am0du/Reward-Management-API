package reward_management.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reward_management.dto.request.CashbackDto;
import reward_management.dto.response.CashbackHistoryDto;
import reward_management.dto.response.GeneralResponseDto;
import reward_management.management.entity.CashbackHistory;
import reward_management.management.entity.Reward;
import reward_management.management.repository.CashBackHistoryRepository;
import reward_management.management.repository.RewardsRepository;
import reward_management.management.service.ManagementServiceImpl;
import reward_management.user.Entity.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AddCashbackTest {

    @Mock
    private CashBackHistoryRepository cashBackHistoryRepository;

    @Mock
    private RewardsRepository rewardsRepository;

    @InjectMocks
    private ManagementServiceImpl managementService;

    @Test
    void testAddCashback_Success() {
        UUID userId = UUID.randomUUID();

        User user = new User();
        Reward reward = new Reward();
        reward.setTotalCashback(100.0);
        reward.setCurrentBalance(50.0);
        user.setReward(reward);

        CashbackDto cashbackDto = new CashbackDto();
        cashbackDto.setAmount(50.0);
        cashbackDto.setDescription("Test cashback");

        CashbackHistory cashbackHistory = new CashbackHistory();
        cashbackHistory.setAmount(cashbackDto.getAmount());
        cashbackHistory.setDescription(cashbackDto.getDescription());
        cashbackHistory.setUser(user);

        when(cashBackHistoryRepository.save(cashbackHistory)).thenReturn(cashbackHistory);
        when(rewardsRepository.save(reward)).thenReturn(reward);

        GeneralResponseDto<?> response = managementService.addCashback(user, cashbackDto);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());

        CashbackHistoryDto cashbackHistoryDto = (CashbackHistoryDto) response.getData();
        assertEquals(cashbackHistory.getId(), cashbackHistoryDto.getTransactionId());
        assertEquals(50.0, cashbackHistoryDto.getAmount());
        assertEquals("Test cashback", cashbackHistoryDto.getDescription());

        verify(cashBackHistoryRepository).save(cashbackHistory);
        verify(rewardsRepository).save(reward);
    }
}
