package edu.sjsu.cmpe275.repository;
import edu.sjsu.cmpe275.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ryanf on 12/5/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findByid(Integer id);
    List<User> findByrole(String role);
}