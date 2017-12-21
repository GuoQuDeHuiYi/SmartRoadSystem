package com.smartcity.qiuchenly;

import com.smartcity.qiuchenly.Base.SQLCreator;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        Calendar c = Calendar.getInstance();
        String year = ""+c.get(Calendar.YEAR);
        System.out.println(year);
    }
}