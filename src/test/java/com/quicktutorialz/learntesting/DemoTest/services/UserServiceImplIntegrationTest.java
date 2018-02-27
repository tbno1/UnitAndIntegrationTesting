package com.quicktutorialz.learntesting.DemoTest.services;

import com.quicktutorialz.learntesting.DemoTest.DemoTestApplication;
import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@SpringBootTest(classes = DemoTestApplication.class)       //load configuration file
@RunWith(SpringRunner.class)
public class UserServiceImplIntegrationTest {

    @Autowired UserService userService;    //injected by Spring (Singleton)

    @Autowired UserDao userDao;            //injected by Spring (Singleton: so it's the same injected into userService)

    UserDao userDaox;                      //got from Service with Reflection


    @Before
    public void setUp() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException{
        userDao.deleteAll();
        userDao.save(new User("MKTSN85G5643H", "Mike Johnson", 67, null));
        userDao.save(new User("NNTMBDJ384JDG", "Anne Timberland", 32, null));
        userDao.save(new User("RBRTMLLR234FH", "Robert Miller", 45, null));
    }


    @Test
    public void verifySpringContext() {
        //let's test the reflection and the injection of Spring
        userDaox = (UserDao) ReflectionTestUtils.getField(userService, "userDao");

        assertNotNull(userDao);
        assertNotNull(userDaox);
        assertEquals(userDao, userDaox);
    }




    @Test
    public void getUsersTest(){
        //prepare expected: they must be the re
        List<User> expectedList = new ArrayList<User>();
        expectedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, null));
        expectedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, null));
        expectedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, null));

        //call method on test of system under test
        List<User> actualList = userService.getUsers();
        //assert
        assertEquals(actualList, expectedList);
    }


    @Test
    public void saveUserTest(){
        User savedUser = userService.saveUser(new User("FRCQRN75F563K","Francesca Quaranta", 42, null));
        assertEquals(savedUser, new User("FRCQRN75F563K","Francesca Quaranta", 42, null));
        assertThat(savedUser, equalTo(new User("FRCQRN75F563K","Francesca Quaranta", 42, null)));
    }




}