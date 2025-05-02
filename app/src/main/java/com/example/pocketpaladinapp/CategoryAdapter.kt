package com.example.pocketpaladinapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 Portions of this code were assisted or generated using OpenAI's ChatGPT
 (https://chat.openai.com/) to improve productivity, readability, and functionality.
 Final implementation decisions and code integration were made by the developer.
*/

class CategoryAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.categoryNameTextView)
        val extraText: TextView = itemView.findViewById(R.id.categoryExtraTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.nameText.text = category
        holder.extraText.text = "Tap to edit" // placeholder text
    }

    override fun getItemCount(): Int = categories.size
}