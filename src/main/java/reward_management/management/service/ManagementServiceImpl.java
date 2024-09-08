package reward_management.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reward_management.dto.request.CashbackDto;
import reward_management.dto.response.CashbackHistoryDto;
import reward_management.dto.response.GeneralResponseDto;
import reward_management.dto.response.ListResponseDto;
import reward_management.dto.response.RewardDto;
import reward_management.exceptions.NotFound;
import reward_management.management.entity.CashbackHistory;
import reward_management.management.entity.Reward;
import reward_management.management.repository.CashBackHistoryRepository;
import reward_management.management.repository.RewardsRepository;
import reward_management.user.Entity.User;
import reward_management.utils.Utils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{

    private final CashBackHistoryRepository cashBackHistoryRepository;
    private final RewardsRepository rewardsRepository;

    @Override
    public GeneralResponseDto<?> getRewards(String userId) {

        UUID user = Utils.stringToUuid(userId);
        Reward rewards = rewardsRepository.findByUserId(user)
                .orElseThrow(() -> new NotFound("Reward balance not found"));

        RewardDto rewardDto = RewardDto.builder()
                .totalCashback(rewards.getTotalCashback())
                .currentBalance(rewards.getCurrentBalance()).build();

        return GeneralResponseDto.builder()
                .isSuccessful(true)
                .statusCode(HttpStatus.OK.value())
                .data(rewardDto).build();
    }

    @Override
    public ListResponseDto<?> cashbackHistory(String userId, int page, int size) {

        UUID user = Utils.stringToUuid(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        Page<CashbackHistory> cashbackHistories = cashBackHistoryRepository.findByUserId(user, pageable);

        if (cashbackHistories.isEmpty())
            return ListResponseDto.builder()
                    .isSuccessful(true)
                    .statusCode(HttpStatus.OK.value())
                    .size(cashbackHistories.getSize())
                    .page(cashbackHistories.getNumber())
                    .total(cashbackHistories.getTotalPages())
                    .data(Collections.emptyList()).build();

        List<CashbackHistoryDto> cashbackHistoryDtoList = cashbackHistories.stream()
                .map(this::convertToDTO).toList();

        Page<CashbackHistoryDto> page1 = new PageImpl<>(cashbackHistoryDtoList,
                pageable, cashbackHistories.getTotalElements());

        return ListResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .size(page1.getSize())
                .total(page1.getTotalPages())
                .page(page1.getNumber())
                .data(page1.getContent())
                .build();
    }

    @Override
    public GeneralResponseDto<?> addCashback(User user, CashbackDto cashbackDto) {
        CashbackHistory cashbackHistory = new CashbackHistory();
        cashbackHistory.setAmount(cashbackDto.getAmount());
        cashbackHistory.setDescription(cashbackDto.getDescription());
        cashbackHistory.setUser(user);
        CashbackHistory saveCashBackHistory = cashBackHistoryRepository.save(cashbackHistory);

        Reward reward = user.getReward();
        reward.setTotalCashback(reward.getTotalCashback() + saveCashBackHistory.getAmount());
        reward.setCurrentBalance(reward.getCurrentBalance() + saveCashBackHistory.getAmount());

        rewardsRepository.save(reward);

        CashbackHistoryDto cashbackHistoryDto = convertToDTO(saveCashBackHistory);
        return GeneralResponseDto.builder()
                .isSuccessful(true)
                .statusCode(HttpStatus.CREATED.value())
                .data(cashbackHistoryDto)
                .build();
    }

    private CashbackHistoryDto convertToDTO(CashbackHistory cashbackHistory){
        return CashbackHistoryDto.builder()
                .transactionId(cashbackHistory.getId())
                .amount(cashbackHistory.getAmount())
                .description(cashbackHistory.getDescription())
                .createdDate(cashbackHistory.getCreatedDate())
                .build();
    }
}
