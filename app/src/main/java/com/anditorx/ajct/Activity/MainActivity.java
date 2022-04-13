package com.anditorx.ajct.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anditorx.ajct.API.APIRequestDataLaundry;
import com.anditorx.ajct.API.RetroServer;
import com.anditorx.ajct.Adapter.AdapterDataLaundry;
import com.anditorx.ajct.Model.DataLaundryModel;
import com.anditorx.ajct.Model.ResponseLaundryModel;
import com.anditorx.ajct.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvDataLaundry;
    private FloatingActionButton fabAdd;
    private RecyclerView.Adapter adDataLaundry;
    private RecyclerView.LayoutManager lmDataLaundry;
    private List<DataLaundryModel> listDataLaundry = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLaundry;
    private ProgressBar pbDataLaundry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLaundry = findViewById(R.id.srl_dataLaundry);
        pbDataLaundry = findViewById(R.id.pb_dataLaundry);
        rvDataLaundry = findViewById(R.id.rv_data);
        fabAdd = findViewById(R.id.fab_add);
        lmDataLaundry = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDataLaundry.setLayoutManager(lmDataLaundry);
        // retrieveDataLaundry();

        // swipe refresh
        swipeRefreshLaundry.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLaundry.setRefreshing(true);
                retrieveDataLaundry();
                swipeRefreshLaundry.setRefreshing(false);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLaundryActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveDataLaundry();
    }

    public void retrieveDataLaundry(){
        pbDataLaundry.setVisibility(View.VISIBLE);

        APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
        Call<ResponseLaundryModel> viewDataLaundry = ardLaundry.ardRetrieveDataLaundry();

        viewDataLaundry.enqueue(new Callback<ResponseLaundryModel>() {
            @Override
            public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                listDataLaundry = response.body().getData();

                adDataLaundry = new AdapterDataLaundry(MainActivity.this, listDataLaundry);
                rvDataLaundry.setAdapter(adDataLaundry);
                adDataLaundry.notifyDataSetChanged();
                pbDataLaundry.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cannot call server", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
                pbDataLaundry.setVisibility(View.VISIBLE);
            }
        });
    }



}