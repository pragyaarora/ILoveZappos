package com.example.pragya.ilovezappos;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by pragya on 2/5/2017.
 */

public class ZapposItemAdapter extends RecyclerView.Adapter<ZapposItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<ZapposItem> mItemsList;
    View fabView;

    int count = 0;

    public ZapposItemAdapter(List<ZapposItem> list, Context context, View fabView) {
        this.mItemsList = list;
        this.mContext = context;
        this.fabView = fabView;
    }

    public void setList(List<ZapposItem> list) {
        this.mItemsList.clear();
        count = 0;
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

        final ZapposItem item = mItemsList.get(position);
        Log.d("pragya", "Inside onBindViewHolder");
        holder.setItem(item);
        holder.mBinder.setVariable(com.example.pragya.ilovezappos.BR.item, item);
        ImageView img = (ImageView) holder.mBinder.getRoot().findViewById(R.id.card_img);
        Glide.with(mContext).load(item.thumbnailImageUrl).placeholder(R.drawable.placeholder).crossFade().into(img);
        ImageView heartImg = (ImageView) holder.mBinder.getRoot().findViewById(R.id.heart_img);
        if (item.pressed)
            heartImg.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            heartImg.setImageResource(R.drawable.ic_favorite_border_black_24dp);


        heartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) fabView.findViewById(R.id.count);
                item.pressed = !item.pressed;

                if (item.pressed) {
                    count++;
                    ((ImageView) v).setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    count--;
                    ((ImageView) v).setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                textView.setText("" + count);
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bounce_animation);
                fabView.startAnimation(animation);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding mBinder;
        ZapposItem item;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinder = binding;
            mBinder.executePendingBindings();

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ItemDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("brandName", item.brandName);
                    bundle.putString("productName", item.productName);
                    bundle.putString("price", item.price);
                    bundle.putString("image_url", item.thumbnailImageUrl);

                    intent.putExtras(bundle);

                    mContext.startActivity(intent);
                }
            });
        }

        public void setItem(ZapposItem item) {
            this.item = item;
        }
    }
}
