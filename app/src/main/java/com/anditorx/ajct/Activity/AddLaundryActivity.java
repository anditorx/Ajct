package com.anditorx.ajct.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
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

public class AddLaundryActivity extends AppCompatActivity {
    private EditText etName, etAddress, etPhone;
    private Button btnSave;
    private String name, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laundry);

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                address = etAddress.getText().toString();
                phone = etPhone.getText().toString();

                // validation
                if (name.trim().equals("")){
                    etName.setError("Please input name");
                }else if (address.trim().equals("")){
                    etAddress.setError("Please input address");
                }else if (phone.trim().equals("")){
                    etPhone.setError("Please input phone");
                }else {
                    createData();
                }
            }
        });

    }

    private void createData(){
        APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
        Call<ResponseLaundryModel> saveData = ardLaundry.ardCreateDataLaundry(name, address, phone);
        
        saveData.enqueue(new Callback<ResponseLaundryModel>() {
            @Override
            public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                Toast.makeText(AddLaundryActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                Toast.makeText(AddLaundryActivity.this, "Create data failed | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}