package com.quicktutorialz.learntesting.DemoTest.services;

import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.utilities.StaticUtils;
import com.quicktutorialz.learntesting.DemoTest.utilities.UserNotPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUsers(){
        return userDao.findAll();
    }

    @Override
    public Optional<User> getAUser(String id)throws UserNotPresentException{
        Optional<User> userr = userDao.findById(id);
        if(userr.isPresent()){
            userr.get().setName(StaticUtils.addSuffixName(userr.get().getName()));
        } else {
            throw new UserNotPresentException("User not in database");
        }
        return userr;
    }

    @Override
    public User saveUser(@Valid User user){
        return userDao.save(user);
    }


    private String hello(String name){
        return "hello " + name;
    }


    public String callingPrivateInside(String name){
        return hello(name);
    }
}
