package com.quicktutorialz.learntesting.DemoTest.controllers;

import com.quicktutorialz.learntesting.DemoTest.DemoTestApplication;
import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.services.UserService;
import com.quicktutorialz.learntesting.DemoTest.services.UserServiceImpl;
import com.quicktutorialz.learntesting.DemoTest.utilities.JsonResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/* mocking a deep dependency with mockito without using Reflections thanks to @Autowired of Spring */

@SpringBootTest(classes = DemoTestApplication.class)  //load configuration file
@RunWith(SpringRunner.class)
public class RestControllerIntegrationTest2 {

    @Autowired   RestController restController;

    @InjectMocks @Autowired UserService userService;

    @Mock        UserDao userDao;


    /*
    * the system under test is restController
    * if we @Autowired it we get the one created by Spring
    *
    * if we @InjetcMocks on UserServiceImpl without @Autowired it we get a new instance of UserServiceImpl
    * in which is injected the Mock versione of userDao.
    *
    * with @Autowired we can use UserService instead of UserServiceImpl necessary if it would have been @InjectMocks alone
    * */


    @Test
    public void getAllUserMockingDao(){
        //creating the expectation
        List<User> mockedList = new ArrayList<User>();
        mockedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, null));
        mockedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, null));
        mockedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, null));
        //mocking rule for the dao
        when(userDao.findAll()).thenReturn(mockedList);

        //calling the method under test
        ResponseEntity<JsonResponseBody> httpResponse = restController.getAllUsers();
        List<User> actualList = (List<User>) httpResponse.getBody().getResponse();

        //assert
        assertEquals(mockedList, actualList);



    }

}