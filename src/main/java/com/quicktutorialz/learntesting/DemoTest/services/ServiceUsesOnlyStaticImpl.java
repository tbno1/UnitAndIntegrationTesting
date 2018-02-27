package com.quicktutorialz.learntesting.DemoTest.services;


import com.quicktutorialz.learntesting.DemoTest.utilities.StaticUtils;

public class ServiceUsesOnlyStaticImpl implements ServiceUsesOnlyStatic{

    @Override
    public String callStatic(String name){
        return StaticUtils.addSuffixName(name);   //calls a static method to be mocked in the test
    }
}
