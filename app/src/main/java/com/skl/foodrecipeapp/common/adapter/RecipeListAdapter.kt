package com.skl.foodrecipeapp.common.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skl.foodrecipeapp.R
import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.databinding.RecipeListItemBinding

class RecipeListAdapter(private val listener: OnItemClick): ListAdapter<Recipe, RecipeListAdapter.RecipeListHolder>(COMPARATOR) {

    companion object{
        val COMPARATOR = object: DiffUtil.ItemCallback<Recipe>(){
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListHolder {
        val view = RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListHolder(view, listener)
    }

    override fun onBindViewHolder(holder: RecipeListHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    inner class RecipeListHolder(private val binding: RecipeListItemBinding, private val listener: RecipeListAdapter.OnItemClick)
        : RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener.onClick(getItem(position))
                }
            }
        }

        fun bind(recipe: Recipe){
            binding.apply {
                val uri = Uri.parse(recipe.image).buildUpon().scheme("https").build()

                Glide.with(itemView)
                    .load(uri)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_broken_image)
                    .thumbnail(.5f)
                    .into(recipeImage)

                recipeTitle.text = recipe.title
            }
        }
    }

    interface OnItemClick{
        fun onClick(recipe: Recipe)
    }
}
