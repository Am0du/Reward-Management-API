package reward_management.management.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reward_management.dto.request.CashbackDto;
import reward_management.exceptions.Forbidden;
import reward_management.management.service.ManagementService;
import reward_management.user.Entity.User;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rewards")
@Tag(name = "Management")
public class ManagementController {

    private final ManagementService managementService;

    @GetMapping("/balance/{customerId}")
    public ResponseEntity<?> getBalance(@PathVariable("customerId") String customerId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if (!user.getId().toString().equals(customerId))
            throw new Forbidden("You don't have the permission to access this resource");

        return new ResponseEntity<>(managementService.getRewards(customerId), HttpStatus.OK);
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<?> getCashBackHistory(@PathVariable("customerId") String customerId,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        if (!user.getId().toString().equals(customerId))
            throw new Forbidden("You don't have the permission to access this resource");

        return new ResponseEntity<>(managementService.cashbackHistory(customerId, page, size), HttpStatus.OK);
    }

    @PostMapping("/history")
    public ResponseEntity<?> addCashback(@RequestBody @Valid CashbackDto cashbackDto,
                                         Authentication authentication){

        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(managementService.addCashback(user, cashbackDto), HttpStatus.CREATED);
    }
}
