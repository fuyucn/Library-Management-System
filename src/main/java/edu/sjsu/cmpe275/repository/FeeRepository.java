package edu.sjsu.cmpe275.repository;

import edu.sjsu.cmpe275.model.Fee;

import org.springframework.data.repository.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by fu on 12/7/16.
 */

@NoRepositoryBean
interface MyBaseRepository <T, ID extends Serializable> extends Repository<T, ID>{
        T findOne(ID id);
        T save(T entity);
}

public interface FeeRepository extends MyBaseRepository <Fee, Integer>,  CrudRepository<Fee,Integer> {

    Fee findByuid(int uid);

}
