package com.Transaction.Service.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTransactionDto {

    private Date fromDate;
    private Date toDate;
    private String accNum;
    private String requestType;
    private int pageNo;
    private int pageSize;


}
