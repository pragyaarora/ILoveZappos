package com.example.pragya.ilovezappos;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by pragya on 2/4/2017.
 */

public class ZapposItem extends BaseObservable {
    @Bindable
   public String brandName;

    String thumbnailImageUrl;

    String productId;

    String originalPrice;

    String styleId;

    String colorId;

    @Bindable
    public String price;

    String percentOff;

    String productUrl;

    @Bindable
    public String productName;

    boolean pressed = false;

}
