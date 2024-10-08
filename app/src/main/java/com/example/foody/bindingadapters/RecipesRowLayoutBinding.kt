package com.example.foody.bindingadapters

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foody.R
import com.example.foody.models.Recipe
import com.google.android.material.card.MaterialCardView

class RecipesRowLayoutBinding {
    companion object {

        @BindingAdapter("setHtmlParsedText")
        @JvmStatic
        fun setHtmlParsedText(textView: TextView, text: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString()
            } else {
                textView.text = Html.fromHtml(text).toString()
            }
        }

        @BindingAdapter("recipeOnClick")
        @JvmStatic
        fun setOnClickListener(materialCardView: MaterialCardView, recipe: Recipe) {

        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            Glide.with(imageView).load(url).transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }


        @BindingAdapter("setGreenColor")
        @JvmStatic
        fun setGreenColor(view: View, isGreen: Boolean) {
            if (isGreen) {
                when (view) {
                    is ImageView -> view.setColorFilter(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
    }
}