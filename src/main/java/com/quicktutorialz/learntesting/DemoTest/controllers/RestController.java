package com.quicktutorialz.learntesting.DemoTest.controllers;

import com.quicktutorialz.learntesting.DemoTest.entities.User;
import com.quicktutorialz.learntesting.DemoTest.services.UserService;
import com.quicktutorialz.learntesting.DemoTest.utilities.JsonResponseBody;
import com.quicktutorialz.learntesting.DemoTest.utilities.UserNotPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    UserService userService;


    @RequestMapping("/users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), userService.getUsers()));
    }


    @RequestMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable(value="id") String id){
        try{
            return ResponseEntity.ok(new JsonResponseBody( 200, userService.getAUser(id).get()));
        }catch(UserNotPresentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), e.toString()));
        }
    }


    @RequestMapping(value = "save/user", method = RequestMethod.POST)
    public ResponseEntity saveUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), result.toString()));
        }
        return ResponseEntity.ok(new JsonResponseBody(200, userService.saveUser(user)));
    }

}
