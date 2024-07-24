package com.Transaction.Service.Repository;

import com.Transaction.Service.Model.dto.TransactionDto;
import com.Transaction.Service.Model.entity.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionDetails, Integer> {

    Page<TransactionDetails> findTransactionDetailsByaccnumber(String accNum, Pageable paging);

    @Query("select new com.Transaction.Service.Model.dto.TransactionDto(a.transaction_number,a.accnumber,a.DOT,a.trans_amount,a.trans_status,a.remark,c.trans_type_name,b.medium_of_transaction_name) from TransactionDetails a inner join medium_of_transaction b on a.medium_of_trans=b.id_medium_of_transaction inner join TransactionType c on a.trans_type=c.id_transaction_type where a.accnumber=:accNum and a.DOT between  :fromDate AND :toDate")
    List<TransactionDto> getAllTransactionDetailsByDateRange(String accNum, Date fromDate, Date toDate);
}
