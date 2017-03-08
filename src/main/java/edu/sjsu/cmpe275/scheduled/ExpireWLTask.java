package edu.sjsu.cmpe275.scheduled;
import edu.sjsu.cmpe275.common.HtmlPage;
import edu.sjsu.cmpe275.model.Trascation;
import edu.sjsu.cmpe275.model.User;
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
public class ExpireWLTask implements Task {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private WaitlistRepository waitlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    //@Scheduled(cron="0/5 * *  * * ? ")
    @Scheduled(cron="0 0 0 * * ?")   //every midnight
    @Override
    public void expireTask(){

        List<Waitlist> allWaitList = (List)waitlistRepository.findAll();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        for(Iterator<Waitlist> i = allWaitList.iterator(); i.hasNext(); ) {
            Waitlist currentWL = i.next();

            //if expired
            if (currentWL.geteTime()!=null && daysFunction.getDays( currentWL.geteTime(),currentTime)>0){
                System.out.println("Found expired waitlist");
                waitlistRepository.delete(currentWL.getWid());



                Waitlist newWaiter = waitlistRepository.findBybidOrderByWidAsc(currentWL.getBid());
                System.out.println(newWaiter.getWid());
                newWaiter.seteTime(daysFunction.setDays(currentTime,3));
                newWaiter.setCopyid(currentWL.getCopyid());
                waitlistRepository.save(newWaiter);

                //email next user
                int nextUid = currentWL.getUid();
                User nextUser = userRepository.findOne(nextUid);

                String mail = nextUser.getEmail();

                String page = "Dear user, \n"+
                        "Your waiting book is available:\n"+
                        "Title: " + bookRepository.findOne(currentWL.getBid()).getTitle();
                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(mail);
                email.setSubject("Available notice");
                email.setText(page);
                mailSender.send(email);
            }


        }
    }
}
