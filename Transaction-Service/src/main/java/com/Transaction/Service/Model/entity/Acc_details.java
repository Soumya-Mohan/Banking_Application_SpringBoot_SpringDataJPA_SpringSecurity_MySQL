package com.Transaction.Service.Model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Acc_details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acc_id;
    private String accnum;
    private String customerId;
    private boolean mobile_banking_status;
    private boolean net_banking_status;
    private boolean kyc_status;
    private BigDecimal acc_balance;
    private int acc_type;
    private int acc_branch_id;
    @CreationTimestamp
    private Timestamp AOD;
    private Timestamp ACD;
    private boolean Acc_status;
    private String cust_type;
    private String aadharnum;

}
