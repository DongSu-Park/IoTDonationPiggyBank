package com.flore.iotdonationpiggybank.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flore.iotdonationpiggybank.R;
import com.flore.iotdonationpiggybank.model.MyUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MileageUsesItemTargetActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    ImageView iv_target_mileage_item_image;
    TextView tv_target_mileage_item_name;
    TextView tv_target_mileage_item_service;
    TextView tv_target_mileage_item_point;

    int getResid;
    String getItemName;
    String getItemService;
    int getItemPoint;

    Button btn_gift_change;
    int originMileage;
    String getuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_uses_item_target);

        iv_target_mileage_item_image = findViewById(R.id.iv_target_mileage_item_image);
        tv_target_mileage_item_name = findViewById(R.id.tv_target_mileage_item_name);
        tv_target_mileage_item_service = findViewById(R.id.tv_target_mileage_item_service);
        tv_target_mileage_item_point = findViewById(R.id.tv_target_mileage_item_point);
        btn_gift_change = findViewById(R.id.btn_gift_change);


        getResid = getIntent().getIntExtra("giftimgId",1);
        getItemName = getIntent().getStringExtra("giftName");
        getItemService = getIntent().getStringExtra("giftService");
        getItemPoint = getIntent().getIntExtra("giftPoint",1);

        iv_target_mileage_item_image.setImageResource(getResid);
        tv_target_mileage_item_name.setText(getItemName);
        tv_target_mileage_item_service.setText(getItemService);
        tv_target_mileage_item_point.setText(String.valueOf(getItemPoint) + " P");

        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

            databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    MyUser myUser = dataSnapshot.getValue(MyUser.class);
                    originMileage = Integer.parseInt(myUser.getTotalMileage());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e){
            e.getStackTrace();
        }

        btn_gift_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MileageUsesItemTargetActivity.this, R.style.myDialog));
                builder.setTitle("구매 확인");
                builder.setMessage(String.valueOf(getItemPoint) + " 마일리지를 사용하여 상품으로 교환하시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int changeMileage = originMileage - getItemPoint;
                        if (changeMileage >= 0)
                        {
                            databaseReference.child("User").child(getuid).child("totalMileage").setValue(String.valueOf(changeMileage));
                            Intent intent = new Intent(getApplicationContext(), MileageUsesItemGetActivity.class);
                            intent.putExtra("okgiftImgID", getResid);
                            intent.putExtra("okgiftName", getItemName);
                            intent.putExtra("okgiftService", getItemService);
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(getApplicationContext(),"마일리지 점수가 부족합니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }
}
