package com.quicktutorialz.learntesting.DemoTest.controllers;

import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.services.UserService;

import com.quicktutorialz.learntesting.DemoTest.utilities.JsonResponseBody;
import com.quicktutorialz.learntesting.DemoTest.utilities.UserNotPresentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RestControllerTest {

    @Mock
    BindingResult bindingResult;

    @Mock
    UserService userService;

    @InjectMocks
    RestController restController;


    @Test
    public void getAllUsers() {

        List<User> expectedListOfUsers = new ArrayList<User>();
        expectedListOfUsers.add(new User("MKTSN85G5643H", "Mike Johnson", 67, null));
        expectedListOfUsers.add(new User("NNTMBDJ384JDG", "Anne Timberland", 32, null));
        expectedListOfUsers.add(new User("RBRTMLLR234FH", "Robert Miller", 45, null));

        when(userService.getUsers()).thenReturn(expectedListOfUsers);

        ResponseEntity<JsonResponseBody> httpResponse = restController.getAllUsers();
        List<User> actualListOfUsers = (List<User>) httpResponse.getBody().getResponse();

        assertEquals(expectedListOfUsers, actualListOfUsers);

    }

    @Test
    public void getAUser() throws UserNotPresentException {

        User expectedUser = new User("MKTSN85G5643H", "Mike Johnson", 67, null);
        when(userService.getAUser("MKTSN85G5643H")).thenReturn(Optional.of(expectedUser));

        ResponseEntity<JsonResponseBody> httpResponse = restController.getUser("MKTSN85G5643H");
        User actualUser = (User) httpResponse.getBody().getResponse();

        assertEquals(expectedUser, actualUser);


    }

    @Test
    public void saveUser() {

        User userToBeSaved = new User("MKTSN85G5643H", "Mike Johnson", 67, null);

        when(userService.saveUser(any(User.class))).thenReturn(userToBeSaved);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<JsonResponseBody> httpResponse = restController.saveUser(userToBeSaved, bindingResult);
        User savedUser = (User) httpResponse.getBody().getResponse();

        assertEquals(userToBeSaved, savedUser);

    }

}