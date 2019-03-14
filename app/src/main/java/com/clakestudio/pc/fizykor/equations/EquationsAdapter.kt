package com.clakestudio.pc.fizykor.equations

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.databinding.MultiEquationBinding
import com.jstarczewski.pc.mathview.src.MathView

class EquationsAdapter(private var equations: ArrayList<List<Equation>>) : RecyclerView.Adapter<EquationsAdapter.ViewHolder>() {


    class ViewHolder(private val binding: com.clakestudio.pc.fizykor.databinding.MultiEquationBinding) : RecyclerView.ViewHolder(binding.root) {

        val updateData = { tv: TextView, mv: MathView, cv: CardView, equation: Equation ->
            (if (equation.title == "") cv.visibility = View.GONE else { tv.text = equation.title; mv.text = equation.equation }) }

        private val textViews = arrayListOf(binding.tvTitle1, binding.tvTitle2, binding.tvTitle3, binding.tvTitle4)
        private val mathViews = arrayListOf(binding.mvEquation1, binding.mvEquation2, binding.mvEquation3, binding.mvEquation4)
        private val cardViews = arrayListOf(binding.cvEquation1, binding.cvEquation2, binding.cvEquation3, binding.cvEquation4)

        fun bind(equationsList: List<Equation>) {
            for (x in 0..3)
                updateData(textViews[x], mathViews[x], cardViews[x], equationsList[x])
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