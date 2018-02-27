package com.quicktutorialz.learntesting.DemoTest.services;

public class ServiceUsesOnlyStaticStub implements ServiceUsesOnlyStatic {

    @Override
    public String callStatic(String name){
        return "Ciao Stubbed";
    }
}
