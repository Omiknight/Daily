package com.cins.daily.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.greendao.NewsChannelTable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/19.
 */

public class NewsChannelAdapter extends RecyclerView.Adapter<NewsChannelAdapter.ViewHolder> {

    private List<NewsChannelTable> mNewsChannelTableList;

    public NewsChannelAdapter(List<NewsChannelTable> newsChannelTableList) {
        mNewsChannelTableList = newsChannelTableList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsChannelTable newsChannelTable = mNewsChannelTableList.get(position);
        String newsChannelName = newsChannelTable.getNewsChannelName();
        holder.mNewsChannelTv.setText(newsChannelName);

        if (newsChannelTable.getNewsChannelIndex() == 0) {
            holder.mNewsChannelTv.setTextColor(ContextCompat.getColor(App.getAppContext(),R.color.alpha_40_black));
        }
    }

    @Override
    public int getItemCount() {
        return mNewsChannelTableList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_channel_tv)
        TextView mNewsChannelTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
