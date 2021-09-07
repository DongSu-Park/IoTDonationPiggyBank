package com.flore.iotdonationpiggybank.util.rvadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flore.iotdonationpiggybank.ui.activity.MileageUsesItemTargetActivity;
import com.flore.iotdonationpiggybank.R;
import com.flore.iotdonationpiggybank.ui.fragment.MileageUsesFragment;

import java.util.ArrayList;

public class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.ItemViewHolder> {

    private ArrayList<GiftListData> listData = new ArrayList<>();
    private GiftListData giftListData;
    Context context;

    @NonNull
    @Override
    public GiftListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mileage_uses_item, parent, false);
        this.context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftListAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(GiftListData giftListData){
        listData.add(giftListData);
    }

   class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv_gift_item_img;
        private TextView tv_gift_item_name;
        private TextView tv_gift_item_service;
        private TextView tv_gift_item_point;
        private GiftListData giftListData;


       ItemViewHolder(@NonNull View itemView) {
           super(itemView);

           iv_gift_item_img = itemView.findViewById(R.id.iv_gift_item_img);
           tv_gift_item_name = itemView.findViewById(R.id.tv_gift_item_name);
           tv_gift_item_service = itemView.findViewById(R.id.tv_gift_item_service);
           tv_gift_item_point = itemView.findViewById(R.id.tv_gift_item_point);
       }

       void onBind(GiftListData listData) {
            this.giftListData = listData;

            iv_gift_item_img.setImageResource(listData.getGift_img_resid());
            tv_gift_item_name.setText(listData.getGift_item_name());
            tv_gift_item_service.setText(listData.getGift_item_service());
            tv_gift_item_point.setText(String.valueOf(listData.getGift_item_point()) + " P");

            itemView.setOnClickListener(this);
       }

       @Override
       public void onClick(View view) {
           MileageUsesFragment mileageUsesFragment = new MileageUsesFragment();
           if(view.getId() == R.id.layout_gift_item){
               Intent intent = new Intent(context, MileageUsesItemTargetActivity.class);

               intent.putExtra("giftimgId", giftListData.getGift_img_resid());
               intent.putExtra("giftName",giftListData.getGift_item_name());
               intent.putExtra("giftService",giftListData.getGift_item_service());
               intent.putExtra("giftPoint",giftListData.getGift_item_point());

               context.startActivity(intent);
           }
       }
   }
}
