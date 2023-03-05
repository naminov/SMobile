package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.R
import com.naminov.smobile.databinding.OrderHistoryItemBinding
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.presentation.extension.setOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

class OrderHistoryAdapter(
    private val singleClickController: SingleClickController
) : PagingDataAdapter<OrderHistory, OrderHistoryAdapter.OrderHistoryViewHolder>(
    DiffItemCallback()
) {
    var onItemClickListener: OnItemClickListener? = null
    var onCopyClickListener: OnCopyClickListener? = null
    var onRemoveClickListener: OnRemoveClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = OrderHistoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class OrderHistoryViewHolder(
        private val binding: OrderHistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            setListeners()
        }

        private fun setListeners() {
            binding.root.setOnSingleClickListener(singleClickController) {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION){
                    return@setOnSingleClickListener
                }
                val item = getItem(bindingAdapterPosition) ?: return@setOnSingleClickListener
                onItemClickListener?.onItemClick(item)
            }

            binding.moreBtn.setOnSingleClickListener(singleClickController) {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION){
                    return@setOnSingleClickListener
                }
                val item = getItem(bindingAdapterPosition) ?: return@setOnSingleClickListener

                val popup = PopupMenu(it.context, it)
                popup.menuInflater.inflate(R.menu.order_history_more, popup.menu)
                popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                    when (menuItem.itemId) {
                        R.id.copy -> onCopyClickListener?.onCopyClick(item)
                        R.id.remove -> onRemoveClickListener?.onRemoveClick(item)
                    }
                    return@setOnMenuItemClickListener true
                }
                popup.show()
            }
        }

        fun bind(order: OrderHistory) {
            binding.run {
                numberTv.text = order.number
                dateTv.text = order.date
                customerTv.text = order.customer
                sumTv.text = order.sum
                completedIv.isVisible = order.completed
                noPaymentIv.isVisible = !order.payment
                noDocumentsIv.isVisible = !order.documents
            }
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(order: OrderHistory)
    }

    fun interface OnCopyClickListener {
        fun onCopyClick(order: OrderHistory)
    }

    fun interface OnRemoveClickListener {
        fun onRemoveClick(order: OrderHistory)
    }

    class DiffItemCallback: DiffUtil.ItemCallback<OrderHistory>() {
        override fun areItemsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem == newItem
        }
    }
}