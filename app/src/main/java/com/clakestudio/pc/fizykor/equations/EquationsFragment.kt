package com.clakestudio.pc.fizykor.equations

import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.databinding.FragmentEquationsBinding

class EquationsFragment : androidx.fragment.app.Fragment() {

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
            (layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams).anchorId = View.NO_ID
        }
    }


    private fun setupRecyclerView() {
        if (viewDataBinding.viewmodel != null) {
            viewDataBinding.rvEquations.apply {
                equationsAdapter = EquationsAdapter(arrayListOf())
                setHasFixedSize(true)
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                viewDataBinding.rvEquations.adapter = equationsAdapter
            }
        }
    }

    companion object {
        fun newInstance() = EquationsFragment()
    }

}

