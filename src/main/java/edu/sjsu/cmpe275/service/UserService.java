package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.model.User;

public interface UserService {

    User getById(Integer id);

    User findByEmail(String email);

    User saveUser(User user);

    void deleteUser(Integer id);

    User findUserById(Integer id);
}
