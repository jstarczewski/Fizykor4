package com.clakestudio.pc.fizykor.equations

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.clakestudio.pc.fizykor.data.Equation

object EquationsRecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("app:equations")
    fun setEquations(recyclerView: androidx.recyclerview.widget.RecyclerView, equations: ArrayList<Equation>) {
        with(recyclerView.adapter as EquationsAdapter) {

            if (!equations.isEmpty()) {
                val smallerLists: List<List<Equation>> = equations.chunked(4)
                while (smallerLists[smallerLists.size - 1].size < 4)
                    (smallerLists[smallerLists.size - 1] as ArrayList<Equation>).add(Equation("", "", ""))
                replaceData(smallerLists as ArrayList<List<Equation>>)
            }

        }
    }
}