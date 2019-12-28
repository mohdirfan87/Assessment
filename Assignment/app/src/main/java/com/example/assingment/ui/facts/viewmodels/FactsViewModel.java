package com.example.assingment.ui.facts.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.assingment.models.Facts;
import com.example.assingment.networking.Repository;

public class FactsViewModel extends ViewModel {
    private MutableLiveData<Boolean> isRefreshed=new MutableLiveData<>();
    private Repository factsRepository;

    public FactsViewModel() {
        factsRepository = Repository.getInstance();
        refreshFacts();
    }

    LiveData<Facts> factsLiveData = Transformations.switchMap(isRefreshed, refresh ->
            factsRepository.getFacts()
    );

    public void refreshFacts() {
        isRefreshed.postValue(true);
    }


    public LiveData<Facts> getFacts() {
        return factsLiveData;
    }

}
