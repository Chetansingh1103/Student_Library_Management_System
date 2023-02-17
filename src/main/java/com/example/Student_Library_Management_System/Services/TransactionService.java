package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.DTOs.ReturnBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transaction;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{
        int bookId = issueBookRequestDto.getBookId();
        int cardId = issueBookRequestDto.getCardId();

        //get the book entity and card entity ??? why do we need this
        // we are doing this because we want to set the transaction attributes....
        Book book = bookRepository.findById(bookId).get();
        Card card = cardRepository.findById(cardId).get();

        // final goal is to make a transaction entity, set its attributes and save it

        Transaction transaction = new Transaction();
        // setting the attributes
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        //attribute left is success/failure
        // check for validations
        if(book == null || book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available");
        }

        if(card == null || card.getCardStatus() != CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw  new Exception("Card is not valid");
        }

        // we have reached a success case

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        //set attributes of book

        book.setIssued(true);
        book.getTransactionList().add(transaction);
        book.setCard(card);

        // we need to make changes in the card
        // book and the card
        card.getBookIssued().add(book);

        //card and the transaction : bidirectional (parent class)
        card.getTransactionList().add(transaction);

        // save the parent
        cardRepository.save(card);

        return "Book has been Issued and your issue id is";
    }

    public String returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception {

        int transId = transactionRepository.getTransactionIdFromBookIdAndCardId(returnBookRequestDto.getBookId(), returnBookRequestDto.getCardId());

        Book book = bookRepository.findById(returnBookRequestDto.getBookId()).get();
        Card card = cardRepository.findById(returnBookRequestDto.getCardId()).get();

        Transaction transaction = transactionRepository.findById(transId).get();


        book.setIssued(false);
        book.setCard(null);

        card.getBookIssued().remove(book);

        transactionRepository.delete(transaction);

        return "book has been returned";
    }

    public String getTransaction(int bookId,int cardId){
        List<Transaction> transactionList = transactionRepository.getTransactionListForBookIdAndCardId(bookId,cardId);

        return transactionList.get(0).getTransactionId();
    }
}
