package com.flore.iotdonationpiggybank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NfcReadActivity extends AppCompatActivity {
    TextView tv_hw_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcread);

        tv_hw_id = findViewById(R.id.tv_hw_id);

        Intent getHardwareId = getIntent();
        String hwID = getHardwareId.getStringExtra("hardwareDeviceId");

        tv_hw_id.setText("넘어온 id값 " + hwID);
    }
}
