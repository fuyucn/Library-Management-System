package edu.sjsu.cmpe275.common;
import edu.sjsu.cmpe275.model.*;
import edu.sjsu.cmpe275.repository.TrascationRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HtmlPage {
    TrascationRepository tr;
    public static String activeConfirmation(User user) {
        String page =""
                + "Your account has been signup successful.\n"
                + "You need to use link below to active your account, or user active code when you login\n"
                + "http://localhost:8080/active?id=" + user.getUid() + "&code="+user.getCode()+"\n"
                + "Active Code: "+ user.getCode() +"\n";
        return page;
    }

    public static String transactionConfirmation(List<String> books) {
        String page =""
                + "You have successful check out.\n"
                + "There is your borrow List: \n";

            for(Iterator<String> i = books.iterator(); i.hasNext(); ) {
                String a = i.next();
                 page+=a;
            }

        return page;
    }

    public static String returnConfirmation(List<String> books) {
        String page =""
                + "You have successful return book(s).\n"
                + "Book List: \n";

        for(int i=0;i<books.size();i++){
            page += books + "\n";
        }
        return page;
    }

    public static String expirePage(List<String> books) {
        String page =""
                + "Your book(s) almost expire:\n"
                + "Book List:";

        page += books + "\n";
        return page;
    }

}