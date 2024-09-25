package com.Transaction.Service.Service;

import com.Transaction.Service.Model.dto.GetTransactionDto;
import com.Transaction.Service.Model.dto.Mail;
import com.Transaction.Service.Model.dto.Response;
import com.Transaction.Service.Model.dto.TransactionDto;
import com.Transaction.Service.Model.entity.TransactionDetails;
import com.Transaction.Service.Model.utility.Constants;
import com.Transaction.Service.Model.utility.PDFGenerator;
import com.Transaction.Service.Model.utility.Validation;
import com.Transaction.Service.Repository.AccountDetailsRepository;
import com.Transaction.Service.Repository.TransactionRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    AccountDetailsRepository accountDetailsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PDFGenerator pdfGenerator;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Response createTransaction(TransactionDto transactionDto) {
        log.info("Inside TransactionServiceImpl::createTransaction");
        return saveTransactionDetailsAndUpdateAccount(transactionDto);

    }

    @Override
    public Response getTransactionStatement(GetTransactionDto getTransactionDto) throws IOException {
        log.info("Inside TransactionServiceImpl::getTransactionStatement with data ", getTransactionDto);
        Response response = new Response();
        if (getTransactionDto.getRequestType().equals("Download")) {
            response = downloadTransactionDetailsByDateRange(getTransactionDto);
        } else if (getTransactionDto.getRequestType().equals("Email")) {
            response = emailTransactionDetailsByDateRange(getTransactionDto);
        } else {
            response = getAllTransaction(getTransactionDto);
        }

        return response;
    }

    private Response getAllTransaction(GetTransactionDto getTransactionDto) {
        log.info("Inside TransactionServiceImpl::getTransactionStatement");
        Pageable paging = PageRequest.of(getTransactionDto.getPageNo(), getTransactionDto.getPageSize(), Sort.by("DOT").descending());
        Page<TransactionDetails> pageable = transactionRepository.findTransactionDetailsByaccnumber(getTransactionDto.getAccNum(), paging);
        return (pageable.hasContent()) ?
                Response.builder().message(Constants.SUCCESS).code(Constants.SUCCESS_CODE).data(pageable).build()
                :
                Response.builder().message(Constants.DATA_NOT_FOUND).code(Constants.DATA_NOT_FOUND_CODE).build();
    }

    private Response emailTransactionDetailsByDateRange(GetTransactionDto getTransactionDto) throws IOException {
        Response response = new Response();
        String path = pdfGenerator.generatePdfReport(getTransactionDto);
        sendEmailWithAttachment();
        return response;
    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {
        Mail mail = new Mail();
        mail.setMailFrom("soumya.sphatige@gmail.com");
        mail.setMailTo("soumya.sphatige@gmail.com");
        mail.setMailSubject("Spring Boot - Email demo");
        mail.setMailContent("Just testing");
        sendEmail(mail);

    }


    public void sendEmail(Mail mail)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }





    private Response downloadTransactionDetailsByDateRange(GetTransactionDto getTransactionDto) {
        log.info("Inside TransactionServiceImpl::getTransactionStatement wirh downloadTransactionDetailsByDateRange ");
        Response response = new Response();
        String path = pdfGenerator.generatePdfReport(getTransactionDto);
        return (path != null) ?
                Response.builder().message(Constants.SUCCESS).code(Constants.SUCCESS_CODE).data(path).build()
                :
                Response.builder().message(Constants.DATA_NOT_FOUND).code(Constants.DATA_NOT_FOUND_CODE).build();
    }

    private Response saveTransactionDetailsAndUpdateAccount(TransactionDto transactionDto) {
        Response response = new Response();
        accountDetailsRepository.findAcc_detailsByAccnum(transactionDto.getAccnumber()).ifPresentOrElse(
                (account) -> {
                    TransactionDetails transactionDetails = new TransactionDetails();
                    if (transactionDto.getTrans_type() == 1) {//deposit
                        account.setAcc_balance(account.getAcc_balance().add(transactionDto.getTrans_amount()));
                    } else if (transactionDto.getTrans_type() == 2) {//withdraw
                        if (!account.isAcc_status()) {
                            log.error("account is either inactive/closed, cannot process the transaction");
                            response.setMessage(Constants.ACCOUNT_INACTIVE_CLOSED);
                            response.setCode(Constants.ACCOUNT_INACTIVE_CLOSED_CODE);
                        }
                        if (account.getAcc_balance().compareTo(transactionDto.getTrans_amount()) < 0) {
                            log.error("insufficient balance in the account");
                            response.setMessage(Constants.ACCOUNT_INSUFFICIENT_BALANCE);
                            response.setCode(Constants.ACCOUNT_INSUFFICIENT_BALANCE_CODE);
                        }
                        account.setAcc_balance(account.getAcc_balance().subtract(transactionDto.getTrans_amount()));
                        transactionDetails.setTrans_amount(transactionDto.getTrans_amount().negate());
                    }
                    transactionDetails = modelMapper.map(transactionDto, TransactionDetails.class);
                    transactionDetails.setTrans_status(Constants.TRANSACTION_STATUS);
                    transactionDetails.setTransaction_number(Validation.generateRandomNumber(10));
                    transactionRepository.save(transactionDetails);

                    response.setMessage(Constants.SUCCESS);
                    response.setCode(Constants.SUCCESS_CODE);
                    response.setData(transactionDetails.toString());
                },
                () -> {
                    log.info(Constants.ACCOUNT_DOES_NOT_EXITS);
                    response.setMessage(Constants.DATA_NOT_FOUND);
                    response.setCode(Constants.DATA_NOT_FOUND_CODE);
                }
        );
        return response;
    }
}
