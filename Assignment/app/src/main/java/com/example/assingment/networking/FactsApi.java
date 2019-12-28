package com.example.assingment.networking;

import com.example.assingment.models.Facts;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FactsApi {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<Facts> getFacts();
}
