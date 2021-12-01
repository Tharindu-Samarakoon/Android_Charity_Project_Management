package com.example.myapplication;

import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private static MainActivity mainActivity;
    private static EditText email;
    private static EditText contactNo;
    private static EditText date;
    private static String emailx;

    @BeforeClass
    public static void initMain()
    {
        mainActivity = new MainActivity();
    }

    @Before
    public void setup(){
        emailx = "hello@g";


    }

    @Test
    public void email_validation_test() {
        boolean a = mainActivity.validateEmail(emailx);
        assertEquals(false, a);
    }

    @After
    public void clear() {
        emailx = null;
    }





}

