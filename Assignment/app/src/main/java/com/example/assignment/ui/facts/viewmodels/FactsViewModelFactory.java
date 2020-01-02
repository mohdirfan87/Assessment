package com.example.assignment.ui.facts.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FactsViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public FactsViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FactsViewModel.class)) {
            return (T) new FactsViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
