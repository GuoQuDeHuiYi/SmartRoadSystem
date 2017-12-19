package com.smartcity.qiuchenly.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Eason on 2017/11/18.
 */

public class mPrepaidRecyclerViewAdapter extends RecyclerView.Adapter<mPrepaidRecyclerViewAdapter.VH>  {

    class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
    }

    @Override
    public mPrepaidRecyclerViewAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(mPrepaidRecyclerViewAdapter.VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
