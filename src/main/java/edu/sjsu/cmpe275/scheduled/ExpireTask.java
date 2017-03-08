package edu.sjsu.cmpe275.scheduled;
import edu.sjsu.cmpe275.common.HtmlPage;
import edu.sjsu.cmpe275.model.Trascation;
import edu.sjsu.cmpe275.model.Waitlist;
import edu.sjsu.cmpe275.repository.BookRepository;
import edu.sjsu.cmpe275.repository.TrascationRepository;
import edu.sjsu.cmpe275.repository.UserRepository;
import edu.sjsu.cmpe275.repository.WaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.util.*;
/**
 * Created by fu on 12/13/16.
 */
@Component
public class ExpireTask implements Task {

    @Autowired
    private TrascationRepository trascationRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WaitlistRepository waitlistRepository;
    @Autowired
    private JavaMailSender mailSender;

    //@Scheduled(cron="0/5 * *  * * ? ")
    @Scheduled(cron="0 0 12 * * ?")   //every 12pm
    @Override
    public void expireTask(){
        //System.out.println("5s task");
        List<Trascation> allTrans = (List)trascationRepository.findAll();
        //List<Map<Integer,Integer>> expireUid = new ArrayList<>();
        Map<Integer,List<Integer>>almostExpire = new HashMap<>();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
       for(Iterator<Trascation> i = allTrans.iterator(); i.hasNext(); ) {
           Trascation currentTrans = i.next();

           if (currentTrans.getrTime()==null && daysFunction.getDays(currentTime, currentTrans.geteTime())<=5){
               if (almostExpire.get(currentTrans.getUid()) != null){
                   List<Integer> tmp = almostExpire.get(currentTrans.getUid());
                   tmp.add(currentTrans.getBookid());
                   almostExpire.put(currentTrans.getUid(),tmp);
               }else {
                   List<Integer> tmp = new ArrayList<>();
                   tmp.add(currentTrans.getBookid());
                   almostExpire.put(currentTrans.getUid(),tmp);
               }
           }
       }
      /*  // just for print test
        for (Map.Entry entry : almostExpire.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }*/

        List<String> books = new ArrayList<>();
        for (Map.Entry entry : almostExpire.entrySet()) {
            int uid = (int)entry.getKey();
            List<Integer> allBookIDs = (List)entry.getValue();

            for(Iterator<Integer> i = allBookIDs.iterator(); i.hasNext(); ) {
                Integer bID = i.next();
                books.add("\n Title: " + bookRepository.findOne(bID).getTitle());
            }
            System.out.println(books);
            String page = HtmlPage.expirePage(books);
            String mail= userRepository.findOne(uid).getEmail();

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(mail);
            email.setSubject("Expire Alert");
            email.setText(page);

            // sends the e-mail
            mailSender.send(email);
            System.out.println("Sent expire alert success to: "+ mail);
        }

    }

}
