package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        Optional<Transaction> findLatestByAccountId(Long accountId);

        @Query("SELECT " +
                        "t.date AS date , t.balance AS balance , t.amount AS amount, t.type AS transactionType, " +
                        "a.number AS accountNumber, a.type AS accountType, " +
                        "a.initialAmount AS initialAmount, a.isActive AS isActive, " +
                        "a.clientId AS clientId  " +
                        "FROM Transaction t , Account a " +
                        "WHERE t.accountId = a.id AND a.clientId = :clientId AND t.date BETWEEN :init AND :end")
        List<Tuple> findAllByClientIdAndDate(
                        @Param("clientId") Long clientId,
                        @Param("init") Date init,
                        @Param("end") Date end);
}
