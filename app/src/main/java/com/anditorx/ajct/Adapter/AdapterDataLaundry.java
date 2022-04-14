package com.anditorx.ajct.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anditorx.ajct.API.APIRequestDataLaundry;
import com.anditorx.ajct.API.RetroServer;
import com.anditorx.ajct.Activity.ChangeDataLaundryActivity;
import com.anditorx.ajct.Activity.MainActivity;
import com.anditorx.ajct.Model.DataLaundryModel;
import com.anditorx.ajct.Model.ResponseLaundryModel;
import com.anditorx.ajct.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Handler;

public class AdapterDataLaundry extends RecyclerView.Adapter<AdapterDataLaundry.HolderData> {
    private Context ctx;
    private List<DataLaundryModel> listLaundry;
    private List<DataLaundryModel> listDataLaundry;
    private int id;

    public AdapterDataLaundry(Context ctx, List<DataLaundryModel> listLaundry) {
        this.ctx = ctx;
        this.listLaundry = listLaundry;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataLaundryModel dlm = listLaundry.get(position);
        holder.tvId.setText(String.valueOf(dlm.getId()));
        holder.tvName.setText(dlm.getName());
        holder.tvAddress.setText(dlm.getAddress());
        holder.tvPhone.setText(dlm.getPhone());
    }

    @Override
    public int getItemCount() {
        return listLaundry.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvAddress, tvPhone;


        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvPhone = itemView.findViewById(R.id.tv_phone);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogMessage = new AlertDialog.Builder(ctx);
                    dialogMessage.setMessage("Action");
                    dialogMessage.setCancelable(true);

                    id = Integer.parseInt(tvId.getText().toString());

                    dialogMessage.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            boolean handler = new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Your Code
                                    ((MainActivity) ctx).retrieveDataLaundry();
                                }
                            }, 500);

                        }
                    });

                    dialogMessage.setNegativeButton("Change", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            detailData();
                            dialog.dismiss();
                        }
                    });

                    dialogMessage.show();

                    return false;
                }
            });
        }

        private void deleteData (){
            APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
            Call<ResponseLaundryModel> deleteData = ardLaundry.ardDeleteDataLaundry(id);

            deleteData.enqueue(new Callback<ResponseLaundryModel>() {
                @Override
                public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                    int code = response.body().getCode();
                    String message = response.body().getMessage();

                    Toast.makeText(ctx, "Code | "+code+" | message: "+message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                    Toast.makeText(ctx, "Failed delete data | Message : "+t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void detailData (){
            APIRequestDataLaundry ardLaundry = RetroServer.connectRetrofit().create(APIRequestDataLaundry.class);
            Call<ResponseLaundryModel> detailData = ardLaundry.ardDetailDataLaundry(id);

            detailData.enqueue(new Callback<ResponseLaundryModel>() {
                @Override
                public void onResponse(Call<ResponseLaundryModel> call, Response<ResponseLaundryModel> response) {
                    int code = response.body().getCode();
                    String message = response.body().getMessage();

                    listDataLaundry = response.body().getData();
                    int varIdLaundry = listDataLaundry.get(0).getId();
                    String varNamaLaundry = listDataLaundry.get(0).getName();
                    String varAlamatLaundry = listDataLaundry.get(0).getAddress();
                    String varPhoneLaundry = listDataLaundry.get(0).getPhone();

                    Intent parseData = new Intent(ctx, ChangeDataLaundryActivity.class);
                    parseData.putExtra("xId", varIdLaundry);
                    parseData.putExtra("xName", varNamaLaundry);
                    parseData.putExtra("xAddress", varAlamatLaundry);
                    parseData.putExtra("xPhone", varPhoneLaundry);
                    ctx.startActivity(parseData);
//                    Toast.makeText(ctx, "Code | "+code+" | message: "+message+" | nama laundry : "+varNamaLaundry, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseLaundryModel> call, Throwable t) {
                    Toast.makeText(ctx, "Failed delete data | Message : "+t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
