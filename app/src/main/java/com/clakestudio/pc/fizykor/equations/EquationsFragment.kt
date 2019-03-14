package com.clakestudio.pc.fizykor.equations

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.databinding.FragmentEquationsBinding

class EquationsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentEquationsBinding
    private lateinit var equationsAdapter: EquationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentEquationsBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as EquationsActivity).obtainViewModel()
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start()

    }

    private fun setupFab() {
        activity!!.findViewById<FloatingActionButton>(R.id.fab).run {
            setOnClickListener {
                viewDataBinding.viewmodel!!.openFlashCards()
            }
            (layoutParams as CoordinatorLayout.LayoutParams).anchorId = View.NO_ID
        }
    }


    private fun setupRecyclerView() {
        if (viewDataBinding.viewmodel != null) {
            viewDataBinding.rvEquations.apply {
                equationsAdapter = EquationsAdapter(arrayListOf())
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                viewDataBinding.rvEquations.adapter = equationsAdapter
            }
        }
    }

    companion object {
        fun newInstance() = EquationsFragment()
    }


}

