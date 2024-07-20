package com.AccountService.Repository;

import com.AccountService.Model.dto.AccountDto;
import com.AccountService.Model.entity.Acc_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface AccountDetailsRepository extends JpaRepository<Acc_details, Integer> {

    Optional<Acc_details> findAcc_detailsByAccnumOrCustomerId(String accNum, String customerId);

    Optional<Acc_details> findAcc_detailsByaadharnum(String aadharNum);

    @Query(" select new com.AccountService.Model.dto.AccountDto(a.accnum,a.customerId,a.mobile_banking_status,a.net_banking_status,a.kyc_status,a.acc_balance,a.acc_type,a.acc_branch_id,a.AOD,a.ACD,a.Acc_status,a.cust_type,a.aadharnum ,b.b_name,b.b_add,b.b_code,b.b_ph_num) from Acc_details a inner join Branch b on a.acc_branch_id=b.b_id where a.accnum=:accNum or a.customerId=:accNum ")
     AccountDto findAcc_detailsByAccnumOrCustomerIdWithBranchDetails(String accNum);
}
