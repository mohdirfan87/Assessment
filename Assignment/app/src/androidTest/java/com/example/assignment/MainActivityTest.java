package com.example.assignment;

import android.view.View;
import android.widget.ImageButton;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityTest {

    MainActivity mainActivity;
    @Rule
    public ActivityTestRule<MainActivity>mActivityTestRule=new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        mainActivity=mActivityTestRule.getActivity();

    }

    @Test
    public void refreshButtonClick(){
        Espresso.onView(withId(R.id.refresh))
         .perform(click());
    }
    @After
    public void tearDown() throws Exception {
        mainActivity=null;
    }



    @Test
    public void checkTitle() {
        Espresso.onView(withId(R.id.toolbar_title)).check(matches(withText("Facts")));
    }
}