@file:JvmName("ItemsAdapter")

package com.example.espl.sampleappkotlin.giphysearch

import android.content.Context
import android.content.res.Resources
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.provider.Settings.Global.getString
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.espl.sampleappkotlin.MainApplication
import com.example.espl.sampleappkotlin.R
import com.example.espl.sampleappkotlin.databinding.ItemViewBinding
import com.example.espl.sampleappkotlin.models.DataItem
import io.objectbox.BoxStore.context

class DataItemsAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<DataItemsAdapter.ViewHolder>() {

    private var items: List<DataItem> = emptyList()
    val context: Context? = mainActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && items.size > position) {
            holder.binding.item1=items[position].images!!.get( (context as MainActivity).resources.getString(R.string.w480_still))
            holder.bind(items[position])

            holder.itemView.setOnClickListener {
                (context as MainActivity).onClickItem(position)
            }
        }
    }

    fun update(items: List<DataItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(items: List<DataItem>) {
            val adapter = adapter as DataItemsAdapter
            adapter.update(items)
        }
    }

    fun getItemdata(pos: Int): DataItem {
        return items.get(pos)
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
        private val parent: ViewGroup,
         val binding: ItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
             R.layout.item_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {

        fun bind(item: DataItem) {
            binding.item1 = item.images!!.get(parent.context.resources.getString(R.string.w480_still))
        }
    }

}