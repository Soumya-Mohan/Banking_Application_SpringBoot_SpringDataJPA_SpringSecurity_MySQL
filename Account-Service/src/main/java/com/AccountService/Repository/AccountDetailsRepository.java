package com.AccountService.Repository;

import com.AccountService.Model.entity.Acc_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountDetailsRepository extends JpaRepository<Acc_details, Integer> {

    Optional<Acc_details> findAcc_detailsByAccnumOrCustomerId(String accNum, String customerId);

    Optional<Acc_details> findAcc_detailsByaadharnum(String aadharNum);
}
