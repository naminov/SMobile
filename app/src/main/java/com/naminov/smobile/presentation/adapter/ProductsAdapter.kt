package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naminov.smobile.databinding.ProductsItemBinding
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.presentation.extension.setOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

class ProductsAdapter(
    private val singleClickController: SingleClickController
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    var items: List<Product> = listOf()
        set(value) {
            val callback = DefaultDiffCallback(
                oldList = field,
                newList = value,
            )
            field = value
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ProductViewHolder(
        private val binding: ProductsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            setListeners()
        }

        private fun setListeners() {
            binding.root.setOnSingleClickListener(singleClickController) {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnSingleClickListener
                }
                val item = items[adapterPosition]
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
}