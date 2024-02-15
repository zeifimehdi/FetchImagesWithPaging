package com.appsnado.momoxassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appsnado.momoxassignment.databinding.LoadStateFooterBinding

class ImageLoadstateAdapter(private val retry:() -> Unit): LoadStateAdapter<ImageLoadstateAdapter.LoadStateViewHolder>(){



    inner class LoadStateViewHolder(private  val loadStateFooterBinding: LoadStateFooterBinding):
            RecyclerView.ViewHolder(loadStateFooterBinding.root){

                init {
                    loadStateFooterBinding.buttonRetry.setOnClickListener {
                        retry.invoke()
                    }
                }

        fun bind(loadState: LoadState) {
            loadStateFooterBinding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }

        }
            }



    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}