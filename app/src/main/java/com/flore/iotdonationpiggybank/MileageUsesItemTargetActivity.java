package com.flore.iotdonationpiggybank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MileageUsesItemTargetActivity extends AppCompatActivity {

    ImageView iv_target_mileage_item_image;
    TextView tv_target_mileage_item_name;
    TextView tv_target_mileage_item_service;
    TextView tv_target_mileage_item_point;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_uses_item_target);

        iv_target_mileage_item_image = findViewById(R.id.iv_target_mileage_item_image);
        tv_target_mileage_item_name = findViewById(R.id.tv_target_mileage_item_name);
        tv_target_mileage_item_service = findViewById(R.id.tv_target_mileage_item_service);
        tv_target_mileage_item_point = findViewById(R.id.tv_target_mileage_item_point);

        int getResid = getIntent().getIntExtra("giftimgId",1);
        String getItemName = getIntent().getStringExtra("giftName");
        String getItemService = getIntent().getStringExtra("giftService");
        int getItemPoint = getIntent().getIntExtra("giftPoint",1);

        iv_target_mileage_item_image.setImageResource(getResid);
        tv_target_mileage_item_name.setText(getItemName);
        tv_target_mileage_item_service.setText(getItemService);
        tv_target_mileage_item_point.setText(String.valueOf(getItemPoint) + " P");
    }
}
