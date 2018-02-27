package com.quicktutorialz.learntesting.DemoTest.services;

import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.utilities.UserNotPresentException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getAUser(String id) throws UserNotPresentException;

    User saveUser(@Valid User user);

}
