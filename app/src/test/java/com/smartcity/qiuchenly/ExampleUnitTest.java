package com.smartcity.qiuchenly;

import com.smartcity.qiuchenly.Base.SQLCreator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String s = new SQLCreator.CreateTableBuilder("mTestTable")
                .setIfNotExists(true)
                .setField("userName", SQLCreator.TableFieldAttributeList.S_String)
                .setField("autoID", SQLCreator.TableFieldAttributeList.S_Int)
                .setField("key1", SQLCreator.TableFieldAttributeList.S_Int)
                .Build()
                .toString();
        System.out.println(s);
    }
}