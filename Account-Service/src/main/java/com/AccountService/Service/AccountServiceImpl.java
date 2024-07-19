package com.AccountService.Service;

import com.AccountService.Model.dto.AccountDto;
import com.AccountService.Model.dto.Response;
import com.AccountService.Model.entity.Acc_details;
import com.AccountService.Model.utility.Constants;
import com.AccountService.Repository.AccountDetailsRepository;
import com.AccountService.exception.ResourceConflict;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.AccountService.Model.utility.Validation.generateRandomNumber;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private static Response response = new Response();
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @KafkaListener(topics = "Create-account", groupId = "group_id")
    public void receiveMessage(String aadhar_num) throws JsonProcessingException {
        createAccountNumberUsingAadharCard(aadhar_num);
    }

    /**
     * @param aadharNum as aadhar or pan card number
     * @return Response
     */
    @Override
    public Response createAccount(String aadharNum) {
        accountDetailsRepository.findAcc_detailsByaadharnum(aadharNum).ifPresent(account -> {
            log.error("Account already exists on the server");
            throw new ResourceConflict("Account already exists on the server");
        });
        Acc_details accDetails = createAccountNumberUsingAadharCard(aadharNum);
        AccountDto accountDto = modelMapper.map(accDetails, AccountDto.class);
        return Response.builder().code(Constants.SUCCESS_CODE).message(Constants.ACCOUNT_CREATED_SUCCESSFUL)
                .data(accountDto).build();
    }

    /**
     * @param accNum
     * @return
     */
    @Override
    public Response getAccountDetailsByAccNum(String accNum) {
        log.info("Inside AccountServiceImpl ::getAccountDetailsByAccNum : {}", accNum);
        accountDetailsRepository.findAcc_detailsByAccnumOrCustomerId(accNum, accNum).ifPresentOrElse((account) -> {
            log.info("Inside AccountServiceImpl::getAccountDetailsByAccNum ==nThe account details are fetched");
            response.setMessage(Constants.SUCCESS);
            response.setCode(Constants.SUCCESS_CODE);
            response.setData(account);
        }, () -> {
            log.error("Inside AccountServiceImpl::getAccountDetailsByAccNum ==The account details are not found in database");
            response.setMessage(Constants.DATA_NOT_FOUND);
            response.setCode(Constants.DATA_NOT_FOUND_CODE);
        });
        return response;
    }

    /**
     * @param accNum
     * @return
     */
    @Override
    public Response getBalance(String accNum) {
        log.info("Inside AccountServiceImpl ::getBalance : {}", accNum);
        accountDetailsRepository.findAcc_detailsByAccnumOrCustomerId(accNum, accNum).ifPresentOrElse((account) -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setAcc_balance(account.getAcc_balance());
            log.info("Inside AccountServiceImpl::getBalance ==nThe account details are fetched");
            response.setMessage(Constants.SUCCESS);
            response.setCode(Constants.SUCCESS_CODE);
            response.setData(accountDto);
        }, () -> {
            log.error("Inside AccountServiceImpl::getBalance ==The account details are not found in database");
            response.setMessage(Constants.DATA_NOT_FOUND);
            response.setCode(Constants.DATA_NOT_FOUND_CODE);
        });
        return response;
    }

    @Override
    public Response updateStatusByType(String accNum, String updateType, String status) {
        log.info("Inside AccountServiceImpl ::updateStatusByType : {}", accNum);
        accountDetailsRepository.findAcc_detailsByAccnumOrCustomerId(accNum, accNum).ifPresentOrElse((account) -> {
            log.info("Inside AccountServiceImpl::updateStatusByType ==The account is closed");
            switch (updateType) {

                case "Account_Close":
                    log.info("Inside AccountServiceImpl ::updateStatusByType with Account_Close case");
                    account.setAcc_status(Boolean.parseBoolean(status));
                    break;
                case "mobile_Banking":
                    log.info("Inside AccountServiceImpl ::updateStatusByType with mobile_Banking case");
                    account.setMobile_banking_status(Boolean.parseBoolean(status));
                    break;
                case "net_Banking":
                    log.info("Inside AccountServiceImpl ::updateStatusByType with net_Banking case");
                    account.setNet_banking_status(Boolean.parseBoolean(status));
                    break;
                case "KYC":
                    log.info("Inside AccountServiceImpl ::updateStatusByType with KYC case");
                    account.setKyc_status(Boolean.parseBoolean(status));
                    break;
                case "branch":
                    log.info("Inside AccountServiceImpl ::updateStatusByType with branch case");
                    account.setAcc_branch_id(Integer.valueOf(status));
                    break;

            }
            response.setMessage(Constants.SUCCESS);
            response.setCode(Constants.SUCCESS_CODE);
        }, () -> {
            log.error("Inside AccountServiceImpl::updateStatusByType ==The account details are not found in database");
            response.setMessage(Constants.DATA_NOT_FOUND);
            response.setCode(Constants.DATA_NOT_FOUND_CODE);
        });
        return response;

    }

    private Acc_details createAccountNumberUsingAadharCard(String aadhar_num) {
        Acc_details accountDetails = new Acc_details();
        accountDetails.setAccnum(Constants.ACC_PREFIX + generateRandomNumber(7));
        accountDetails.setAadharnum(aadhar_num);
        accountDetails.setAcc_balance(BigDecimal.valueOf(0));
        accountDetails.setCustomerId(generateRandomNumber(8));
        accountDetails.setMobile_banking_status(false);
        accountDetails.setNet_banking_status(false);
        accountDetails.setKyc_status(false);
        accountDetails.setAcc_type(1);
        accountDetails.setAcc_branch_id(1);
        accountDetails.setAcc_status(true);
        accountDetails.setCust_type("regular");
        accountDetailsRepository.save(accountDetails);
        return accountDetails;
    }


}
