package edu.sjsu.cmpe275.controller;

import edu.sjsu.cmpe275.model.Action;
import edu.sjsu.cmpe275.model.Book;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.repository.ActionRepository;
import edu.sjsu.cmpe275.repository.BookRepository;
import edu.sjsu.cmpe275.repository.TrascationRepository;
import edu.sjsu.cmpe275.repository.UserRepository;
import edu.sjsu.cmpe275.service.SearchImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ZHANG JOHN on 2016/12/7.
 */
@Controller
public class SearchController {
    //BookServiceImpl bookService=new BookServiceImpl();
    @Autowired
    public UserRepository userDAO;
    @Autowired
    public ActionRepository actionDAO;
    @Autowired
    public BookRepository bookDAO;
    @Autowired
    public TrascationRepository trascationDAO;
    @Autowired
    public SearchImpl sImpl;

    @RequestMapping(value="/search",method = RequestMethod.GET)
    public String LibrarianPage(HttpServletRequest req, ModelMap modelMap){
        HttpSession session = req.getSession(false);
        List<User> librarians= userDAO.findByrole("librarian");
        modelMap.addAttribute("Librarian",librarians);
        //List <edu.sjsu.cmpe275.model.Trascation> tr = new ArrayList<Trascation>();
        //tr = trascationDAO.findByuid();
        //WSystem.out.println(trascationDAO.findAll().);
        /*if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else if(userDAO.findOne((Integer)session.getAttribute("uid")).getRole().equals("User")){

            return "Search";
        }else if(userDAO.findOne((Integer)session.getAttribute("uid")).getRole().equals("Lirarian")){
            List<User> librarians= userDAO.findByrole("librarian");
            modelMap.addAttribute("Librarian",librarians);
        }*/
        return "Search";
    }
    @RequestMapping(value = "/search/forResults",method = RequestMethod.POST)
    public String Search(@RequestParam("booktitle")String booktitle,@RequestParam("Librarian") Integer id, @RequestParam("Action") String action, HttpServletRequest req, ModelMap modelMap){
        List<User> librarians= userDAO.findByrole("librarian");
        modelMap.addAttribute("Librarian",librarians);

        List<Book> booklist=new ArrayList<Book>();
        List<Action> a=actionDAO.findByuid(id);
        if(booktitle.length()>0){
            booklist = bookDAO.findBytitle(booktitle);
            modelMap.addAttribute("SearchType","Book Name");
        }else{
            if(action.equals("create")){
                System.out.println("CheckPoint 1");
                for(int h = 0;h<a.size()-1;h++){
                    System.out.println("CheckPoint 3");
                    System.out.println(a.get(h).getAction());
                    if(a.get(h).getAction().equals("create")){
                        System.out.println(" CREATE search");
                        Book c = bookDAO.findOne(a.get(h).getBid());
                        //System.out.println(a.get(h).getBid()+":"+c.getTitle());
                        booklist.add(c);
                    }
                }
                modelMap.addAttribute("SearchType","Create");
            }
            if(action.equals("update")){;
                System.out.println("CheckPoint 1");
                Set<Integer> s= new HashSet<Integer>();
                for(int h = 0;h<a.size()-1;h++){
                    System.out.println("CheckPoint 3");
                    System.out.println(a.get(h).getAction());
                    if(a.get(h).getAction().equals("update")&&!s.contains(a.get(h).getBid())){
                        System.out.println(" UPDATE search");
                        Book c = bookDAO.findOne(a.get(h).getBid());
                        booklist.add(c);
                        s.add(a.get(h).getBid());
                    }
                }
                modelMap.addAttribute("SearchType","Update");
            }
        }
        //booklist = sImpl.searchByCreate(id);

        modelMap.addAttribute("booklist",booklist);
        return "Search";
    }
}
