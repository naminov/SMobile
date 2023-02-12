package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.databinding.OrderHistoryItemBinding
import com.naminov.smobile.domain.model.OrderHistory

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    var items: List<OrderHistory> = listOf()
        set(value) {
            val callback = DefaultDiffCallback(
                oldList = field,
                newList = value,
            )
            field = value
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = OrderHistoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class OrderHistoryViewHolder(
        private val binding: OrderHistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            setListeners()
        }

        private fun setListeners() {
            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION){
                    return@setOnClickListener
                }
                val item = items[adapterPosition]
                onItemClickListener?.onItemClick(item)
            }
        }

        fun bind(order: OrderHistory) {
            binding.run {
                numberTv.text = order.number
                dateTv.text = order.date
                customerTv.text = order.customer
                sumTv.text = order.sum
            }
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(order: OrderHistory)
    }
}