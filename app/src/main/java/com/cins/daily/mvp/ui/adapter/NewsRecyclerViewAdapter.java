package com.cins.daily.mvp.ui.adapter;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.listener.OnItemClickListener;
import com.cins.daily.mvp.entity.NewsSummary;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<NewsSummary> mNewsSummaryList;
    private OnItemClickListener mOnItemClickListener;
    private int mLastPosition = -1;

    @Inject
    public NewsRecyclerViewAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public List<NewsSummary> getNewsSummaryList() {
        return mNewsSummaryList;
    }

    public void setItems(List<NewsSummary> items) {
        this.mNewsSummaryList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        setItemOnClick(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setItemValues(holder, position);
        setItemAppearAnimation(holder, position);
    }

    private void setItemAppearAnimation(ViewHolder holder, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_bottom_in);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    private void setItemValues(ViewHolder holder, int position) {
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

        Glide.with(App.getAppContext()).load(imgSrc).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_load_fail)
                .into(holder.mNewsSummaryPhotoIv);
    }

    @Override
    public int getItemCount() {
        return mNewsSummaryList.size();
    }

    public void setItemOnClick(final ViewHolder itemOnClick) {
        if (mOnItemClickListener != null) {
            itemOnClick.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(itemOnClick.itemView, itemOnClick.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.itemView.getAnimation() != null && holder.itemView
                .getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
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

