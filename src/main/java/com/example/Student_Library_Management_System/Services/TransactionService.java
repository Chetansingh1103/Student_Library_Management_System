package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.DTOs.ReturnBookRequestDto;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transaction;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto){
        Book book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        Card card = cardRepository.findById(issueBookRequestDto.getCardId()).get();

        Transaction transaction = new Transaction();

            transaction.setBook(book);
            transaction.setCard(card);
            transaction.setFine(0);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setIssueOperation(true);
            transactionRepository.save(transaction);

            book.getTransactionList().add(transaction);
            book.setIssued(true);
            book.setCard(card);
            bookRepository.save(book);

            card.getTransactionList().add(transaction);
            card.getBookIssued().add(book);
            cardRepository.save(card);

            return "Book has been Issued and your issue id is";
    }

    public String returnBook(ReturnBookRequestDto returnBookRequestDto){

        Transaction transaction = transactionRepository.findById(returnBookRequestDto.getTransId()).get();



        Book book = bookRepository.findById(transaction.getBook().getId()).get();
        Card card = cardRepository.findById(transaction.getCard().getId()).get();

        book.setIssued(false);
        book.setCard(null);

        transactionRepository.delete(transaction);

        card.getBookIssued().remove(book);



        return "book has been returned";
    }

}
