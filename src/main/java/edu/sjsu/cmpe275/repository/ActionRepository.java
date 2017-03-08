package edu.sjsu.cmpe275.repository;

import edu.sjsu.cmpe275.model.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZHANG JOHN on 2016/12/7.
 */
@Repository
public interface ActionRepository extends CrudRepository<Action,Integer>{
    List<Action> findByuid(Integer id);
}
