package com.flore.iotdonationpiggybank;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {

    private RecyclerView rv_donation_list;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private ArrayList<myDonationList> arrayList;
    private ArrayList<myDonationListToGeo> geoArrayList;

    TextView tv_donation_check_fr2;

    ViewGroup viewGroup;

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        // 프레그먼트2 (기부 내역 리스트) 코드 실행

        rv_donation_list = viewGroup.findViewById(R.id.rv_donation_list);
        rv_donation_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_donation_list.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // myDonationList 객체를 담을 어레이 리스트
        geoArrayList = new ArrayList<>(); // 이걸로 보내야함~!

        tv_donation_check_fr2 = viewGroup.findViewById(R.id.tv_donation_check_fr2);

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String getuid = user.getUid(); // 로그인한 회원의 uid 값이 가져옴

            databaseReference.child("User").child(getuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myUser myUser = dataSnapshot.getValue(com.flore.iotdonationpiggybank.myUser.class);
                    tv_donation_check_fr2.setText(myUser.getTotalCoin() + "원");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            databaseReference.child("User").child(getuid).child("donationLog").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // 파이어베이스 데이터베이스의 데이터를 받아옴
                    arrayList.clear(); // 기존 배열리스트 존재하지 않게 초기화
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        myDonationList myDonationList = snapshot.getValue(com.flore.iotdonationpiggybank.myDonationList.class);
                        arrayList.add(myDonationList); // 일단 myDonationList에 저장
                    }

                    if (arrayList.size() > 0){
                        Geocoder geocoder = new Geocoder(getContext());

                        for (int i = 0; i < arrayList.size(); i++){
                            try{
                                double lat = Double.parseDouble(arrayList.get(i).getLocation_lat());
                                double lng = Double.parseDouble(arrayList.get(i).getLocation_lng());
                                List<Address> getAddress = geocoder.getFromLocation(lat, lng,1);

                                myDonationListToGeo myDonationListToGeo = new myDonationListToGeo(arrayList.get(i).getInsertCoin(), arrayList.get(i).getGetMileage(),
                                        arrayList.get(i).getDate(), getAddress.get(0).getAddressLine(0));

                                geoArrayList.add(myDonationListToGeo);
                            } catch (Exception e){
                                e.getStackTrace();
                            }
                        }

                        adapter = new UserDonationListAdapter(geoArrayList);
                        rv_donation_list.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 디비 에러 발생시
                    Log.e("Fragment2", String.valueOf(databaseError.toException()));
                }
            });
        } catch (Exception e){
            e.getStackTrace();
        }
        return viewGroup;
    }
}
