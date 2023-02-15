package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.BookRequestDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;
    public String addBook(BookRequestDto bookRequestDto){


        // we want to get the authorEntity
        int authorId = bookRequestDto.getAuthorId();

        // now we will be fetching the authorEntity

        Author author = authorRepository.findById(authorId).get();


        //converting it from dto to simple object
        Book book = new Book();
        book.setName(bookRequestDto.getName());
        book.setGenre(bookRequestDto.getGenre());
        book.setPages(bookRequestDto.getPages());
        book.setIssued(false);


        // how do exception handling

        // basic attributes are already set from postman

        // setting the foreign key attribute in the child class :

        book.setAuthor(author);

        // we need to update the list of book written in the parent class
        List<Book> bookList = author.getBooksWritten();
        bookList.add(book);
        author.setBooksWritten(bookList);

        // now the book is to be saved, but the author need to be updated

        authorRepository.save(author); // date was modified

        // save function works both  save function and as update function

        // bookRepo.save is not required : because it will be autoCalled by cascading effect

        return "book added successfully";

    }
}
