package com.flore.iotdonationpiggybank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Fragment3 extends Fragment {
    ViewGroup viewGroup;

    private RecyclerView rv_gift_list;
    private RecyclerView.LayoutManager layoutManager;
    private giftListAdapter giftListAdapter;

    public static Fragment3 newInstance() {
        return new Fragment3();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3,container,false);

        // 프레그먼트 3 시작
        init();

        getData();

        return viewGroup;
    }

    private void init() {
        rv_gift_list = viewGroup.findViewById(R.id.rv_gift_list);
        rv_gift_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rv_gift_list.setLayoutManager(layoutManager);

        giftListAdapter = new giftListAdapter();
        rv_gift_list.setAdapter(giftListAdapter);
    }

    private void getData() {
        List<Integer> listResId = Arrays.asList(
                R.drawable.piggy_bank,
                R.drawable.piggy_bank,
                R.drawable.piggy_bank,
                R.drawable.piggy_bank,
                R.drawable.piggy_bank
        );

        List<String> list_gift_item_name = Arrays.asList(
                "카페 아메리카노 Tall",
                "세븐일레븐 1만원권",
                "시크릿 어피치",
                "이디야커피_달고나 라떼",
                "패밀리 아이스크림"
        );

        List<String> list_gift_item_service = Arrays.asList(
                "스타벅스",
                "세븐일레븐",
                "베스킨라빈스",
                "이디야",
                "베스킨라빈스"
        );

        List<Integer> list_gift_item_point = Arrays.asList(
                4100,10000,28000,3800,4000
        );

        for (int i = 0; i < list_gift_item_name.size(); i++){
            giftListData giftListData = new giftListData();
            giftListData.setGift_img_resid(listResId.get(i));
            giftListData.setGift_item_name(list_gift_item_name.get(i));
            giftListData.setGift_item_service(list_gift_item_service.get(i));
            giftListData.setGift_item_point(list_gift_item_point.get(i));

            giftListAdapter.addItem(giftListData);
        }

        giftListAdapter.notifyDataSetChanged();
    }
}
