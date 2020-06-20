package com.flore.iotdonationpiggybank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NfcReadActivity extends AppCompatActivity {
    TextView tv_get_coin;
    TextView tv_get_mileage;
    TextView tv_nfcread_coin_check;

    Button btn_nfcread_close;

    String hwID;

    int newGetCoin;
    int newGetMileage;

    int getWaitCoin;
    Double getDevicelat;
    Double getDevicelng;

    String getMileage;

    public String get_user_donation_coin;
    public String get_user_mileage;
    public String getuid;

    int waitCoinZero = 0;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcread);

        tv_get_coin = findViewById(R.id.tv_get_coin);
        tv_get_mileage = findViewById(R.id.tv_get_mileage);
        btn_nfcread_close = findViewById(R.id.btn_nfcread_close);
        tv_nfcread_coin_check = findViewById(R.id.tv_nfcread_coin_check);

        Intent getHardwareId = getIntent();
        hwID = getHardwareId.getStringExtra("hardwareDeviceId"); // 아이디값 가져옴

        // 1. 해당 디바이스 id의 모든 값을 가져옴
        databaseReference.child("Device").child(hwID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myDevice myDevice = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myDevice.class);
                getWaitCoin = myDevice.getWaitCoin(); // 중요~! : 하드웨어에서 유저가 동전 넣은 값을 가져옴
                getDevicelat = myDevice.getLat();
                getDevicelng = myDevice.getLng();

                if ( getWaitCoin > 0 ) {
                    // 2. Deviceid에 있는 waitCoin을 0으로 초기화
                    tv_get_coin.setText("기부 동전 금액 : " + getWaitCoin +"원");
                    databaseReference.child("Device").child(hwID).child("waitCoin").setValue(waitCoinZero);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

                    try {
                        databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                myUser myUser = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myUser.class);

                                get_user_donation_coin = myUser.getTotalCoin(); // 원래 유저가 기부한 총 금액 가져옴 (string)
                                get_user_mileage = myUser.getTotalMileage(); // 원래 유저가 가지고 있는 마일리지 금액 가져 옴

                                newGetCoin = Integer.parseInt(get_user_donation_coin) + getWaitCoin; // 더해진 값이 나와야함 (정상) (int)
                                newGetMileage = Integer.parseInt(get_user_mileage) + (getWaitCoin / 10); // 환산한 마일리지 값 ex. 500 + (500/10) = 550 (오류)

                                tv_get_mileage.setText("적립 마일리지 점수 : " + (getWaitCoin / 10) + "점");

                                // 유저의 총 기부 금액과 마일리지 점수를 업데이트 하기
                                if(!get_user_donation_coin.equals(String.valueOf(newGetCoin))){
                                    databaseReference.child("User").child(getuid).child("totalCoin").setValue(String.valueOf(newGetCoin));
                                    databaseReference.child("User").child(getuid).child("totalMileage").setValue(String.valueOf(newGetMileage));

                                    // donation log 남기기
                                    // 시간 가져오기
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date time = new Date();
                                    String time1 = format1.format(time); // 현재시간 나타내기

                                    HashMap<Object, String> hashMap = new HashMap<>();
                                    hashMap.put("insertCoin", String.valueOf(getWaitCoin));
                                    hashMap.put("getMileage", String.valueOf(getWaitCoin / 10));
                                    hashMap.put("date",time1);
                                    hashMap.put("location_lat", String.valueOf(getDevicelat));
                                    hashMap.put("location_lng", String.valueOf(getDevicelng));

                                    // 기부금 로그 user 목록에 남기기
                                    databaseReference.child("User").child(getuid).child("donationLog").child(time1).setValue(hashMap);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("NfcReadActivity","loadPost:onCancelled", databaseError.toException());
                            }
                        });

                    } catch (Exception e){
                        e.getStackTrace();
                    }
                } else {
                    Toast.makeText(NfcReadActivity.this,"기부 저금통에 동전을 투입하지 않았습니다.",Toast.LENGTH_LONG).show();
                    tv_nfcread_coin_check.setText("기부 저금통에 동전을 투입하지 않았습니다.");
                    tv_get_coin.setText("");
                    tv_get_mileage.setText("");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("NfcReadActivity","loadPost:onCancelled", databaseError.toException());
            }
        });

        btn_nfcread_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
