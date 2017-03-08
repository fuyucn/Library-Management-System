package edu.sjsu.cmpe275.repository;

import edu.sjsu.cmpe275.model.Callnumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ZHANG JOHN on 2016/12/17.
 */
@Repository
public interface CallnumberRepository extends CrudRepository<Callnumber,Integer> {
    List<Callnumber> findBybid(int bid);
    Callnumber findBycopyid(int copyid);
}
