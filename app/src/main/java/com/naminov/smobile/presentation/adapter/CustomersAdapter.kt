package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.databinding.CustomersItemBinding
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.presentation.extension.setOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

class CustomersAdapter(
    private val singleClickController: SingleClickController
) : PagingDataAdapter<Customer, CustomersAdapter.CustomerViewHolder>(
    DiffItemCallback()
) {
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = CustomersItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class CustomerViewHolder(
        private val binding: CustomersItemBinding
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
        }

        fun bind(customer: Customer) {
            binding.nameTv.text = customer.name
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(customer: Customer)
    }

    class DiffItemCallback: DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }
}