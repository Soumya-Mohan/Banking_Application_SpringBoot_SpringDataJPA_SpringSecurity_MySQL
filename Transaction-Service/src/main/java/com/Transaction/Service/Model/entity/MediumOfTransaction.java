package com.Transaction.Service.Model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "medium_of_transaction")
public class MediumOfTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medium_of_transaction")
    private Integer idMediumOfTransaction;

    @Column(name = "medium_of_transaction_name", length = 45, nullable = false)
    private String mediumOfTransactionName;

}
