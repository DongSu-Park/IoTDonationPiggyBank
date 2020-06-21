package com.flore.iotdonationpiggybank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemViewHolder> {

    private ArrayList<NewsList> listData = new ArrayList<>();
    private NewsList newsList;
    Context context;

    @NonNull
    @Override
    public NewsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        this.context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(NewsList newsList){
        listData.add(newsList);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_news_title;
        private TextView tv_news_main;
        private NewsList newsList;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_news_title = itemView.findViewById(R.id.tv_news_title);
            tv_news_main = itemView.findViewById(R.id.tv_news_main);
        }

        void onBind(NewsList newsList) {
            this.newsList = newsList;

            tv_news_title.setText(newsList.getNews_title());
            tv_news_main.setText(newsList.getNews_main());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Fragment4 fragment4 = new Fragment4();

            if (view.getId() == R.id.lv_newslist){
                Intent intent = new Intent(context, NewsInfoActivity.class);
                context.startActivity(intent);
            }
        }


    }
}
