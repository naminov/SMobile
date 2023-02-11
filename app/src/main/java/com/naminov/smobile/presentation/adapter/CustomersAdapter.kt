package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.databinding.CustomersItemBinding
import com.naminov.smobile.domain.model.Customer

class CustomersAdapter : RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    var items: List<Customer> = listOf()
        set(value) {
            val callback = DefaultDiffCallback(
                oldList = field,
                newList = value,
            )
            field = value
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = CustomersItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CustomerViewHolder(
        private val binding: CustomersItemBinding
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

        fun bind(customer: Customer) {
            binding.nameTv.text = customer.name
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(customer: Customer)
    }
}