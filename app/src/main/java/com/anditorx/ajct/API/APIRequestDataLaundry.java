package com.anditorx.ajct.API;

import com.anditorx.ajct.Model.ResponseLaundryModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestDataLaundry {
    @GET("retrieve.php")
    Call<ResponseLaundryModel> ardRetrieveDataLaundry();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseLaundryModel> ardCreateDataLaundry(
            @Field("name") String name,
            @Field("address") String address,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseLaundryModel> ardDeleteDataLaundry(
            @Field("id") int id

    );


}
