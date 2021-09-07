package com.flore.iotdonationpiggybank.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.flore.iotdonationpiggybank.R;
import com.flore.iotdonationpiggybank.ui.fragment.DonationLogFragment;
import com.flore.iotdonationpiggybank.ui.fragment.MileageUsesFragment;
import com.flore.iotdonationpiggybank.ui.fragment.DonationNewsFragment;
import com.flore.iotdonationpiggybank.ui.fragment.MainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager = getSupportFragmentManager();

    MainFragment mainFragment;
    DonationLogFragment donationLogFragment;
    MileageUsesFragment mileageUsesFragment;
    DonationNewsFragment donationNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initUser();
        changeFragment();
    }

    // Firebase 유저 정보 초기화
    private void initUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            // 로그인된 정보가 없으면 메인 액티비티 종료 후 로그인 액티비티로 이동
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // 프레그먼트 초기화
    private void initFragment() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        mainFragment = new MainFragment(); // 메인화면
        donationLogFragment = new DonationLogFragment(); // 기부 내역 리스트
        mileageUsesFragment = new MileageUsesFragment(); // 마일리지 사용
        donationNewsFragment = new DonationNewsFragment(); // 기부 뉴스

        // 처음에 나와야 할 메인화면 설정 (프레그먼트 트랜잭션 설정)
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, mainFragment).commitAllowingStateLoss();
    }

    // 바텀 네비게이션을 이용한 프레그먼트 변경 요청
    private void changeFragment() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // 트렌젝션이 새로 발생할때마다 객체를 새로 만들어 줘야 함. (프레그먼트 변경사항이 일어날때마다)
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.tab1:{
                        // 첫번째 메뉴 - 메인화면 프레그먼트 이동
                        transaction.replace(R.id.main_layout, mainFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.tab2:{
                        // 두번째 매뉴 - 기부 내역 확인 프레그먼트 이동
                        transaction.replace(R.id.main_layout, donationLogFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.tab3:{
                        // 세번째 매뉴 - 마일리지 사용 프레그먼트 이동
                        transaction.replace(R.id.main_layout, mileageUsesFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.tab4:{
                        // 네번째 매뉴 - 공지사항 메뉴 프래그먼트 이동
                        transaction.replace(R.id.main_layout, donationNewsFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }

    // 메인 엑티비티에서 백버튼 클릭 시 바로 종료되지 않게 타임을 확인해서 종료카운트 설정
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

    // 메인 프래그먼트의 각 아이콘을 눌렀을 때 프레그먼트 변경을 위한 함수 추가
    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment).commit();
    }
}