package com.Transaction.Service.Model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TransactionDto {

    private String transaction_number;

    private String accnumber;

    private Timestamp DOT;


    private String medium_of_trans;


    private int trans_type;

    private BigDecimal trans_amount;

    private String trans_status;

    private String remark;

    private String medium_of_transaction_name;

    private String trans_type_name;


    public TransactionDto(java.lang.String transaction_number, java.lang.String accnumber, java.sql.Timestamp DOT, java.math.BigDecimal trans_amount, java.lang.String trans_status, java.lang.String remark, java.lang.String trans_type_name, java.lang.String medium_of_transaction_name) {
        this.transaction_number = transaction_number;
        this.accnumber = accnumber;
        this.DOT = DOT;
        this.trans_amount = trans_amount;
        this.trans_status = trans_status;
        this.remark = remark;
        this.medium_of_transaction_name = medium_of_transaction_name;
        this.trans_type_name = trans_type_name;
    }
}
