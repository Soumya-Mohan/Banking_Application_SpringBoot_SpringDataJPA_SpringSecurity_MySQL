package com.AccountService.Model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AccountDto implements Serializable {


    @JsonProperty("AccountNumber")

    private String accnum;

    @JsonProperty("CustomerID")
    private String customerId;

    @JsonProperty("MobileBankingStatus")
    private boolean mobile_banking_status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("NetBankingStatus")
    private boolean net_banking_status;

    @JsonProperty("KYCStatus")
    private boolean kyc_status;

    @JsonProperty("AccountBalance")
    private BigDecimal acc_balance;

    @JsonProperty("AccountType")
    private int acc_type;

    @JsonProperty("AccountBranchId")
    private int acc_branch_id;

    @CreationTimestamp
    private Timestamp AOD;

    private Timestamp ACD;

    @JsonProperty("AccountStatus")
    private boolean Acc_status;

    @JsonProperty("CustomerType")
    private String cust_type;

    @JsonProperty("AadharNumber")
    private String aadharnum;

    public AccountDto(BigDecimal accBalance) {
        this.acc_balance = accBalance;
    }
}
