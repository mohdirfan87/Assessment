package com.example.assignment.networking;

import androidx.lifecycle.MutableLiveData;

import com.example.assignment.models.Facts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository factsRepository;
    private FactsApi factsApi;
    public static Repository getInstance(){
        if (factsRepository == null){
            factsRepository = new Repository();
        }
        return factsRepository;
    }



    public Repository(){
        factsApi = RetrofitService.cteateService(FactsApi.class);
    }

    public MutableLiveData<Facts> getFacts(){
         final MutableLiveData<Facts> factsMutableLiveData = new MutableLiveData<>();
         factsApi.getFacts().enqueue(new Callback<Facts>() {
             @Override
             public void onResponse(Call<Facts> call, Response<Facts> response) {

                 if(response.isSuccessful()){
                     factsMutableLiveData.setValue(response.body());
                 }else {

                     factsMutableLiveData.setValue(null);
                 }
             }

             @Override
             public void onFailure(Call<Facts> call, Throwable t) {
                 factsMutableLiveData.setValue(null);
             }
         });
        return factsMutableLiveData;
    }
}
