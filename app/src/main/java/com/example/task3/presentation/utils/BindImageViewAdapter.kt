package com.example.task3.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

private val shimmer = Shimmer.AlphaHighlightBuilder()
    .setBaseAlpha(1f)
    .setHighlightAlpha(1f)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setIntensity(0.3f)
    .setDropoff(0.7f)
    .setAutoStart(true)
    .build()

private val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
}

@BindingAdapter("imageUrl")
fun ImageView.bindUrlImage(imageUrl: String?) =
    Glide
        .with(this.context)
        .load(imageUrl)
        .placeholder(shimmerDrawable)
        .error(shimmerDrawable)
        .into(this)