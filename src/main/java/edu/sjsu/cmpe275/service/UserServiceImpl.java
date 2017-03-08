package edu.sjsu.cmpe275.service;

/**
 * Created by ryanf on 12/5/2016.
 */
import edu.sjsu.cmpe275.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.repository.UserRepository;


@Service(value="userService")
public class UserServiceImpl implements UserService {


    private UserRepository userDAO;

    @Autowired
    public void BookRepository(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getById(Integer id) {
        return userDAO.findOne(id);
    }
    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return userDAO.save(user);
    }
    @Override
    public void deleteUser(Integer id) {
        userDAO.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    @Override
    public User findUserById(Integer id) {
        return userDAO.findByid(id);
    }
}
