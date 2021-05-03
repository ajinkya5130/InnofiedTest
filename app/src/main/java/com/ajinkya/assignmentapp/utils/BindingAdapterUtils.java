package com.ajinkya.assignmentapp.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.ajinkya.assignmentapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class BindingAdapterUtils {
    @BindingAdapter({"loadImage"})
    public static void loadImages(final ImageView view, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(view.getContext()).load(imageUrl).placeholder(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(options).into(view);
    }
}
