package edu.sjsu.cmpe275.repository;
import edu.sjsu.cmpe275.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by han on 12/5/2016.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, CrudRepository<Book, Integer>{
    List<Book> findBytitle(String title);
    //List<Book> findBooksBySearchString(String searchString);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.title = ?1, b.author = ?2, b.callNumber = ?3" +
            ", b.publisher = ?4, b.year = ?5, b.location = ?6, b.number = ?7, b.status = ?8" +
            ", b.keywords = ?9  WHERE b.bid = ?10")
    void updateBookByID(String title, String author,String call_number,
                         String publisher,int year,String location,
                         int number,String status, String keywords,
                         Integer bid);
}