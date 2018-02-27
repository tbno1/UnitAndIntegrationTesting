package com.quicktutorialz.learntesting.DemoTest.services;

import com.quicktutorialz.learntesting.DemoTest.utilities.StaticUtils;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/* test of ServiceUsesOnlyStatic.java: useless for the rest of the project */
public class ServiceUsesOnlyStaticTest {


    ServiceUsesOnlyStatic sut;

    @Before
    public void setUp(){
        sut = new ServiceUsesOnlyStaticImpl();
    }

    /* mock the static method of an existing object */
    @Test
    public void getNameWithSuffixWithMockTest() {

        new MockUp<StaticUtils>(){
          @Mock
          public String addSuffixName(String name){
              return "Ciao Mocked";
          }
        };

        String actual = sut.callStatic("DummyName");
        assertEquals("Ciao Mocked", actual);

    }


    /* stub the interface to prevent to mock static method */
    @Test
    public void getNameWithSuffixWithStubTest() {

        sut = new ServiceUsesOnlyStaticStub();

        String actual = sut.callStatic("DummyName");
        assertEquals("Ciao Stubbed", actual);

    }



}