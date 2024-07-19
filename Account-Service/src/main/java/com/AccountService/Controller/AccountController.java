package com.AccountService.Controller;

import com.AccountService.Model.dto.Response;
import com.AccountService.Model.utility.Constants;
import com.AccountService.Service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    AccountService accountService;

    @PostMapping("/createAccount")
    public Response createAccount(@RequestParam String number) {
        log.info("getting profile details using : {}", number);
        if (number.isEmpty() || number.equals(null) || number.isBlank()) {
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        return accountService.createAccount(number);
    }

    @GetMapping("getDetailsByAccNum")
    public Response getAccountDetailsByAccNum(@RequestParam String accNum) {
        log.info("Inside AccountController ::getAccountDetailsByAccNum : {}", accNum);
        if (accNum.isEmpty() || accNum.equals(null) || accNum.isBlank()) {
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }

        return accountService.getAccountDetailsByAccNum(accNum);
    }

    @GetMapping("getBalance")
    public Response getBalance(@RequestParam String accNum) {
        log.info("Inside AccountController ::getBalance : {}", accNum);
        if (accNum.isEmpty() || accNum.equals(null) || accNum.isBlank()) {
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        return accountService.getBalance(accNum);

    }

    @PatchMapping("/updateStatusByType")
    public Response updateStatusByType(@RequestParam String accNum, @RequestParam String updateType, @RequestParam String status) {
        log.info("Inside AccountController ::updateStatusByType : {}", accNum);
        if (accNum.isEmpty() || accNum.equals(null) || accNum.isBlank()) {
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        return accountService.updateStatusByType(accNum, updateType, status);

    }


}
