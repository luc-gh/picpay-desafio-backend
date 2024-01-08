package com.spring.picpaychallenge.repositories;

import com.spring.picpaychallenge.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "transactions")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
