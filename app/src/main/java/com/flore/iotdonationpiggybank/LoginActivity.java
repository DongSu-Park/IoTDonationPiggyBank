package com.flore.iotdonationpiggybank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth loginAuth;

    Button btn_register; // 회원 가입 버튼
    Button btn_login;
    // Button btn_findpw;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAuth = FirebaseAuth.getInstance(); // 파이어베이스 기본 초기화

        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        btn_register.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼을 누르면 회원가입 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() { // 로그인 버튼이 눌러지면
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });
    }

    private void startLogin() {
        String email = ((EditText) findViewById(R.id.et_login_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_login_pw)).getText().toString();

        if (email.length() > 0 && password.length() > 0){ // 이메일과 패스워드가 입력이 되어 있을때
            loginAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = loginAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다", Toast.LENGTH_LONG).show();

                                String getuid = loginAuth.getCurrentUser().getUid(); // 로그인한 회원의 uid 값이 가져옴
                                databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        myUser myUser = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myUser.class);
                                        String userProfile = myUser.getUserProfile();

                                        if (userProfile.equals("1"))  // 일반 사용자 화면으로
                                        {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        } else if (userProfile.equals("2")) // 사업주 화면으로
                                        {
                                            Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
        }
    }
}