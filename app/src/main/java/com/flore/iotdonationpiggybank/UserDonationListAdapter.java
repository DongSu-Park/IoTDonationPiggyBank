package com.flore.iotdonationpiggybank;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDonationListAdapter extends RecyclerView.Adapter<UserDonationListAdapter.ItemViewHolder> {

    private ArrayList<myDonationListToGeo> arrayList;
    private Geocoder geocoder;
    private Context context;

    public UserDonationListAdapter(ArrayList<myDonationListToGeo> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donationlist_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tv_user_donation_coin.setText(arrayList.get(position).getInsertCoin());
        holder.tv_user_mileage.setText(arrayList.get(position).getGetMileage());
        holder.tv_user_time.setText(arrayList.get(position).getDate());
        holder.tv_user_location.setText(arrayList.get(position).getLocation_change());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_donationlist_icon; // 아이콘 이미지
        TextView tv_user_donation_coin; // 유저가 기부한 금액
        TextView tv_user_mileage; // 유저가 얻은 마일리지
        TextView tv_user_time; // 유저가 기부한 시간
        TextView tv_user_location; // 유저가 기부한 위치 (디바이스 위치) 참고로 이건 lat과 lng을 받고나서 구글 지도를 통해 바꿔줘야함

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_donationlist_icon = itemView.findViewById(R.id.iv_donationlist_icon);
            this.tv_user_donation_coin = itemView.findViewById(R.id.tv_user_donation_coin);
            this.tv_user_mileage = itemView.findViewById(R.id.tv_user_mileage);
            this.tv_user_time = itemView.findViewById(R.id.tv_user_time);
            this.tv_user_location = itemView.findViewById(R.id.tv_user_location);
        }
    }
}
