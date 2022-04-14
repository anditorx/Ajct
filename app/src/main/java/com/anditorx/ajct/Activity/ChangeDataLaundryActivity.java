package com.anditorx.ajct.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anditorx.ajct.API.APIRequestDataLaundry;
import com.anditorx.ajct.API.RetroServer;
import com.anditorx.ajct.Model.ResponseLaundryModel;
import com.anditorx.ajct.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeDataLaundryActivity extends AppCompatActivity {
    private int xId;
    private String xName, xAddress, xPhone;
    private EditText etName, etAddress, etPhone;
    private Button btnUpdate;
    private String yName, yAddress, yPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data_laundry);

        Intent receiveData = getIntent();
        xId = receiveData.getIntExtra("xId", -1);
        xName = receiveData.getStringExtra("xName");
        xAddress = receiveData.getStringExtra("xAddress");
        xPhone = receiveData.getStringExtra("xPhone");

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        btnUpdate = findViewById(R.id.btn_update);

        etName.setText(xName);
        etAddress.setText(xAddress);
        etPhone.setText(xPhone);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yName = etName.getText().toString();
                yAddress = etAddress.getText().toString();
                yPhone = etPhone.getText().toString();
                updateData();
            }
        });


    }

    private void updateData(){
        APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
        Call<ResponseLaundryModel> updateData = ardLaundry.ardUpdateDataLaundry(xId, yName, yAddress, yPhone);

        updateData.enqueue(new Callback<ResponseLaundryModel>() {
            @Override
            public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                Toast.makeText(ChangeDataLaundryActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                Toast.makeText(ChangeDataLaundryActivity.this, "Create data failed | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}