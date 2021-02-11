package com.dhruv.apps.historyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.databinding.RawHistoryItemBinding
import com.dhruv.apps.historyapp.databinding.RawUserBinding
import com.dhruv.apps.historyapp.models.GitUser
import com.dhruv.apps.historyapp.models.HistoryData

class GitUserSelectedAdapter(var mList: ArrayList<GitUser.GitUserItem> = ArrayList()) :
    RecyclerView.Adapter<GitUserSelectedAdapter.ViewHolder>() {

    fun setData(list: ArrayList<GitUser.GitUserItem>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: RawUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.raw_user,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.switchOn.visibility = View.GONE
        holder.itemBinding.data = mList[position]
    }

    class ViewHolder(var itemBinding: RawUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}