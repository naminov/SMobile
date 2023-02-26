package com.naminov.smobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.naminov.smobile.databinding.LoadingErrorItemBinding
import com.naminov.smobile.databinding.LoadingProgressItemBinding

class LoadingAdapter : LoadStateAdapter<ViewHolder>() {
    override fun getStateViewType(loadState: LoadState): Int {
        return when (loadState) {
            is LoadState.NotLoading -> error("Not supported")
            LoadState.Loading -> PROGRESS
            is LoadState.Error -> ERROR
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return when (loadState) {
            is LoadState.NotLoading -> error("Not supported")
            LoadState.Loading -> {
                val binding = LoadingProgressItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                ProgressViewHolder(binding)
            }
            is LoadState.Error -> {
                val binding = LoadingErrorItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                ErrorViewHolder(binding)
            }
        }
    }

    inner class ProgressViewHolder(
        binding: LoadingProgressItemBinding
    ) : ViewHolder(binding.root)

    inner class ErrorViewHolder(
        binding: LoadingErrorItemBinding
    ) : ViewHolder(binding.root)

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }
}