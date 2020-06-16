package com.flore.iotdonationpiggybank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register_done;
    RadioGroup rg_profile;
    public String userprofile;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rg_profile = findViewById(R.id.rg_reg_profile);

        mAuth = FirebaseAuth.getInstance(); // 파이어베이스 접근 초기화

        btn_register_done = findViewById(R.id.btn_register_done);
        btn_register_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() { // 가입을 위한 함수
        String email = ((EditText) findViewById(R.id.et_reg_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_reg_pw)).getText().toString();
        String passwordcheck = ((EditText) findViewById(R.id.et_reg_pw)).getText().toString();
        final String nickname = ((EditText) findViewById(R.id.et_reg_nickname)).getText().toString();
        final String phone = ((EditText) findViewById(R.id.et_reg_phone)).getText().toString();

        // 각 edittext form에 입력이 되어 있을 때
        if (email.length() > 0 && password.length() > 0 && passwordcheck.length() > 0 && nickname.length() > 0 && phone.length() > 0){
            if (password.equals(passwordcheck)){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){ // 가입 성공 시
                            FirebaseUser user = mAuth.getCurrentUser();

                            String getuid = user.getUid(); // 자동으로 생성된 UID 번호
                            String getemail = user.getEmail(); // 등록한 이메일 주소
                            String getname = nickname; // 등록한 닉네임
                            String getphone = phone; // 등록한 번호
                            String totalCoin = "0"; // 총 기부금액 0원으로 초기화
                            String totalMileage = "0"; // 총 마일리지 금액 0원으로 초기화
                            RadioButton rd = findViewById(rg_profile.getCheckedRadioButtonId());
                            if (rd.getText().toString().equals("일반")){
                               userprofile = "1";
                            } else if (rd.getText().toString().equals("사업주")){
                               userprofile = "2";
                            }

                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("userUid", getuid);
                            hashMap.put("userEmail", getemail);
                            hashMap.put("userName", getname);
                            hashMap.put("userPhone", getphone);
                            hashMap.put("totalCoin", totalCoin);
                            hashMap.put("totalMileage", totalMileage);
                            hashMap.put("userProfile", userprofile);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("User"); // User라는 공간에 기본 데이터 저장
                            ref.child(getuid).setValue(hashMap);

                            // 가입 완료시 화면 빠져나가기
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하였습니다", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(RegisterActivity.this, "이미 등록된 이메일 입니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this,"입력하지 않은 공간이 있습니다", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}