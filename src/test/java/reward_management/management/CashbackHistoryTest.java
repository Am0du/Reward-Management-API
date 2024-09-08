package reward_management.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import reward_management.dto.response.CashbackHistoryDto;
import reward_management.dto.response.ListResponseDto;
import reward_management.management.entity.CashbackHistory;
import reward_management.management.repository.CashBackHistoryRepository;
import reward_management.management.service.ManagementServiceImpl;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CashbackHistoryTest {

    @Mock
    private CashBackHistoryRepository cashBackHistoryRepository;

    @InjectMocks
    private ManagementServiceImpl managementService;

    @Test
    void testCashbackHistory_Success() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdDate")));
        CashbackHistory cashbackHistory = new CashbackHistory();
        cashbackHistory.setId(UUID.randomUUID());
        cashbackHistory.setAmount(50.0);
        cashbackHistory.setDescription("Test cashback");

        Page<CashbackHistory> cashbackHistories = new PageImpl<>(Collections.singletonList(cashbackHistory), pageable, 1);

        when(cashBackHistoryRepository.findByUserId(userId, pageable)).thenReturn(cashbackHistories);

        ListResponseDto<?> response = managementService.cashbackHistory(userId.toString(), 0, 10);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());
        assertEquals(10, response.getSize());
    }

    @Test
    void testCashbackHistory_Empty() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdDate")));
        Page<CashbackHistory> cashbackHistories = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(cashBackHistoryRepository.findByUserId(userId, pageable)).thenReturn(cashbackHistories);

        ListResponseDto<?> response = managementService.cashbackHistory(userId.toString(), 0, 10);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.isSuccessful());
    }
}
