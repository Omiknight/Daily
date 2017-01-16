package com.cins.daily.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cins.daily.R;

import java.util.List;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<String> mNewsList;

    public NewsRecyclerViewAdapter(List<String> list) {
        mNewsList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        String msg = mNewsList.get(position);
        holder.mItemTv.setText(msg);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mItemTv;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemTv = (TextView) itemView.findViewById(R.id.msg);
        }
    }
}

