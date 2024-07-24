package com.Transaction.Service.Service;

import com.Transaction.Service.Model.dto.Response;
import com.Transaction.Service.Model.dto.TransactionDto;
import com.Transaction.Service.Model.entity.TransactionDetails;
import com.Transaction.Service.Model.utility.Constants;
import com.Transaction.Service.Model.utility.Validation;
import com.Transaction.Service.Repository.AccountDetailsRepository;
import com.Transaction.Service.Repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements  TransactionService {

    @Autowired
    AccountDetailsRepository accountDetailsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TransactionRepository transactionRepository;

    private static  Response response = new Response();


    @Override
    public Response createTransaction(TransactionDto transactionDto) {
        log.info("Inside TransactionServiceImpl::createTransaction");
        return saveTransactionDetailsAndUpdateAccount(transactionDto);

    }

    private Response saveTransactionDetailsAndUpdateAccount(TransactionDto transactionDto) {
        accountDetailsRepository.findAcc_detailsByAccnum(transactionDto.getAccNumber()).ifPresentOrElse(
                (account)->{
                    TransactionDetails transactionDetails=new TransactionDetails();
                    if(transactionDto.getTransType()==1){//deposit
                        account.setAcc_balance(account.getAcc_balance().add(transactionDto.getTransAmount()));
                    }
                    else if(transactionDto.getTransType()==2){//withdraw
                        if(!account.isAcc_status()){
                            log.error("account is either inactive/closed, cannot process the transaction");
                            response.setMessage(Constants.ACCOUNT_INACTIVE_CLOSED);
                            response.setCode(Constants.ACCOUNT_INACTIVE_CLOSED_CODE);
                        }
                        if(account.getAcc_balance().compareTo(transactionDto.getTransAmount())<0){
                            log.error("insufficient balance in the account");
                            response.setMessage(Constants.ACCOUNT_INSUFFICIENT_BALANCE);
                            response.setCode(Constants.ACCOUNT_INSUFFICIENT_BALANCE_CODE);
                        }
                        account.setAcc_balance(account.getAcc_balance().subtract(transactionDto.getTransAmount()));
                        transactionDetails.setTransAmount(transactionDto.getTransAmount().negate());
                    }
                    transactionDetails=modelMapper.map(transactionDto,TransactionDetails.class);
                    transactionDetails.setTransStatus(Constants.TRANSACTION_STATUS);
                    transactionDetails.setTransactionNumber(Validation.generateRandomNumber(10));
                    transactionRepository.save(transactionDetails);

                    response.setMessage(Constants.SUCCESS);
                    response.setCode(Constants.SUCCESS_CODE);
                    response.setData(transactionDetails.toString());
                },
                () -> {
                    log.info("The Given account details are not found in the database");
                    response.setMessage(Constants.DATA_NOT_FOUND);
                    response.setCode(Constants.DATA_NOT_FOUND_CODE);
                }
        );
        return response;
    }
}
