package com.appsnado.momoxassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.appsnado.momoxassignment.R
import com.appsnado.momoxassignment.databinding.ItemImageBinding
import com.appsnado.momoxassignment.model.SearchResponse
import javax.inject.Inject

class ImageListAdapter @Inject constructor() : PagingDataAdapter<SearchResponse.Result,ImageListAdapter.ImageViewHolder>(diffUtil) {


    companion object{
         val diffUtil = object : DiffUtil.ItemCallback<SearchResponse.Result>() {
            override fun areItemsTheSame(oldItem: SearchResponse.Result, newItem: SearchResponse.Result): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchResponse.Result, newItem: SearchResponse.Result): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResponse.Result) {
            binding.apply {

                img.load(item.urls.regular) {
                    crossfade(true)
                    placeholder(R.drawable.image_placeholder)
                    scale(Scale.FILL)

                }
                tvImageName.text = item.user.name

                }
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if(item!=null){
                        onItemClickListener?.let { it1 -> it1(item) }
                    }

                }
            }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    private var onItemClickListener: ((SearchResponse.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (SearchResponse.Result) -> Unit) {
        onItemClickListener = listener
    }

}



