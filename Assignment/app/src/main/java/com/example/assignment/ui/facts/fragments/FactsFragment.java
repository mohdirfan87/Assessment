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

import java.util.ArrayList;
import java.util.List;

public class FactsFragment extends Fragment {
    private List<Rows> facts = new ArrayList<>();
    private FactsAdapter factsAdapter;
    private RecyclerView factRecyclerView;
    private ProgressBar progress;
    private FactsViewModel factsViewModel;
    private FragmentCallback callback;


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
        progress = view.findViewById(R.id.progress);

        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factsViewModel = ViewModelProviders.of(this).get(FactsViewModel.class);

        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        EspressoIdlingResource.increment();
        factsViewModel.getFacts().observe(this, response -> {
            if (response != null) {
                List<Rows> rows = response.getRows();
                if (rows != null && rows.size() > 0) {
                    facts.addAll(rows);
                    factsAdapter.notifyDataSetChanged();
                }
            }
            progress.setVisibility(View.GONE);
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }
        });
    }

    public void refreshFacts() {
        if (facts != null && facts.size() > 0)
            facts.clear();
        progress.setVisibility(View.VISIBLE);
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
