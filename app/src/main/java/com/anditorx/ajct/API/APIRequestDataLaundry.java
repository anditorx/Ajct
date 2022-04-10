package com.anditorx.ajct.API;

import com.anditorx.ajct.Model.ResponseLaundryModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestDataLaundry {
    @GET("retrieve.php")
    Call<ResponseLaundryModel> ardRetrieveDataLaundry();
}
