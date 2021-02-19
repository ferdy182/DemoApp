package com.fernandofgallego.demo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandofgallego.demo.data.DataItem
import com.fernandofgallego.demo.databinding.ItemRowBinding
import com.fernandofgallego.demo.databinding.SectionBinding

class DataAdapter: RecyclerView.Adapter<DataAdapter.BaseViewHolder>() {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_SECTION = 1
    }

    private var items: MutableList<DataItem> = mutableListOf()

    fun setItems(items: List<DataItem>) {
        this.items.clear() // better use DiffCallback
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == TYPE_ITEM) ItemViewHolder.from(parent) else SectionViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DataItem.Item -> TYPE_ITEM
            is DataItem.Section -> TYPE_SECTION
        }
    }


    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: DataItem)
    }


    class ItemViewHolder(private val itemRowBinding: ItemRowBinding) :
        BaseViewHolder(itemRowBinding.root) {
        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val binding =
                    ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding)
            }
        }

        override fun bind(item: DataItem) {
            item as DataItem.Item
            itemRowBinding.text.text = item.title
            itemRowBinding.text.isChecked = item.checked
        }
    }

    class SectionViewHolder(private val sectionBinding: SectionBinding) :
        BaseViewHolder(sectionBinding.root) {
        companion object {
            fun from(parent: ViewGroup): SectionViewHolder {
                val binding =
                    SectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SectionViewHolder(binding)
            }
        }

        override fun bind(item: DataItem) {
            item as DataItem.Section
            sectionBinding.text.text = item.title
        }
    }
}

