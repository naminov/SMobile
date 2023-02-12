package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.naminov.smobile.databinding.OrderDetailsProductItemBinding
import com.naminov.smobile.domain.model.OrderDetailsProduct

class OrderDetailsProductsAdapter :
    RecyclerView.Adapter<OrderDetailsProductsAdapter.OrderDetailsProductViewHolder>() {
    var onProductNumberChangeListener: OnProductNumberChangeListener? = null
    var onProductRemoveListener: OnProductRemoveListener? = null

    var items: List<OrderDetailsProduct> = listOf()
        set(value) {
            val callback = DefaultDiffCallback(
                oldList = field,
                newList = value,
            )
            field = value
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
        }

    var editable = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsProductViewHolder {
        val binding = OrderDetailsProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderDetailsProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsProductViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
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
                        if (adapterPosition == RecyclerView.NO_POSITION) {
                            return@setOnFocusChangeListener
                        }
                        val product = items[adapterPosition]
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

            binding.removeBtn.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                val product = items[adapterPosition]
                onProductRemoveListener?.onProductRemove(product)
            }
        }

        fun bind(orderDetailsProduct: OrderDetailsProduct) {
            binding.run {
                removeBtn.isVisible = editable
                nameEt.editText?.setText(orderDetailsProduct.product.name)
                numberEt.editText?.apply {
                    isFocusable = editable
                    isCursorVisible = editable
                    setTextIsSelectable(editable)
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
}