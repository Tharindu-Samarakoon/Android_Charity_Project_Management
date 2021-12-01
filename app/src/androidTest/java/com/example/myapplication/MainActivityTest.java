package com.example.myapplication;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public String stringToBetyped, stringToBetyped1, stringToBetyped2, stringToBetyped3;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Project name";
        stringToBetyped1 = "00/00/0000";
        stringToBetyped3 = "name@gmail.com";

    }


    @Test
    public void validname() {
        // Type text and then press the button.
        onView(withId(R.id.et_projectName))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.btn_update_project)).perform(click());

        // Check that the text was changed.
    }

    @Test
    public void validemail() {
        // Type text and then press the button.
        onView(withId(R.id.et_projectEmail))
                .perform(typeText(stringToBetyped3), closeSoftKeyboard());
        onView(withId(R.id.btn_update_project)).perform(click());

        // Check that the text was changed.
    }

    @Test
    public void validdate() {
        // Type text and then press the button.
        onView(withId(R.id.et_ProjectDonDeadline))
                .perform(typeText(stringToBetyped1), closeSoftKeyboard());
        onView(withId(R.id.btn_update_project)).perform(click());

        // Check that the text was changed.
    }


//    @Before
//    public void setUp() throws Exception {
//        activity = mMainActivity.getActivity();
//    }
//
//    @Test
//    public void testLaunch() {
//
//        View view = activity.findViewById(R.id.);
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
}