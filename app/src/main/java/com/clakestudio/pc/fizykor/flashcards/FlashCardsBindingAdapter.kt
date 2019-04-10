package com.clakestudio.pc.fizykor.flashcards

import androidx.databinding.BindingAdapter
import android.view.View

object FlashCardsBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(v: View, isVisible: Boolean) {
        v.visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
    }
}