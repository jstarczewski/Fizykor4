package com.clakestudio.pc.fizykor.equations

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.databinding.MultiEquationBinding
import com.jstarczewski.pc.mathview.src.MathView

class EquationsAdapter(private var equations: ArrayList<List<Equation>>) : androidx.recyclerview.widget.RecyclerView.Adapter<EquationsAdapter.ViewHolder>() {

    class ViewHolder(binding: MultiEquationBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        /**
         * This Multi Equation Binding pattern as a RecyclerView object is based on Spaghetti Code design pattern and was the only
         * solution that I came up with that guarantees enough scrolling performance and reduces
         * memory leak that happens because of WebViews used to display equations.
         * */

        private val textViews = arrayListOf(binding.tvTitle1, binding.tvTitle2, binding.tvTitle3, binding.tvTitle4)
        private val mathViews = arrayListOf(binding.mvEquation1, binding.mvEquation2, binding.mvEquation3, binding.mvEquation4)
        private val cardViews = arrayListOf(binding.cvEquation1, binding.cvEquation2, binding.cvEquation3, binding.cvEquation4)

        fun bind(equationsList: List<Equation>) {
            for (x in 0..3)
                updateData(textViews[x], mathViews[x], cardViews[x], equationsList[x])
        }

        private fun updateData(tv: TextView, mv: MathView, cv: androidx.cardview.widget.CardView, equation: Equation) {
            if (equation.title.isEmpty()) cv.visibility = View.GONE
            else tv.text = equation.title; mv.text = equation.equation
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationsAdapter.ViewHolder {

        val binding: MultiEquationBinding
        val inflater = LayoutInflater.from(parent.context)
        binding = MultiEquationBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return equations.size
    }

    override fun onBindViewHolder(holder: EquationsAdapter.ViewHolder, position: Int) {
        holder.bind(equations[position])
    }


    fun replaceData(equations: ArrayList<List<Equation>>) = setEquations(equations)

    private fun setEquations(equations: ArrayList<List<Equation>>) {
        this.equations = equations
        notifyDataSetChanged()
    }

}