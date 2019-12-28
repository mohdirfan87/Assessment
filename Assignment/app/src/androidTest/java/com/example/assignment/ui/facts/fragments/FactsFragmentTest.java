package com.example.assignment.ui.facts.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.assignment.MainActivity;
import com.example.assignment.R;
import com.example.assignment.networking.NetworkUtil;
import com.example.assignment.res_idle.EspressoIdlingResource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.assertTrue;


public class FactsFragmentTest {
    private MainActivity mActivity = null;
    private boolean isNetwork;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        isNetwork=NetworkUtil.isNetworkAvailable(mActivity);
    }

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }




    @Test
    public void testSampleRecyclerVisible() {
        assertTrue(isNetwork);
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCaseForRecyclerClick() {
        assertTrue(isNetwork);
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void testCaseForRecyclerScroll() {
        assertTrue(isNetwork);
        int items = mActivity.factsFragment.getRecyclerItemCount();
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivity.getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(items - 1));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}