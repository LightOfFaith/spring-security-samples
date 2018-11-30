 package com.share.lifetime.security;

import java.util.Calendar;

public class Test {

    @org.junit.Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println();
    }

}
