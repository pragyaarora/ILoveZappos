package com.example.pragya.ilovezappos;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by pragya on 2/5/2017.
 */

public class ZapposItemAdapter extends RecyclerView.Adapter<ZapposItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<ZapposItem> mItemsList;

    public ZapposItemAdapter(List<ZapposItem> list, Context context) {
        this.mItemsList = list;
        this.mContext = context;
    }

    public void setList(List<ZapposItem> list) {
        this.mItemsList.clear();
        this.mItemsList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("pragya", "Inside oncreate view holder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.card_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ZapposItem item = mItemsList.get(position);
        Log.d("pragya", "Inside onBindViewHolder");
        holder.mBinder.setVariable(com.example.pragya.ilovezappos.BR.item, item);
        ImageView img = (ImageView) holder.mBinder.getRoot().findViewById(R.id.card_img);
        Glide.with(mContext).load(item.thumbnailImageUrl).placeholder(R.drawable.placeholder).centerCrop().crossFade().into(img);
    }

    @Override
    public int getItemCount() {
        return this.mItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding  mBinder;

        public MyViewHolder(ViewDataBinding  binding) {
            super(binding.getRoot());
            this.mBinder = binding;
            mBinder.executePendingBindings();
        }
    }
}
