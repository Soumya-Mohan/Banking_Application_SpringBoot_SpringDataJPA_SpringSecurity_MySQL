package com.Transaction.Service.Model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction_type")
    private Integer idTransactionType;

    @Column(name = "trans_type_name", length = 45, nullable = false)
    private String transTypeName;


}
