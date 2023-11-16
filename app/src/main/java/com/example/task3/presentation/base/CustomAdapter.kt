package com.example.task3.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class CustomAdapter<T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup, Boolean) -> T,
    private val count: Int,
    private val bind: (T, Int) -> Unit
) : RecyclerView.Adapter<CustomAdapter<T>.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int = count

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) = holder.bind(position)

    inner class CustomViewHolder(private val binding: T) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = bind(binding, position)
    }
}