package com.quicktutorialz.learntesting.DemoTest.services;

import com.quicktutorialz.learntesting.DemoTest.daos.UserDao;
import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.utilities.StaticUtils;
import com.quicktutorialz.learntesting.DemoTest.utilities.UserNotPresentException;
import mockit.MockUp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/* we're mocking UserDao: no need to add dependencies in pom.xml: mockito is already provided with Spring Boot*/
/* here we're not mocking the static class. We're using it with no mock*/

@RunWith(MockitoJUnitRunner.class)      //UNIT TEST DON'T NEED THIS, ONLY MOCK
public class UserServiceImplTest {

    @Mock
    UserDao userDao;

    @InjectMocks                        // must be IMPLEMENTED CLASS, NOT INTERFACE
    UserServiceImpl userService;       //no need of constructor with this: userDao will be created and injected into userService

    @Test
    public void getUsersTest(){
        //prepare expected
        List<User> expectedList = new ArrayList<User>();
        expectedList.add(new User("MKTSN85G5643H", "Mike Johnson", 67, new Date("27/10/2004")));
        expectedList.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, new Date("28/09/2017")));
        expectedList.add(new User("RBRTMLLR234FH", "Robert Miller", 45, new Date("13/05/2009")));
        //define mocking rule
        when(userDao.findAll()).thenReturn(expectedList);  //mock rule on Dependency
        //call method on test of system under test
        List<User> actualList = userService.getUsers();
        //assert
        assertEquals(actualList, expectedList);
    }

    @Test //entangled
    public void getAUserCorrectlyTestEntangled() throws UserNotPresentException{
        //prepare expected
        User expectedUser = new User("RBRMLC34J673F", "Robert Malcom", 56, new Date("12/12/2012"));
        //mock rule
        when(userDao.findById("RBRMLC34J673F")).thenReturn(Optional.of(expectedUser));//new User(1, "Robert Malcom", 56, new Date("12/12/2012"))));
        //call method under test
        Optional<User> actualUser = userService.getAUser("RBRMLC34J673F");
         //because the mocked return is sent in output of the SUT, this two objects remain entangled, so if you modify one, the othe will be modified
        expectedUser.setName(expectedUser.getName() + " user2"); //even if in the original is + " user"
        //assert
        assertEquals(actualUser.get().getId(), expectedUser.getId());
        assertEquals(actualUser.get().getName(), expectedUser.getName());
        assertEquals(actualUser.get().getAge(), expectedUser.getAge());
        assertEquals(actualUser.get().getDateOfSubscription(), expectedUser.getDateOfSubscription());
        //it won't work if you don't override User.class equals method
        assertEquals(actualUser.get(), expectedUser);
    }


    @Test //not entangled
    public void getAUserCorrectlyTestNotEntangled() throws UserNotPresentException{
        //mock rule
        when(userDao.findById("RBRMLC34J673F")).thenReturn(Optional.of(new User("RBRMLC34J673F", "Robert Malcom", 56, new Date("12/12/2012"))));
        //call method under test
        Optional<User> actualUser = userService.getAUser("RBRMLC34J673F");
        //because the mocked return is sent in output of the SUT, this two objects remain entangled, so if you modify one, the othe will be modified
        User expectedUser = new User("RBRMLC34J673F", "Robert Malcom", 56, new Date("12/12/2012"));
        expectedUser.setName(expectedUser.getName() + " user2"); //even if in the original is + " user"
        //assert
        assertEquals(actualUser.get().getId(), expectedUser.getId());
        assertNotEquals(actualUser.get().getName(), expectedUser.getName()); //NOT
        assertEquals(actualUser.get().getAge(), expectedUser.getAge());
        assertEquals(actualUser.get().getDateOfSubscription(), expectedUser.getDateOfSubscription());
        //it won't work if you don't override User.class equals method
        assertNotEquals(actualUser.get(), expectedUser);                     //NOT

        expectedUser.setName("Robert Malcom user");

        //assert
        assertEquals(actualUser.get().getId(), expectedUser.getId());
        assertEquals(actualUser.get().getName(), expectedUser.getName());
        assertEquals(actualUser.get().getAge(), expectedUser.getAge());
        assertEquals(actualUser.get().getDateOfSubscription(), expectedUser.getDateOfSubscription());
        //it won't work if you don't override User.class equals method
        assertEquals(actualUser.get(), expectedUser);
    }


    @Test(expected = UserNotPresentException.class)
    public void getAUserWithExceptionTest() throws UserNotPresentException{
        when(userDao.findById("RBRMLC34J673F")).thenReturn(Optional.empty());
        //call method under test
        Optional<User> actualUser = userService.getAUser("RBRMLC34J673F");
    }




    /* UNIT TEST MOCKING A STATIC METHOD OF THE DEPENDENCY */
    @Test
    public void getAUserCorrectlyWithStaticMockTest() throws UserNotPresentException {

        //mock rule for Mockito
        when(userDao.findById("RBRMLCM45H763S")).thenReturn(Optional.of(new User("RBRMLCM45H763S", "Robert Malcom", 56, new Date("12/12/2012"))));

        //mock rule for static method with JMockit
        new MockUp<StaticUtils>(){
            @mockit.Mock
            public String addSuffixName(String name){    //EVEN IF IT'S STATIC, HERE I DON'T USE KEYWORK STATIC IN THE MOCK
                return "Robert Malcom user1234";
            }
        };

        //call method under test
        Optional<User> actualUser = userService.getAUser("RBRMLCM45H763S");
        //expectation
        User expectedUser = new User("RBRMLCM45H763S", "Robert Malcom user1234", 56, new Date("12/12/2012"));

        //assert
        assertEquals(actualUser.get().getId(), expectedUser.getId());
        assertEquals(actualUser.get().getName(), expectedUser.getName());
        assertEquals(actualUser.get().getAge(), expectedUser.getAge());
        assertEquals(actualUser.get().getDateOfSubscription(), expectedUser.getDateOfSubscription());
        //it won't work if you don't override User.class equals method
        assertEquals(actualUser.get(), expectedUser);
    }



    /* UNIT TEST MOCKING PRIVATE METHOD OF THE SYSTEM UNDER TEST */
    @Test
    public void callingPrivateInsideTest(){

        //without mocking private of sut
        String greets = userService.callingPrivateInside("Zucchi");
        assertEquals("hello Zucchi", greets);

        //mock rule for private method with JMockit
        new MockUp<UserServiceImpl>(){
            @mockit.Mock
            private String hello(String name){
                return "Hellooooo Zucchi";
            }
        };
        greets = userService.callingPrivateInside("Zucchi");
        assertEquals("Hellooooo Zucchi", greets);
    }







}