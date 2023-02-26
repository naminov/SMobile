package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.naminov.smobile.databinding.OrderDetailsProductItemBinding
import com.naminov.smobile.domain.model.OrderDetailsProduct
import com.naminov.smobile.presentation.extension.setOnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

class OrderDetailsProductsAdapter(
    private val singleClickController: SingleClickController
) : RecyclerView.Adapter<OrderDetailsProductsAdapter.OrderDetailsProductViewHolder>() {
    var onProductNumberChangeListener: OnProductNumberChangeListener? = null
    var onProductRemoveListener: OnProductRemoveListener? = null

    private val differ = AsyncListDiffer(this, DiffItemCallback())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsProductViewHolder {
        val binding = OrderDetailsProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderDetailsProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsProductViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitData(data: List<OrderDetailsProduct>) {
        differ.submitList(data)
    }

    private fun getItem(position: Int): OrderDetailsProduct? {
        val items = differ.currentList
        if (position >= items.size) return null
        return items[position]
    }

    inner class OrderDetailsProductViewHolder(
        private val binding: OrderDetailsProductItemBinding
    ) : ViewHolder(binding.root) {
        init {
            setListeners()
        }

        private fun setListeners() {
            binding.numberEt.editText?.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        isCursorVisible = false
                        post {
                            isCursorVisible = true
                            setSelection(text.length)
                        }
                    } else {
                        if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                            return@setOnFocusChangeListener
                        }
                        val product = getItem(bindingAdapterPosition) ?: return@setOnFocusChangeListener
                        val number = if (text.isNotEmpty()) {
                            text.toString().toInt()
                        } else {
                            0
                        }
                        onProductNumberChangeListener?.onProductNumberChange(product, number)
                    }
                }

                doOnTextChanged { text, _, _, _ ->
                    if (text.isNullOrEmpty()) {
                        return@doOnTextChanged
                    }
                    val textOld = text.toString()
                    val textNew = textOld.toInt().toString()
                    if (textOld == textNew) {
                        return@doOnTextChanged
                    }
                    setText(textNew)
                    setSelection(textNew.length)
                }

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        clearFocus()
                    }
                    return@setOnEditorActionListener false
                }
            }

            binding.removeBtn.setOnSingleClickListener(singleClickController) {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnSingleClickListener
                }
                val product = getItem(bindingAdapterPosition) ?: return@setOnSingleClickListener
                onProductRemoveListener?.onProductRemove(product)
            }
        }

        fun bind(orderDetailsProduct: OrderDetailsProduct) {
            binding.run {
                removeBtn.isVisible = orderDetailsProduct.editable
                nameEt.editText?.setText(orderDetailsProduct.product.name)
                numberEt.editText?.apply {
                    isFocusable = orderDetailsProduct.editable
                    isCursorVisible = orderDetailsProduct.editable
                    setTextIsSelectable(orderDetailsProduct.editable)
                    setText(orderDetailsProduct.number.toString())
                }
                priceEt.editText?.setText(orderDetailsProduct.price)
                sumEt.editText?.setText(orderDetailsProduct.sum)
            }
        }
    }

    fun interface OnProductNumberChangeListener {
        fun onProductNumberChange(product: OrderDetailsProduct, number: Int)
    }

    fun interface OnProductRemoveListener {
        fun onProductRemove(product: OrderDetailsProduct)
    }

    class DiffItemCallback: DiffUtil.ItemCallback<OrderDetailsProduct>() {
        override fun areItemsTheSame(
            oldItem: OrderDetailsProduct,
            newItem: OrderDetailsProduct
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderDetailsProduct,
            newItem: OrderDetailsProduct
        ): Boolean {
            return oldItem == newItem
        }
    }
}