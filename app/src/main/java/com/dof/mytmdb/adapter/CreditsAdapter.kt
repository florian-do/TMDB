package com.dof.mytmdb.adapter

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dof.mytmdb.R
import com.dof.mytmdb.databinding.AdapterCreditsBinding
import com.dof.mytmdb.service.model.Crew

class CreditsAdapter : RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {

    private var items : List<Crew> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : AdapterCreditsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_credits,
                parent,
                false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = items.get(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items : List<Crew>?) {
        this.items = items!!
    }

    class ViewHolder(val binding: AdapterCreditsBinding) : RecyclerView.ViewHolder(binding.root)
}