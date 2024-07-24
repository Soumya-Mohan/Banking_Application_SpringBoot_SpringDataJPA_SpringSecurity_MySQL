package com.Transaction.Service.Repository;

import com.Transaction.Service.Model.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDetails, Integer> {



}
