package edu.sjsu.cmpe275.repository;

import edu.sjsu.cmpe275.model.Waitlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
/**
 * Created by fu on 12/7/16.
 */
@Repository
public interface WaitlistRepository extends  CrudRepository<Waitlist, Integer>{


        List<Waitlist> findByuid (int uid);
        List<Waitlist> findBybid (int uid);
        Waitlist findByuidAndBid(int uid,int bid);
        Waitlist findBybidOrderByWidAsc(int bid);
}