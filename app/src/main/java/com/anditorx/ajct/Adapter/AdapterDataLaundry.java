package com.anditorx.ajct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anditorx.ajct.Model.DataLaundryModel;
import com.anditorx.ajct.R;

import java.util.List;

public class AdapterDataLaundry extends RecyclerView.Adapter<AdapterDataLaundry.HolderData> {
    private Context ctx;
    private List<DataLaundryModel> listLaundry;

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
        }
    }
}
