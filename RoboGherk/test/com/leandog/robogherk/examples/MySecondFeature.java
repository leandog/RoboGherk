package com.leandog.robogherk.examples;

import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.robogherk.Feature;

public class MySecondFeature extends Feature {

    public MySecondFeature() {
        super(HelloRoboGherk.class);
    }
    
    public void test_A_second_feature_does_not_hang_or_blow_up() {
       Given("witches float"); 
       Then("witches burn");
    }

}
