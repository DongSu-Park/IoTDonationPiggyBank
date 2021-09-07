package com.flore.iotdonationpiggybank.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flore.iotdonationpiggybank.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MileageUsesItemGetActivity extends AppCompatActivity {
    ImageView iv_uses_mileage_item_image;
    TextView tv_uses_mileage_item_name;
    TextView tv_uses_mileage_item_service;
    TextView tv_uses_item_time;

    Button btn_giftcart;
    Button btn_gift_close;

    int getResid;
    String getItemName;
    String getItemService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_uses_item_ok);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time); // 현재시간 나타내기

        iv_uses_mileage_item_image = findViewById(R.id.iv_uses_mileage_item_image);
        tv_uses_mileage_item_name = findViewById(R.id.tv_uses_mileage_item_name);
        tv_uses_mileage_item_service = findViewById(R.id.tv_uses_mileage_item_service);
        tv_uses_item_time = findViewById(R.id.tv_uses_item_time);

        btn_giftcart = findViewById(R.id.btn_giftcart);
        btn_gift_close = findViewById(R.id.btn_gift_close);

        getResid = getIntent().getIntExtra("okgiftImgID",1);
        getItemName = getIntent().getStringExtra("okgiftName");
        getItemService = getIntent().getStringExtra("okgiftService");

        iv_uses_mileage_item_image.setImageResource(getResid);
        tv_uses_mileage_item_name.setText(getItemName);
        tv_uses_mileage_item_service.setText(getItemService);
        tv_uses_item_time.setText(time1);

        btn_giftcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"보관함은 현재 지원하지 않습니다.", Toast.LENGTH_LONG).show();
            }
        });

        btn_gift_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
