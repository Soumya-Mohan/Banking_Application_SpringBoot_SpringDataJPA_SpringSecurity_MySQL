package com.Transaction.Service.Model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "transaction_details")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trans_id;

    private String transaction_number;

    private String accnumber;

    @CreationTimestamp
    private Timestamp DOT;

    private int medium_of_trans;

    private int trans_type;

    private BigDecimal trans_amount;

    private String trans_status;

    private String remark;


}
