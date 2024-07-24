package com.Transaction.Service.Service;

import com.Transaction.Service.Model.dto.Response;
import com.Transaction.Service.Model.dto.TransactionDto;

public interface TransactionService {
    Response createTransaction(TransactionDto transactionDto);
}
