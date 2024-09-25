package com.Transaction.Service.controller;

import com.Transaction.Service.Model.dto.GetTransactionDto;
import com.Transaction.Service.Model.dto.Response;
import com.Transaction.Service.Model.dto.TransactionDto;
import com.Transaction.Service.Model.utility.Constants;
import com.Transaction.Service.Service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @PostMapping("/createTransaction")
    public Response createTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("Inside TransactionController::createTransaction");
        if (transactionDto.equals(null)) {
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/getTransactionStatement")
    public Response getTransactionStatement(@RequestBody GetTransactionDto getTransactionDto) throws IOException {
        log.info("Inside TransactionController::getTransactionStatement");
        return transactionService.getTransactionStatement(getTransactionDto);

    }

}
