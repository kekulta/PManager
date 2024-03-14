package com.kekulta.pmanager

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SitesAdapter : ListAdapter<SiteVo, SitesAdapter.SiteViewHolder>(DIFF_CALLBACK) {
    class SiteViewHolder(private val view: SiteView) : RecyclerView.ViewHolder(view) {
        fun bind(vo: SiteVo) {
            view.bind(vo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(SiteView(parent.context))
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SiteVo>() {
            override fun areItemsTheSame(oldItem: SiteVo, newItem: SiteVo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SiteVo, newItem: SiteVo): Boolean {
                return oldItem == newItem
            }
        }
    }
}