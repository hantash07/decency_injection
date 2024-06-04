package com.hantash.dependancy_injection.screens.common.image_loader

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ImageLoader(
    private val activity: AppCompatActivity
) {

    fun loadImage(imageUrl: String, target: ImageView) {
        Glide
            .with(activity)
            .load(imageUrl)
            .centerCrop()
            .into(target)
    }
}