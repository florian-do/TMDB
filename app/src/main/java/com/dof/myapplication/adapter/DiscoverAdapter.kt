package com.dof.myapplication.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dof.myapplication.Const
import com.dof.myapplication.R
import com.dof.myapplication.databinding.AdapterDiscoverBinding
import com.dof.myapplication.module.GlideApp
import com.dof.myapplication.service.model.Result

class DiscoverAdapter(val context : Context, val items: List<Result>?) : RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : AdapterDiscoverBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_discover,
                parent,
                false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(context)
                .load(Const.Companion.URL_PHOTO+items?.get(position)?.poster_path)
                .fitCenter()
                .into(holder.binding.cover)
    }

    class ViewHolder(val binding: AdapterDiscoverBinding) : RecyclerView.ViewHolder(binding.root)
}