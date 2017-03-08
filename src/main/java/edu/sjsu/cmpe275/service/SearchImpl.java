package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.model.Action;
import edu.sjsu.cmpe275.model.Book;
import edu.sjsu.cmpe275.repository.ActionRepository;
import edu.sjsu.cmpe275.repository.BookRepository;
import edu.sjsu.cmpe275.repository.TrascationRepository;
import edu.sjsu.cmpe275.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANG JOHN on 2016/12/11.
 */
@Service
public class SearchImpl {
    @Autowired
    public UserRepository userDAO;
    @Autowired
    public ActionRepository actionDAO;
    @Autowired
    public BookRepository bookDAO;
    @Autowired
    public TrascationRepository trascationDAO;

    public List<Book> searchByCreate(Integer uid){
        List<Book> books = new ArrayList<Book>();
        List<Action> actions= new ArrayList<Action>();
        actions = actionDAO.findByuid(uid);
        System.out.println("Action number:"+actions.size());
            for(int i=0;i<actions.size()-1;i++){
                books.add(bookDAO.findOne(actions.get(i).getBid()));
            }
        System.out.println("Service TESTED");
        if(books.size()==0){
            System.out.println("Service NOT USABLE");
        }
        return books;
    }
}
