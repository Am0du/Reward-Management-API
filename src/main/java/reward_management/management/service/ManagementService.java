package reward_management.management.service;

import reward_management.dto.request.CashbackDto;
import reward_management.dto.response.GeneralResponseDto;
import reward_management.dto.response.ListResponseDto;
import reward_management.user.Entity.User;

public interface ManagementService {

    GeneralResponseDto<?> getRewards(String userId);

    ListResponseDto<?> cashbackHistory(String userId, int page, int size);

    GeneralResponseDto<?> addCashback(User user, CashbackDto cashbackDto);
}
