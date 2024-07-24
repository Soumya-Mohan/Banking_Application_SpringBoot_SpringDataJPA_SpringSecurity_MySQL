package com.Transaction.Service.Model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TransactionDto {

    private String transactionNumber;

   // @JsonProperty("acc_number")
    private String accNumber;

    //@CreationTimestamp
    private Timestamp DOT;

    //@JsonProperty("medium_of_trans")
    private int mediumOfTransaction;

    //@JsonProperty("trans_type")
    private int transType;

    //@JsonProperty("trans_amount")
    private BigDecimal transAmount;

   // @JsonProperty("trans_status")
    private boolean transStatus;

    private String remark;


    private String transTypeName;

    private String mediumOfTransactionName;

}
