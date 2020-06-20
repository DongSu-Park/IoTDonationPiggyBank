package com.flore.iotdonationpiggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        fragment1 = new Fragment1(); // 메인화면
        fragment2 = new Fragment2(); // 기부 내역 리스트
        fragment3 = new Fragment3(); // 마일리지 사용
        fragment4 = new Fragment4(); // 기부 뉴스

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // 처음에 나와야 할 메인화면 설정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment1).commitAllowingStateLoss();


        // 아래 메뉴를 선택했을때 원하는 프레그먼트가 나오게 리스너 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               FragmentTransaction transaction = fragmentManager.beginTransaction();

               switch (menuItem.getItemId()){
                   case R.id.tab1:{
                       transaction.replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                       break;
                   }
                   case R.id.tab2:{
                       transaction.replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                       break;
                   }
                   case R.id.tab3:{
                       transaction.replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                       break;
                   }
                   case R.id.tab4:{
                       transaction.replace(R.id.main_layout, fragment4).commitAllowingStateLoss();
                       break;
                   }
               }
               return true;
           }
       });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment).commit();
    }
}