package com.example.myapplication.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.R

fun getProgressDrawable(context:Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

}

@BindingAdapter("android:imageUrl")
fun loadImage(view:ImageView, uri : String){
    view.loadImage(uri, getProgressDrawable(view.context))
}

@BindingAdapter("android:background")
fun setBackground(view: LinearLayout, uri:String){
    Glide.with(view)
        .asBitmap()
        .load(uri)
        .into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource)
                    .generate(){pallete->
                        val intColor = pallete?.lightMutedSwatch?.rgb?:0
                        view.setBackgroundColor(intColor)
                    }
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
}