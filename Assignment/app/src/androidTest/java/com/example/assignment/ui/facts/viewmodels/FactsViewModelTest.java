package com.example.assignment.ui.facts.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ApplicationLifecycleMonitorRegistry;

import com.example.assignment.MainActivity;
import com.example.assignment.models.Facts;
import com.example.assignment.networking.NetworkUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class FactsViewModelTest {

    private FactsViewModel factsViewModel;
    private boolean isNetwork;

    @Rule
    public  ActivityTestRule<MainActivity>testRule=new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        factsViewModel = new FactsViewModel();
        isNetwork=NetworkUtil.isNetworkAvailable(testRule.getActivity());
    }

    @Test
    public void checkNetwork(){
        assertTrue(isNetwork);
    }
    @Test
    public void checkData() {
        assertTrue(isNetwork);
        LiveData<Facts> factsLiveData = factsViewModel.getFacts();
        assertNotNull(factsLiveData);
    }

    @After
    public void tearDown() throws Exception {
    }
}