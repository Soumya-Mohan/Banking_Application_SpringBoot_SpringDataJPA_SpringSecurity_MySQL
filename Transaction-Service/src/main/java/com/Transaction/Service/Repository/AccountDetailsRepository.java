package com.Transaction.Service.Repository;


import com.Transaction.Service.Model.entity.Acc_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDetailsRepository extends JpaRepository<Acc_details, Integer> {

    Optional<Acc_details> findAcc_detailsByAccnum(String accNum);

}
