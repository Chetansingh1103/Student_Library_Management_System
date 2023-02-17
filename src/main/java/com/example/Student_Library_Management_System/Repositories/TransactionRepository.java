package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query(value = "select id from transaction t where t.book_id=:bookId and t.card_id=:cardId and is_issue_operation=true",nativeQuery = true)
    int getTransactionIdFromBookIdAndCardId(int bookId, int cardId);

    @Query(value = "select * from transaction t where t.book_id=:bookId and t.card_id=:cardId and is_issue_operation=true",nativeQuery = true)
    List<Transaction> getTransactionListForBookIdAndCardId(int bookId, int cardId);
}
