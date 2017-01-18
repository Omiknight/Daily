package com.cins.daily.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.mvp.entity.NewsSummary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<NewsSummary> mNewsSummaryList;

    public void setItems(List<NewsSummary> items) {
        this.mNewsSummaryList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        String title = mNewsSummaryList.get(position).getLtitle();
        if (title == null) {
            title = mNewsSummaryList.get(position).getTitle();
        }
        String time = mNewsSummaryList.get(position).getPtime();
        String digest = mNewsSummaryList.get(position).getDigest();
        String imgSrc = mNewsSummaryList.get(position).getImgsrc();

        holder.mNewsSummaryTitleTv.setText(title);
        holder.mNewsSummaryPtimeTv.setText(time);
        holder.mNewsSummaryDigestTv.setText(digest);
    }

    @Override
    public int getItemCount() {
        return mNewsSummaryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_summary_photo_iv)
        ImageView mNewsSummaryPhotoIv;
        @BindView(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @BindView(R.id.news_summary_digest_tv)
        TextView mNewsSummaryDigestTv;
        @BindView(R.id.news_summary_ptime_tv)
        TextView mNewsSummaryPtimeTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

