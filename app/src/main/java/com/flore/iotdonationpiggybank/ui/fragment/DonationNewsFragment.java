package com.flore.iotdonationpiggybank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flore.iotdonationpiggybank.util.rvadapter.NewsAdapter;
import com.flore.iotdonationpiggybank.util.rvadapter.NewsList;
import com.flore.iotdonationpiggybank.R;

import java.util.Arrays;
import java.util.List;

public class DonationNewsFragment extends Fragment {
    ViewGroup viewGroup;

    private RecyclerView rv_news_list;
    private RecyclerView.LayoutManager layoutManager;
    private NewsAdapter newsAdapter;

    public static DonationNewsFragment newInstance() {
        return new DonationNewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment4,container,false);

        init();

        getData();

        return viewGroup;
    }
    private void init() {
        rv_news_list = viewGroup.findViewById(R.id.rv_news_list);
        rv_news_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rv_news_list.setLayoutManager(layoutManager);

        newsAdapter = new NewsAdapter();
        rv_news_list.setAdapter(newsAdapter);
    }

    private void getData() {
        List<String> list_title = Arrays.asList(
                "2020. 6월 업데이트",
                "2020. 5월 업데이트",
                "2020. 4월 업데이트",
                "2020. 3월 업데이트",
                "2020. 2월 업데이트",
                "2020. 1월 업데이트"
        );

        List<String> list_main = Arrays.asList(
                "6월의 기부처 기부내용입니다.",
                "5월의 기부처 기부내용입니다.",
                "4월의 기부처 기부내용입니다.",
                "3월의 기부처 기부내용입니다.",
                "2월의 기부처 기부내용입니다.",
                "1월의 기부처 기부내용입니다."
        );

        for (int i = 0; i < list_title.size(); i++){
            NewsList newsList = new NewsList();
            newsList.setNews_title(list_title.get(i));
            newsList.setNews_main(list_main.get(i));

            newsAdapter.addItem(newsList);
        }

        newsAdapter.notifyDataSetChanged();

    }


}
