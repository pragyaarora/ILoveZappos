package com.example.pragya.ilovezappos;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by pragya on 2/6/2017.
 */

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_detail);

        Bundle bundle = getIntent().getExtras();
        String productName = bundle.getString("productName");
        String url = bundle.getString("image_url");
        String price = bundle.getString("price");
        String brandName = bundle.getString("brandName");

        //Glide.with(mContext).load(item.thumbnailImageUrl).placeholder(R.drawable.placeholder).crossFade().into(img);
        ImageView imageView = (ImageView) findViewById(R.id.card_img);
        Glide.with(this).load(url).placeholder(R.drawable.placeholder).crossFade().into(imageView);

        TextView productText = (TextView) findViewById(R.id.product_name);
        productText.setText(Html.fromHtml(productName));

        TextView priceTextview = (TextView) findViewById(R.id.price);
        priceTextview.setText(price);

        TextView brandNameTV = (TextView) findViewById(R.id.brandName);
        brandNameTV.setText(brandName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
