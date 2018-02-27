package com.quicktutorialz.learntesting.DemoTest.controllers;

import com.quicktutorialz.learntesting.DemoTest.DemoTestApplication;
import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.services.UserService;
import com.quicktutorialz.learntesting.DemoTest.utilities.JsonResponseBody;
import com.quicktutorialz.learntesting.DemoTest.utilities.ReflectionUtils;
import mockit.MockUp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * using Jmockit almost everywhere we don't need @Mock and @InjectMocks because we're mocking a second level deep dependency:
 * controller: System Under Test  ->  service  -> Mocked Dependency(Dao)
 */

@SpringBootTest(classes = DemoTestApplication.class)  //load configuration file
@RunWith(SpringRunner.class)
public class RestControllerIntegrationTest {

    @Autowired RestController restController;

    @Test //with jmockit and reflections
    public void getAllUsersMockingDaoTest() throws Exception{

        //creating the expectation
        List<User> mockedList = new ArrayList<User>();
        mockedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, new Date("27/10/2004")));
        mockedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, new Date("28/09/2017")));
        mockedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, new Date("13/05/2009")));

        /* mock the Dao interface with Jmockit and creating the implemented instance */
        UserDao userDao = new MockUp<UserDao>(){
            @mockit.Mock
            public List<User> findAll(){
                return mockedList;
            }
        }.getMockInstance();

        //getting the @Autowired service from the controller using Reflection
        UserService userService = (UserService) ReflectionUtils.getPrivateField(restController, "userService");
        //setting the @Autowired UserDaoImpl mocked into the service
        ReflectionUtils.setPrivateField(userService, "userDao", userDao);

        //calling the method under test
        ResponseEntity<JsonResponseBody> httpResponse = restController.getAllUsers();
        List<User> actualList = (List<User>) httpResponse.getBody().getResponse();

        //assert
        assertEquals(mockedList, actualList);
    }


    @Test //with mockito and reflections
    public void getAllUsersMockingDaoTest2() throws Exception{

        UserDao userDao = mock(UserDao.class); //mockito: no need of @InjectMocks because we don't use Service

        //creating the expectation
        List<User> mockedList = new ArrayList<User>();
        mockedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, new Date("27/10/2004")));
        mockedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, new Date("28/09/2017")));
        mockedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, new Date("13/05/2009")));

        when(userDao.findAll()).thenReturn(mockedList);

        //getting the @Autowired service from the controller using Reflection
        UserService userService = (UserService) ReflectionUtils.getPrivateField(restController, "userService");
        //setting the @Autowired UserDaoImpl mocked into the service
        ReflectionUtils.setPrivateField(userService, "userDao", userDao);

        //calling the method under test
        ResponseEntity<JsonResponseBody> httpResponse = restController.getAllUsers();
        List<User> actualList = (List<User>) httpResponse.getBody().getResponse();

        //assert
        assertEquals(mockedList, actualList);
    }



    @Test
    public void getAllUsersTest() throws Exception{

        //creating the expectation
        List<User> expectedList = new ArrayList<User>();
        expectedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, new Date("27/10/2004")));
        expectedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, new Date("28/09/2017")));
        expectedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, new Date("13/05/2009")));

        //getting the @Autowired service from the controller using Reflection
        UserService userService = (UserService) ReflectionUtils.getPrivateField(restController, "userService");
        //getting the @Autowired dao from the service using Reflection
        UserDao userDao = (UserDao) ReflectionUtils.getPrivateField(userService, "userDao");

        userDao.deleteAll();
        userDao.save(new User("MKTSN85G5643H", "Mike Johnson", 67, null));
        userDao.save(new User("NNTMBDJ384JDG", "Anne Timberland", 32, null));
        userDao.save(new User("RBRTMLLR234FH", "Robert Miller", 45, null));

        //calling the method under test
        ResponseEntity<JsonResponseBody> httpResponse = restController.getAllUsers();
        List<User> actualList = (List<User>) httpResponse.getBody().getResponse();

        //assert
        assertEquals(expectedList, actualList);
    }



    @Test  //with JMockit
    public void getUserTest(){

        /* mock the BindingResult interface with Jmockit and creating the implemented instance */
        BindingResult mockedBindingResult = new MockUp<BindingResult>(){
            @mockit.Mock
            public boolean hasErrors(){
                return false;
            }
        }.getMockInstance();

        restController.saveUser(new User("NNTSN85G5643H", "Anne Johnson", 56, null), mockedBindingResult);

        ResponseEntity<JsonResponseBody> httpResponse = restController.getUser("NNTSN85G5643H");
        User actualUser = (User) httpResponse.getBody().getResponse();
        User expectedUser = new User("NNTSN85G5643H", "Anne Johnson user", 56, null);
        assertEquals(expectedUser, actualUser);

    }


    @Test  //with Jmockit
    public void saveUserTest(){

        /* mock the BindingResult interface with Jmockit and creating the implemented instance */
        BindingResult mockedBindingResult = new MockUp<BindingResult>(){
            @mockit.Mock
            public boolean hasErrors(){
                return false;
            }
        }.getMockInstance();

        ResponseEntity<JsonResponseBody> httpResponse = restController.saveUser(new User("NNTSN85G5643H", "Anne Johnson", 56, null), mockedBindingResult);
        assertNotNull(httpResponse.getBody().getResponse());
        assertTrue(httpResponse.getBody().getResponse() instanceof User);
    }





}