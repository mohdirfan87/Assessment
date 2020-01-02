package com.example.assignment.ui.facts.viewmodels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.assignment.R;
import com.example.assignment.models.Facts;
import com.example.assignment.networking.NetworkUtil;
import com.example.assignment.networking.Repository;

public class FactsViewModel extends ViewModel {
    private MutableLiveData<Boolean> isRefreshed = new MutableLiveData<>();
    private Repository factsRepository;
    private Context context;

    public FactsViewModel(Context context) {
        this.context = context;
        factsRepository = Repository.getInstance();
        refreshFacts();
    }

    LiveData<Facts> factsLiveData = Transformations.switchMap(isRefreshed, refresh ->
            factsRepository.getFacts()
    );

    public void refreshFacts() {
        if (NetworkUtil.isNetworkAvailable(context))
            isRefreshed.postValue(true);
        else
            Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }


    public LiveData<Facts> getFacts() {
        return factsLiveData;
    }

}
