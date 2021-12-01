package com.example.myapplication;

import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationUnitTesting {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private static MainActivity mainActivity;
    private static String email;
    private static String contactNo;
    private static String date;
    private static String emailx;

    @BeforeClass
    public static void initMain()
    {
        mainActivity = new MainActivity();
    }

    @Before
    public void setup(){
        emailx = "hello@g";
        contactNo = "0718985422";
        date = "21/2/2022";


    }

    @Test
    public void email_validation_test() {
        boolean a = mainActivity.validateEmail(emailx);
        assertEquals(false, a);
    }

    @Test
    public void contact_validation_test() {
        boolean a = mainActivity.validateDeadline(date);
        assertEquals(true, a);
    }

    @Test
    public void deadline_validation_test() {
        boolean a = mainActivity.validatePhoneNumber(contactNo);
        assertEquals(true, a);
    }



    @After
    public void clear() {
        emailx = null;
    }

}
