package edu.sjsu.cmpe275.repository;
import edu.sjsu.cmpe275.model.Trascation;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by fu on 12/7/16.
 */
@Repository
public interface TrascationRepository extends CrudRepository<Trascation, Integer> {
     List<Trascation> findByuid(int id);
     List<Trascation> findByBookidAndRTime(int id, Timestamp returnT);
}
