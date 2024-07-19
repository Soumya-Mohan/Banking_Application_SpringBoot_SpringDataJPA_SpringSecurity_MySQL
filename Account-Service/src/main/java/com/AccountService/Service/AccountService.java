package com.AccountService.Service;

import com.AccountService.Model.dto.Response;

public interface AccountService {

    Response createAccount(String number);

    Response getAccountDetailsByAccNum(String accNum);

    Response getBalance(String accNum);

    Response updateStatusByType(String accNum, String updateType, String status);
}
