package edu.sjsu.cmpe275.controller;

import edu.sjsu.cmpe275.model.*;
import edu.sjsu.cmpe275.repository.*;
import edu.sjsu.cmpe275.scheduled.daysFunction;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import edu.sjsu.cmpe275.common.HtmlPage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import sun.util.resources.cldr.es.TimeZoneNames_es_AR;

import javax.validation.Valid;
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

/**
 * Created by ryanf on 12/5/2016.
 */


@Controller
class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TrascationRepository trascationRepository;
    @Autowired
    private ActionRepository actionDAO;
    @Autowired
    private CallnumberRepository callnumberDAO;
	@Autowired
    private FeeRepository feeRepository;
    @Autowired
    private WaitlistRepository wlRepository;

    private ActiveCode activeCode;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest req) {
        if (req.getSession().getAttribute("uid")!=null)
            return "redirect:/loginsuccess";
        else
            return "redirect:/logout";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,HttpServletRequest req) {
        if(req.getSession().getAttribute("uid")!=null){
            return "redirect:/loginsuccess";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user, HttpServletRequest req, Model model) {

        HttpSession session = req.getSession(true);
        if (userRepository.findByEmail(user.getEmail())!=null&& userRepository.findByEmail(user.getEmail()).getPassword().equals(user.getPassword())) {
            int user_id= userRepository.findByEmail(user.getEmail()).getUid();
            session.setAttribute("uid",user_id);
            if (userRepository.findByEmail(user.getEmail()).getActive()) {
                // if we get a user
                if("patron".equals(userRepository.findOne(user_id).getRole()))
                {
                    session.setAttribute("edit",false);
                    session.setAttribute("borrow",true);
                }else{
                    // if we are lib
                    session.setAttribute("edit",true);
                    session.setAttribute("borrow",false);
                }
                return "redirect:/loginsuccess";
            } else {
                return "redirect:/active";
            }
        } else {
            model.addAttribute("err", "<div class=\"alert alert-danger\">\n" +
                    "        <strong>Email/password not correct</strong>\n" +
                    "    </div>");
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@ModelAttribute("user") User user, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        if(session!=null)
            session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") User user, Model model) {


        if (user.getEmail().contains("@sjsu.edu")) {
            user.setRole("librarian");
        } else {
            user.setRole("patron");
        }

        System.out.println("User reg:" + user.getEmail() + " P: " + user.getPassword() + " R: " + user.getRole());
        System.out.println("f:" + user.getFirstName() + " l: " + user.getLastName());
        model.addAttribute(user);


        String code = activeCode.getToken(5);
        user.setCode(code);

        user.setActive(false);
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("user", user);
            model.addAttribute("err", "<div class=\"alert alert-danger\">\n" +
                    "        <strong>Email Address Exist</strong>\n" +
                    "    </div>");
            return "signup";
        } else {
            if (userRepository.findByid(user.getId()) != null) {
                model.addAttribute("user", user);
                model.addAttribute("err", "<div class=\"alert alert-danger\">\n" +
                        "        <strong>University Existed</strong>\n" +
                        "    </div>");
                return "signup";
            }
        }

        userRepository.save(user);

        //send email
        String mail = user.getEmail();
        User getUser = userRepository.findByEmail(user.getEmail());
        String page = HtmlPage.activeConfirmation(getUser);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(mail);
        email.setSubject("Active Account");
        email.setText(page);
        // sends the e-mail
        mailSender.send(email);
        String message = "You have successfully sign uo new account. Go to your email to active it.";
        model.addAttribute("mail", mail);
        model.addAttribute("message", message);
        return "regsuccess";
    }

    @RequestMapping(value="/active", method = RequestMethod.GET)
    public String getConfirm (@RequestParam(value = "id", required = false) Integer uid,
                              @RequestParam(value = "code",required = false) String code,
                              HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        Integer CurrentUid;
        if(session!=null){
            CurrentUid= (Integer)session.getAttribute("uid");
        }else {
            return "redirect:/login";
        }

        if(uid!=null && code!=null) {
            User user = userRepository.findOne(uid);

            //active suc
            if(code.equals(user.getCode()) && !user.getActive()) {
                user.setActive(true);
                userRepository.save(user);
                if(session.getAttribute("uid")!=null) {
                    return "activeSuc";
                }else{
                    return "redirect:/login";
                }
            }else if (user.getCode()!=code && !user.getActive()){
                if(CurrentUid!=null) {
                    model.addAttribute("code", new String());
                    model.addAttribute("uid", CurrentUid);
                    return "active";
                }else {
                    return "redirect:/login";
                }
            }else if(user.getCode()==code && user.getActive()){
                if(CurrentUid!=null) {
                    return "redirect:/loginsuccess";
                }else {
                    return "redirect:/login";
                }
            }else{
                if(CurrentUid!=null) {
                    model.addAttribute("code", new String());
                    model.addAttribute("uid", CurrentUid);
                    model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                            "        <strong>Active Code Not correct</strong>\n" +
                            "    </div>");
                    return "redirect:/active";
                }else {
                    return "redirect:/login";
                }
            }

        }
        else{
            if(CurrentUid==null){
                return "redirect:/login";
            }else{
                model.addAttribute("code", new String());
                model.addAttribute("uid",session.getAttribute("uid"));
                return "active";
            }

        }
    }

    @RequestMapping(value="/active", method = RequestMethod.POST)
    public String getActive (@ModelAttribute("code") String code,HttpServletRequest req, Model model) {
        System.out.println("start active post");
        HttpSession session = req.getSession(false);

        if(session==null)
        {
            System.out.println("not login");
            return "redirect:/login";

        }else{
            Integer uid=(Integer) session.getAttribute("uid");
            User user = userRepository.findOne(uid);

            if (user ==null){
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "        <strong>user</strong>\n" +
                        "    </div>");
                System.out.println("return fail");
                return "active";
            }

            System.out.println("get id and acive");
            if(code !=null && code.length()==5 ){
                if(code.equals(user.getCode())){
                    System.out.println("code == code");
                    user.setActive(true);
                    userRepository.save(user);
                    model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                            "        <strong>Good</strong>\n" +
                            "    </div>");

                    if("patron".equals(user.getRole()))
                    {
                        session.setAttribute("edit",false);
                        session.setAttribute("borrow",true);
                    }else{
                        // if we are lib
                        session.setAttribute("edit",true);
                        session.setAttribute("borrow",false);
                    }

                    return "activeSuc";
                    //return "loginsuccess";
                }else{
                    System.out.println("code !=code");
                    model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                            "        <strong>Active code not correct</strong>\n" +
                            "    </div>");
                    System.out.println("return fail");
                    return "active";
                }
            }else{
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "        <strong>active code not correct</strong>\n" +
                        "    </div>");
                System.out.println("return fail");
                return "active";
            }

        }
    }
    @RequestMapping(value="/loginsuccess", method = RequestMethod.GET)
    public String userPage (HttpServletRequest req, Model model) {
        Integer uid = null;
        if( req.getSession(false)!=null && req.getSession(false).getAttribute("uid")!=null)
             uid= (Integer) req.getSession(false).getAttribute("uid");

        if(uid==null)
        {
            model.addAttribute("err", "<div class=\"alert alert-danger\">\n" +
                    "        <strong>You need login first</strong>\n" +
                    "    </div>");
            return "redirect:/login";
        }
        else {
            User user = userRepository.findOne(uid);
            Fee myfee = feeRepository.findByuid(uid);
            int cuttentFee=0;
            if (myfee!=null){
                 cuttentFee = myfee.getFee();
            }

            model.addAttribute("email", user.getEmail());
            model.addAttribute("fee", cuttentFee);
            return "loginsuccess";
        }
    }

    @RequestMapping(value="/book/{bid}", method = RequestMethod.GET)
    public String bookpage (@PathVariable("bid") Integer bid, Model model) {
        Book book = bookRepository.findOne(bid);
        model.addAttribute("book",book);
        List<Callnumber> cn = callnumberDAO.findBybid(bid);
        if(!cn.contains(true)){
            model.addAttribute("avaliable",false);
        }else{
            model.addAttribute("avaliable",true);
        }

        return "book";
    }

    @RequestMapping(value="/book/{bid}/update", method = RequestMethod.GET)
    public String bookUpdatePage (@PathVariable("bid") Integer bid, Model model) {
        Book book = bookRepository.findOne(bid);
        model.addAttribute("book",book);
        model.addAttribute("link", "/book/"+bid+"/update");
        model.addAttribute("edit", true);
        return "bookcreate";

    }

    @RequestMapping(value="/book/{bid}/update", method = RequestMethod.POST)
    public String bookUpdate (@Valid @ModelAttribute("book")  Book book, BindingResult result, @PathVariable("bid") Integer bid,HttpServletRequest req) {
        //save update
        bookRepository.save(book);

        //reset copies
        List<Callnumber> cns=new ArrayList<Callnumber>();
        cns=callnumberDAO.findBybid(book.getBid());
        for(int i = 0;i<cns.size();i++){
            if(cns.size()==0){
                break;
            }else {
                callnumberDAO.delete(cns.get(i).getCnid());
            }
        }
        for(int i=1;i<=book.getNumber();i++){
            Callnumber cl = new Callnumber();
            cl.setBid(book.getBid());
            cl.setAvaliable("true");
            cl.setCallnumber(book.getCallNumber());
            cl.setCopyid(i);
            callnumberDAO.save(cl);
        }

        //set update record
        HttpSession session = req.getSession(false);
        System.out.println("before UPDATING,GOT UID:"+session.getAttribute("uid"));
        Action action = new Action();
        action.setBid(book.getBid());
        action.setUid((Integer)session.getAttribute("uid"));
        action.setAction("update");
        actionDAO.save(action);
        System.out.println("AFTER UPDATING");

        return "redirect:/book/"+bid;
    }

    @RequestMapping(value="/book/{bid}/delete", method = RequestMethod.GET)
    public String bookUpdate (@PathVariable("bid") Integer bid) {

        Book thisBook = bookRepository.getOne(bid);
        List<Trascation> listBook = trascationRepository.findByBookidAndRTime(bid,null);
        if(thisBook!=null && listBook.size()==0){
            bookRepository.delete(bid);
            List<Waitlist> list = wlRepository.findBybid(bid);
            for(Iterator<Waitlist> i = list.iterator();i.hasNext();)
            {
                Waitlist thisOne = i.next();
                wlRepository.delete(thisOne);
            }
        }

        return "redirect:/books/";
    }


    @RequestMapping(value="/book/{bid}/borrow", method = RequestMethod.GET)
    public String borrowBook (@PathVariable("bid") Integer bid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            List<Integer> carts;
            if(session.getAttribute("carts")==null){
                carts = new ArrayList<>();
                carts.add(bid);
            }else{
                carts= ((List<Integer>)session.getAttribute("carts"));
                if(!carts.contains(bid)){
                    carts.add(bid);
                }
            }
            session.setAttribute("carts",carts);
        }
        return "redirect:/books/carts";
    }

    @RequestMapping(value="/carts/submit", method = RequestMethod.GET)
    public String submitCarts (HttpServletRequest req,Model model) {
        //* 检查是否持有超过10本；添加设置借书的copy号
        HttpSession session = req.getSession(false);
        int userid = (Integer) session.getAttribute("uid");
        List<Trascation> tr = new ArrayList<Trascation>();
        tr = trascationRepository.findByuid(userid);
        //* 去除还过的书纪录
        for(int i = 0; i<tr.size();i++){
            if(tr.get(i).getrTime()!=null){
                tr.remove(i);
            };
        }

        if(session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            if(session.getAttribute("carts")==null || ((List<Integer>)session.getAttribute("carts")).isEmpty()){
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "        <strong>No book in carts</strong>\n" +
                        "    </div>");
                return "redirect:/books/carts";
            }else{
                if( ((List<Integer>)session.getAttribute("carts")).size()>5){
                    model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                            "        <strong>cannot borrow more than 5 books each time</strong>\n" +
                            "    </div>");
                    return "redirect:/books/carts";
                } else if(tr.size()>10){ // judge whether the user is keeping books to the up limit, 测试会改回来
                    System.out.println("borrow failed for tr exceed");
                    model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                            "        <strong>You are keeping more than 10 books!</strong>\n" +
                            "    </div>");
                    return "redirect:/books/carts";
                }else{
                    List<Integer> books = (List<Integer>)session.getAttribute("carts");
                    int uid = (int)session.getAttribute("uid");
                    List<String> mails =new ArrayList<>();
                    for(Iterator<Integer> i = books.iterator(); i.hasNext(); ) {
                        int b = i.next();
                        Trascation t = new Trascation();
                        t.setUid(uid);
                        t.setBookid(b);
                        //*添加设置借书的copy号
                        List<Callnumber> cn = new ArrayList<Callnumber>();
                        cn = callnumberDAO.findBybid(b);
                        for(int j=0; j<cn.size();j++){
                            if(cn.get(j).getAvaliable().equals("false")){
                                cn.remove(j);
                            }
                        }
                        if(cn.size()==0){
                            System.out.println("No copies avaliable");
                            model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                                    "        <strong>No copies of the book avaliable,please add waitlist</strong>\n" +
                                    "    </div>");
                            return "redirect:/books/carts";
                        }else{
                            Callnumber cs = cn.get(0);
                            t.setCopyid(cs.getCopyid());
                            cs.setAvaliable("false");
                            callnumberDAO.save(cs);
                        }

                        Date date = new Date();
                        Timestamp timeStamp = new Timestamp(date.getTime());
                        t.setbTime(timeStamp);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(timeStamp);
                        calendar.add(Calendar.DATE, 30);

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = df.format(calendar.getTime());
                        Timestamp eTime = Timestamp.valueOf(time);
                        t.seteTime(eTime);
                        trascationRepository.save(t);
                        mails.add("<p>Title: " + bookRepository.findOne(b).getTitle() + "    BorrowTIme: " + timeStamp +"    ExpeirTime: " + eTime +" </p>");
                    }
                    //send mail
                    String mail;
                    if(userRepository.findOne(uid)!=null){
                        mail= userRepository.findOne(uid).getEmail();
                    }else{
                        return "redirect:/logout";
                    }

                    String page = HtmlPage.transactionConfirmation(mails);
                    SimpleMailMessage email = new SimpleMailMessage();
                    email.setTo(mail);
                    email.setSubject("Transcation");
                    email.setText(page);
                    // sends the e-mail
                    mailSender.send(email);

                    List<Integer> emptyCarts = new ArrayList<>();
                    session.setAttribute("carts", emptyCarts);
                    model.addAttribute("checkout",page);
                    return "checkout";

                }
            }
        }
    }

    @RequestMapping(value="/books/carts", method = RequestMethod.GET)
    public String bookCarts ( Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }
        List<Integer> bookIDs= (List<Integer>)session.getAttribute("carts");
        if(bookIDs==null|| bookIDs.isEmpty()){
            model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                    "        <strong>empty</strong>\n" +
                    "    </div>");
            return "carts";
        }else{
            if(bookIDs.size()>5){
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "        <strong>You should not submit more than 5 each time</strong>\n" +
                        "    </div>");
            }
                List<Book> books = new ArrayList<>();
                for(Iterator<Integer> i = bookIDs.iterator(); i.hasNext(); ) {
                    int b = i.next();
                    Book book = bookRepository.findOne(b);
                    books.add(book);
                }
                model.addAttribute("bbs",books);


            return "carts";

        }
    }

    @RequestMapping(value="/book/create", method = RequestMethod.GET)
    public String createBookPage (Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("link", "/book/create");
        return "bookcreate";
    }

    // all books pages
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public String bookListPage (Model model) {
        List<Book> bookList = (List<Book>)bookRepository.findAll();
        model.addAttribute("books",bookList);
        return "BookList";
    }

    // remove one book from borrow carts
    @RequestMapping(value="/books/carts/delete/{bid}", method = RequestMethod.GET)
    public String deleteFromCarts (Model model,@PathVariable("bid") Integer bid, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }
        List<Integer> bookIDs= (List<Integer>)session.getAttribute("carts");
        if(bookIDs==null|| bookIDs.isEmpty()){
            model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                    "        <strong>Empty</strong>\n" +
                    "    </div>");
            return "carts";
        }else{
            if(bookIDs.contains(bid)){
                bookIDs.remove(bid);
                session.setAttribute("carts",bookIDs);
            }
            return "redirect:/books/carts";
        }
    }

        // post create book
    @RequestMapping(value = "/book/create", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book,HttpServletRequest req, Model model) {
        System.out.println("bookb Create");
        //save book
        bookRepository.save(book);


        List<Book> created = bookRepository.findBytitle(book.getTitle());
        System.out.println("found Titled book:"+created.size());
        int bid =created.get(created.size()-1).getBid();
        HttpSession session = req.getSession(false);
        System.out.println("before UPDATING,GOT UID:"+session.getAttribute("uid"));

        //set copies in callnumber table
        int num = book.getNumber();
        for(int i=1;i<=num;i++){
            Callnumber cl = new Callnumber();
            cl.setBid(bid);
            cl.setAvaliable("true");
            cl.setCallnumber(book.getCallNumber());
            cl.setCopyid(i);
            callnumberDAO.save(cl);
        }
        //set record of creattion
        Action action = new Action();
        action.setBid(bid);
        action.setUid((Integer)session.getAttribute("uid"));
        action.setAction("create");
        actionDAO.save(action);
        System.out.println("AFTER Creating");
        System.out.println("bookb return");
        return  "redirect:/books";
    }

    // get borrow page
    @RequestMapping(value="/user/borrow", method = RequestMethod.GET)
    public String borrowedBook ( Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/";
        }
        else {
            List<Trascation> lt = trascationRepository.findByuid((int) session.getAttribute("uid"));
            List<String> bl = new ArrayList<>();
            if (lt != null && !lt.isEmpty()) {
                for (Iterator<Trascation> i = lt.iterator(); i.hasNext(); ) {
                    Trascation thisTrans = i.next();
                    Book newBook = bookRepository.findOne(thisTrans.getBookid());
                    String a = "<div class=\"panel-group\">" +
                            "<div =\"row\">" +
                            "<div class=\"col-md-12;panel-heading\">Title: " + newBook.getTitle() + "</div>" +
                            "</div>" +
                            "<div =\"row\">" +
                            "<div class=\"col-md-4\"><Strong>Borrow Time:</Strong> " + thisTrans.getbTime() + "</div>" +
                            "<div class=\"col-md-4\"><Strong>Expire Time:</Strong> " + thisTrans.geteTime() + "</div>" +
                            "</div>";
                    if (thisTrans.getrTime() == null) {
                        a += "<div =\"row\">" +
                                "<div class=\"col-md-1\">" +
                                "<a class=\"btn btn-success\" href=\"/user/borrow/return/" + thisTrans.getTid() + "\">Return</a>" +
                                "</div>" +
                                "<div class=\"col-md-1\">";
                        List<Waitlist> allWL = (List) wlRepository.findBybid(thisTrans.getBookid());

                        if (allWL==null || allWL.size()==0) {
                            a += "<a class=\"btn btn-success\" href=\"/user/borrow/renew/" + thisTrans.getTid() + "\">Renew</a>";
                        }
                        a += "</div>" +
                                "</div>";
                    } else {
                        a += "<div =\"row\">" +
                                "<div class=\"col-md-4\"><Strong>Return Time:</Strong> " + thisTrans.getrTime() + "</div>" +

                                "</div>";
                    }
                    a += "</div>";


                    bl.add(a);
                }
            }
            model.addAttribute("bbs", bl);
            return "bookBorrow";
        }
    }


    // add book into return carts
    @RequestMapping(value="/user/borrow/return/{tid}", method = RequestMethod.GET)
    public String returnBook (@PathVariable("tid") Integer tid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            List<Integer> carts;
            if(session.getAttribute("returnCarts")==null){
                carts = new ArrayList<>();
                carts.add(tid);
            }else{
                carts= ((List<Integer>)session.getAttribute("returnCarts"));
                if(!carts.contains(tid)){
                    carts.add(tid);
                }
            }
            session.setAttribute("returnCarts",carts);
        }
        return "redirect:/user/return";
    }

    // renew borrow
    @RequestMapping(value="/user/borrow/renew/{tid}", method = RequestMethod.GET)
    public String renewBook (@PathVariable("tid") Integer tid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            Trascation renewTras = trascationRepository.findOne(tid);

            if(daysFunction.getDays(renewTras.getbTime(),renewTras.geteTime())<90)
            {
                renewTras.seteTime(daysFunction.setDays(renewTras.geteTime(),30));
                trascationRepository.save(renewTras);
            }
            else{
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "  <strong>Warning!</strong> Cannot Renew same book twice.\n" +
                        "<a href=\"/user/borrow\" class=\"alert-link\">Click to return borrow page.</a>"+
                        "</div>");
                return "bookBorrow";
            }

        }
        return "redirect:/user/borrow";
    }

    // return page
    @RequestMapping(value="/user/return", method = RequestMethod.GET)
    public String returnBookpage ( Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            //List<Map<Integer,Book>>allReturns= new ArrayList<>();
            Map<Integer,Book> a = new HashMap<>();
            List<Integer> returnCarts =  ((List<Integer>)session.getAttribute("returnCarts"));
            if( returnCarts==null || returnCarts.isEmpty()){
                model.addAttribute("err","<div class=\"alert alert-danger\">\n" +
                        "        <strong>Return Carts Empty</strong>\n" +
                        "    </div>");
            }else{
                for(Iterator<Integer> i = ((List<Integer>)session.getAttribute("returnCarts")).iterator(); i.hasNext(); ) {
                    Integer thisTid = i.next();

                    a.put(thisTid,bookRepository.findOne(trascationRepository.findOne(thisTid).getBookid()));
                   // allReturns.add(a);
                }
                model.addAttribute("allReturns",a);
            }
        }
        return "returncarts";
    }

    // delete item from return carts
    @RequestMapping(value="/user/borrow/return/{tid}/delete", method = RequestMethod.GET)
    public String returnBookDelete (@PathVariable("tid") Integer tid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            List<Integer> allR = (List<Integer>)session.getAttribute("returnCarts");
            if (allR!=null && !allR.isEmpty() && allR.contains(tid)){
                allR.remove(tid);
            }
            session.setAttribute("returnCarts",allR);
        }
        return "redirect:/user/return";
    }


    // submit Return carts
    @RequestMapping(value="/carts/return/submit", method = RequestMethod.GET)
    public String returnBookSubmit ( Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            int currentFee = 0;
            List<Integer> allR = (List<Integer>)session.getAttribute("returnCarts");
            if (allR!=null && !allR.isEmpty()){
                Date date = new Date();
                Timestamp returnTime = new Timestamp(date.getTime());

                for(Iterator<Integer> i = allR.iterator(); i.hasNext(); ) {
                    Integer thisTid = i.next();

                    Trascation thisR = trascationRepository.findOne(thisTid);
                    Timestamp eDate = thisR.geteTime();

                    thisR.setrTime(returnTime);
                    trascationRepository.save(thisR);
                    int days = daysFunction.getDays(eDate,returnTime);


                    // apply fees to user
                    if ( days>0){
                        currentFee+=days;
                    }

                    //*还书后把callnumber的avaliable设为true
                    List<Callnumber> callnumbers = new ArrayList<Callnumber>();
                    int cn =0;
                    callnumbers = (List) callnumberDAO.findBycopyid(thisR.getCopyid());
                    for(int j=0;j<callnumbers.size();j++){
                        if(callnumbers.get(j).getBid()==thisR.getBookid()){
                            Callnumber callnumber = new Callnumber();
                            callnumber = callnumbers.get(j);
                             cn = callnumber.getCopyid();
                            callnumber.setAvaliable("true");
                            callnumberDAO.save(callnumber);
                            System.out.println("Set copy to avaliable after Returning");
                        }
                    }

                    Waitlist wl = wlRepository.findBybidOrderByWidAsc(thisR.getBookid());

                    wl.seteTime(daysFunction.setDays(returnTime,3));
                    wl.setCopyid(cn);
                    int nextUid = wl.getUid();
                    User nextUser = userRepository.findOne(nextUid);
                    String mail = nextUser.getEmail();
                    String page = "Dear user, \n"+
                            "Your waiting book is available:\n"+
                            "Title: " + bookRepository.findOne(thisR.getBookid()).getTitle();
                    SimpleMailMessage email = new SimpleMailMessage();
                    email.setTo(mail);
                    email.setSubject("Available notice");
                    email.setText(page);

                    // sends the e-mail
                    mailSender.send(email);
                }

                allR.clear();
                session.setAttribute("returnCarts",allR);
                int uid = (int)session.getAttribute("uid");
                Fee userFee = feeRepository.findByuid(uid);
                if(userFee!=null) {
                    currentFee+= userFee.getFee();
                }
                else{
                    userFee = new Fee();
                    userFee.setUid(uid);
                }
                userFee.setFee(currentFee);
                feeRepository.save(userFee);
            }

        }
        return "redirect:/user/return";
    }

    // add to wait list
    @RequestMapping(value="/waitlist/add/{bid}", method = RequestMethod.GET)
    public String waitBook (@PathVariable("bid") Integer bid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            System.out.println("start add into waitlist bid:"+bid);
            int uid = (int)session.getAttribute("uid");
            Waitlist newWL = new Waitlist();
            newWL.setUid(uid);
            newWL.setBid(bid);

            wlRepository.save(newWL);
        }
        return "redirect:/user/waitlist";
    }

    // remove  from wait list
    @RequestMapping(value="/waitlist/remove/{bid}", method = RequestMethod.GET)
    public String removeWaitBook (@PathVariable("bid") Integer bid, Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            Waitlist a= wlRepository.findByuidAndBid((int)session.getAttribute("uid"),bid);
            wlRepository.delete(a);
        }
        return "redirect:/user/waitlist";
    }

    //  wait list page
    @RequestMapping(value="/user/waitlist", method = RequestMethod.GET)
    public String waitlistpage ( Model model,  HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else{
            List<String> ListOfWaitList = new ArrayList<>();
            List<Integer> canBeBorrow = new ArrayList<>();
            int uid = (int)session.getAttribute("uid");
            List<Waitlist> wl = wlRepository.findByuid(uid);
            for(Iterator<Waitlist> i = wl.iterator(); i.hasNext(); ) {
                Waitlist cur = i.next();
                int thisBid= cur.getBid();
                Book newBook = bookRepository.findOne(thisBid);
                String a = "            <li class=\"list-group-item\">\n" +
                        "                <h5>\n" +
                        "                    <div class=\"row\">\n" +
                        "                        <div class=\"col-md-1\"><span class=\"label label-info label-as-badge\">Title </span></div>\n" +
                        "                        <div class=\"col-md-5\"><p style=\"display:inline\">"+newBook.getTitle()+"</p></div>\n" +
                        "                        <div class=\"col-md-4\"><span class=\"label label-info label-as-badge\">Year: </span> "+newBook.getYear()+"</div>\n" +
                        "                        <div class=\"col-md-2\"><a href=\"/book/"+thisBid+"}\" class=\"btn btn-info\" role=\"button\">Detail</a></div>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"row\">\n" +
                        "                        <div class=\"col-md-2\"><small>"+newBook.getAuthor()+"</small></div>\n" +
                        "                    </div>\n" +
                        "                </h5>\n" +
                        "                <div class=\"row\">\n" ;
                        Date date = new Date();
                        Timestamp returnTime = new Timestamp(date.getTime());
                        if (cur.geteTime()!=null ){
                            if (daysFunction.getDays(returnTime,cur.geteTime())>=0){
                                a+="                    <div class=\"col-md-2\"><a href=\"/waitlist/remove/" + thisBid + "}\" class=\"btn btn-danger\" role=\"button\">Remove</a></div>\n"+
                                                "                    <div class=\"col-md-2\"><a href=\"/waitlist/admit/" + thisBid + "}\" class=\"btn btn-success\" role=\"button\">Admit</a></div>\n";
                            }
                            else{
                                a+="                    <div class=\"col-md-2\"><p class=\"text-danger\">Expired</p></div>\n";
                            }
                        }
                        a+= "                </div>\n"+
                            "            </li>"+
                            "<hr>";
                ListOfWaitList.add(a);

            }
            model.addAttribute("bbs",ListOfWaitList);
        }
        return "waitlist";
    }

    @RequestMapping(value="/waitlist/admit/{bid}", method = RequestMethod.GET)
    public String waitListAdmitBook ( @PathVariable("bid") Integer bid, Model model,  HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else {
            Date date = new Date();
            Timestamp currentT = new Timestamp(date.getTime());
            Waitlist wl =  wlRepository.findByuidAndBid((int)session.getAttribute("uid"),bid);
            Trascation newTras = new Trascation();
            newTras.setUid((int)session.getAttribute("uid"));
            newTras.setBookid(bid);
            newTras.setbTime(currentT);
            newTras.seteTime(daysFunction.setDays(currentT,30));
            newTras.setCopyid(wl.getCopyid());
            wlRepository.delete(wl);
            trascationRepository.save(newTras);

        }
        return "redirect:/user/waitlist";
    }

    // post create book
    @RequestMapping(value = "/newtime", method = RequestMethod.POST)
    public String newtime (@ModelAttribute("time") Timestamp time, HttpServletRequest req, Model model) {
        System.out.println("newTime: " + time);
        return "redirect:/loginsuccess";
    }


/*    @RequestMapping(value="/autoISBN/{isbn}", method = RequestMethod.GET)
    public String CreatBookByISBN ( @PathVariable("isbn") Integer isbn, Model model,  HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("uid")==null){
            return "redirect:/login";
        }else {


            }
        }
        return "redirect:/user/waitlist";
    }*/
}