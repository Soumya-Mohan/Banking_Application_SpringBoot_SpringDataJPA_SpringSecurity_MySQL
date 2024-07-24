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

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "acc_number")
    private String accNumber;

    @CreationTimestamp
    private Timestamp DOT;

    @Column(name = "medium_of_trans")
    private int mediumOfTransaction;

    @Column(name = "trans_type")
    private int transType;

    @Column(name = "trans_amount")
    private BigDecimal transAmount;

    @Column(name = "trans_status")
    private String transStatus;

    private String remark;






}
