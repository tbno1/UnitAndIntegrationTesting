package com.quicktutorialz.learntesting.DemoTest.utilities;

public class UserNotPresentException extends Exception {
    public UserNotPresentException(String errorMessage){
        super(errorMessage);
    }
}
