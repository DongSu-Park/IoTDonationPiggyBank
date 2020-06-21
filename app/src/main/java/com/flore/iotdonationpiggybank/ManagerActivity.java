package com.flore.iotdonationpiggybank;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerActivity extends AppCompatActivity {

    Button btn_manager_send;
    Button btn_manager_logout;

    TextView tv_manager_nickname;
    TextView tv_manager_donation_check;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    int device_total_money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        btn_manager_logout = findViewById(R.id.btn_manager_logout);
        btn_manager_send = findViewById(R.id.btn_manager_send);
        tv_manager_donation_check = findViewById(R.id.tv_manager_donation_check);
        tv_manager_nickname = findViewById(R.id.tv_manager_nickname);


        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

            databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myUser myUser = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myUser.class);
                    tv_manager_nickname.setText("안녕하세요! " + myUser.getUserName() +" 님.");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e){
            e.getStackTrace();
        }


        databaseReference.child("Device").child("Device_01").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myDevice myDevice = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myDevice.class);
                device_total_money = myDevice.getTotalMoney();
                tv_manager_donation_check.setText(String.valueOf(device_total_money) + "원");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_manager_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_manager_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ManagerActivity.this, R.style.myDialog));
                builder.setTitle("회수 요청 확인");
                builder.setMessage("기부저금통 사업체에 회수요청 메세지를 보내겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ManagerActivity.this, "담당 사업체에 회수 요청 메세지를 보냈습니다. 2-3일후에 회수 담당자가 방문 예정입니다.",Toast.LENGTH_LONG).show();
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
