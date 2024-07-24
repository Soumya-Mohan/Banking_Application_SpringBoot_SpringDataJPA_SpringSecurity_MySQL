package com.Transaction.Service.Model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class medium_of_transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_medium_of_transaction;

    private String medium_of_transaction_name;

}
