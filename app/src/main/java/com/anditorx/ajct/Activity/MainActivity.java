package com.anditorx.ajct.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anditorx.ajct.API.APIRequestDataLaundry;
import com.anditorx.ajct.API.RetroServer;
import com.anditorx.ajct.Adapter.AdapterDataLaundry;
import com.anditorx.ajct.Model.DataLaundryModel;
import com.anditorx.ajct.Model.ResponseLaundryModel;
import com.anditorx.ajct.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvDataLaundry;
    private RecyclerView.Adapter adDataLaundry;
    private RecyclerView.LayoutManager lmDataLaundry;
    private List<DataLaundryModel> listDataLaundry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvDataLaundry = findViewById(R.id.rv_data);
        lmDataLaundry = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDataLaundry.setLayoutManager(lmDataLaundry);
        retrieveDataLaundry();
    }

    public void retrieveDataLaundry(){
        APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
        Call<ResponseLaundryModel> viewDataLaundry = ardLaundry.ardRetrieveDataLaundry();

        viewDataLaundry.enqueue(new Callback<ResponseLaundryModel>() {
            @Override
            public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();


                Toast.makeText(MainActivity.this, "Successfully get data laundry", Toast.LENGTH_SHORT).show();

                listDataLaundry = response.body().getData();

                adDataLaundry = new AdapterDataLaundry(MainActivity.this, listDataLaundry);
                rvDataLaundry.setAdapter(adDataLaundry);
                adDataLaundry.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cannot call server", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}