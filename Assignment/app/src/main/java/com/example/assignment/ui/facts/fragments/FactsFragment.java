package com.example.assignment.ui.facts.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.adapter.FactsAdapter;
import com.example.assignment.models.Rows;
import com.example.assignment.networking.NetworkUtil;
import com.example.assignment.res_idle.EspressoIdlingResource;
import com.example.assignment.ui.facts.viewmodels.FactsViewModel;
import com.example.assignment.ui.facts.viewmodels.FactsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FactsFragment extends Fragment {
    private List<Rows> facts = new ArrayList<>();
    private FactsAdapter factsAdapter;
    private RecyclerView factRecyclerView;
    private FactsViewModel factsViewModel;
    private FragmentCallback callback;
    private SwipeRefreshLayout refreshView;

    public static FactsFragment newInstance() {
        return new FactsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facts_fragment, container, false);
        factRecyclerView = view.findViewById(R.id.factsRecyclerView);
        refreshView = view.findViewById(R.id.refreshView);
        refreshView.setOnRefreshListener(() -> {
            refreshFacts();
        });
        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FactsViewModelFactory factory = new FactsViewModelFactory(FactsFragment.this.getContext());
        factsViewModel = ViewModelProviders.of(this, factory).get(FactsViewModel.class);

        if (NetworkUtil.isNetworkAvailable(getContext()))
            setRefreshLoader();
        EspressoIdlingResource.increment();
        factsViewModel.getFacts().observe(this, response -> {
            refreshView.setRefreshing(false);
            if (response != null) {
                List<Rows> rows = response.getRows();
                if (rows != null && rows.size() > 0) {
                    facts.addAll(rows);
                    factsAdapter.notifyDataSetChanged();
                }
            }
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setRefreshLoader() {
        if (!refreshView.isRefreshing()) refreshView.setRefreshing(true);
        else refreshView.setRefreshing(false);

    }

    public void refreshFacts() {
        if (facts != null && facts.size() > 0) {
            facts.clear();
            factsAdapter.notifyDataSetChanged();
        }
        setRefreshLoader();
        factsViewModel.refreshFacts();
    }

    private void setupRecyclerView() {
        if (factsAdapter == null) {
            factsAdapter = new FactsAdapter(getContext(), facts, rows -> {
                if (callback != null) {
                    callback.titleUpdate(rows.getTitle());
                }
            });
            factRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            factRecyclerView.setAdapter(factsAdapter);
            factRecyclerView.setItemAnimator(new DefaultItemAnimator());
            factRecyclerView.setNestedScrollingEnabled(true);
        } else {
            factsAdapter.notifyDataSetChanged();
        }


    }

    public int getRecyclerItemCount() {
        return factsAdapter.getItemCount();
    }


    public interface FragmentCallback {
        void titleUpdate(String title);
    }
}
