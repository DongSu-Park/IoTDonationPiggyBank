package com.flore.iotdonationpiggybank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flore.iotdonationpiggybank.R;
import com.flore.iotdonationpiggybank.model.MyUser;
import com.flore.iotdonationpiggybank.ui.activity.LoginActivity;
import com.flore.iotdonationpiggybank.ui.activity.MainActivity;
import com.flore.iotdonationpiggybank.ui.activity.NfcTagReadyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFragment extends Fragment {
    Button btn_logout; // 로그아웃 버튼

    TextView tv_welcome_nickname; // 닉네임 표시
    TextView tv_donation_check; // 기부한 금액 표시
    TextView tv_mileage_check; // 마일리지 표시
    ImageView iv_gotoNFC;
    ImageView iv_gotoList;
    ImageView iv_gotoMileage;
    ImageView iv_gotoNews;

    public String get_id;
    public String get_donation;
    public String get_mileage;

    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    ViewGroup viewGroup;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1,container,false);
        // 프레그먼트 1 코드 실행

        iv_gotoNFC = viewGroup.findViewById(R.id.iv_gotoNFC);
        iv_gotoList = viewGroup.findViewById(R.id.iv_gotoList);
        iv_gotoMileage = viewGroup.findViewById(R.id.iv_gotoMileage);
        iv_gotoNews = viewGroup.findViewById(R.id.iv_gotoNews);

        btn_logout = viewGroup.findViewById(R.id.btn_logout);

        tv_welcome_nickname = viewGroup.findViewById(R.id.tv_welcome_nickname);
        tv_donation_check = viewGroup.findViewById(R.id.tv_donation_check);
        tv_mileage_check = viewGroup.findViewById(R.id.tv_mileage_check);

        // 회원이 기존에 가지고 있는 금액 가져오기
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

            databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    MyUser myUser = dataSnapshot.getValue(MyUser.class);

                    get_id = myUser.getUserName(); // 닉네임
                    get_donation = myUser.getTotalCoin(); // 현재 기부한 금액
                    get_mileage = myUser.getTotalMileage(); // 현재 남은 마일리지 금액

                    tv_welcome_nickname.setText("안녕하세요 " + get_id + " 님!");
                    tv_donation_check.setText(get_donation + "원");
                    tv_mileage_check.setText(get_mileage + "점");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Fragment1","loadPost:onCancelled", databaseError.toException());
                }
            });

        } catch (Exception e){
            e.getStackTrace();
        }

        // 이미지 뷰로 선택시 인텐트

        iv_gotoNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NfcTagReadyActivity.class);
                startActivity(intent);
            }
        });

        iv_gotoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(DonationLogFragment.newInstance());
            }
        });

        iv_gotoMileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(MileageUsesFragment.newInstance());
            }
        });

        iv_gotoNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(DonationNewsFragment.newInstance());
            }
        });

        // 로그아웃 버튼 클릭 시
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    @Override
    public void onResume() { // 다시 돌아올경우 리플레쉬
        super.onResume();
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

            databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    MyUser myUser = dataSnapshot.getValue(MyUser.class);

                    get_id = myUser.getUserName(); // 닉네임
                    get_donation = myUser.getTotalCoin(); // 현재 기부한 금액
                    get_mileage = myUser.getTotalMileage(); // 현재 남은 마일리지 금액

                    tv_welcome_nickname.setText("안녕하세요! " + get_id + " 님!");
                    tv_donation_check.setText(get_donation + "원");
                    tv_mileage_check.setText(get_mileage + "점");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Fragment1","loadPost:onCancelled", databaseError.toException());
                }
            });

        } catch (Exception e){
            e.getStackTrace();
        }
    }
}
