package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.databinding.ProductsItemBinding
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.presentation.extension.setOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

class ProductsAdapter(
    private val singleClickController: SingleClickController
) : PagingDataAdapter<Product, ProductsAdapter.ProductViewHolder>(
    DiffItemCallback()
) {
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class ProductViewHolder(
        private val binding: ProductsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            setListeners()
        }

        private fun setListeners() {
            binding.root.setOnSingleClickListener(singleClickController) {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnSingleClickListener
                }
                val item = getItem(bindingAdapterPosition) ?: return@setOnSingleClickListener
                onItemClickListener?.onItemClick(item)
            }
        }

        fun bind(product: Product) {
            binding.nameTv.text = product.name
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    class DiffItemCallback: DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}