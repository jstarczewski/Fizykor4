package com.clakestudio.pc.fizykor.flashcards

import android.databinding.BindingAdapter
import android.view.View

object FlashCardsBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(v: View, isVisible: Boolean) {
        v.visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
    }
}