package edu.sjsu.cmpe275.service;

import java.util.List;

import edu.sjsu.cmpe275.model.Book;


public interface BookService {

     Book getBookById(Integer id);

     Book addBookAndReturn(Book book);
     void addBook(Book book);

     void deleteBook(Book book);
     void deleteBookById(Integer id);

     void updateBook(Book book);
     Book updateBookAndReturn(Book book);


    //List<Book> findBookBySearchString(String searchstring);
     List<Book> searchbookbyCreate(Integer uid);

     List<Book> searchbookbyUpdate(Integer uid);

     List<Book> searchbookbyName(String bookname);

    boolean checkBookDeleteOk(Integer id);
}