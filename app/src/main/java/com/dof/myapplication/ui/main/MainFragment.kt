package com.dof.myapplication.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dof.myapplication.R
import com.dof.myapplication.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
//        val binding: DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.main_fragment,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.vm = viewModel
        viewModel.amount.set("test")
        test(1)
        test(viewModel)
        test("test")

        var lastName : String? = null
        val l = lastName?.length
        println(l)

        lastName.let { println(it) }


        val originalList = listOf(1, 2, 3, 4, 5, null, 7, 8, 9, 10)

        for (i in originalList) {
            println(i)
        }
    }

    fun test(x: Any) {
        when (x) {
            is Int -> println("INT")
            is String -> println("STRING")
            else -> println("TAMERE")
        }
    }
}