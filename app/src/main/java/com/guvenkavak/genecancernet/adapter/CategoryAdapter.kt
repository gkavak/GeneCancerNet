package com.guvenkavak.genecancernet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.model.Category

class CategoryAdapter(val categoryArray:ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.PostHolder>() {

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val category_list_row_lbl_category_name: TextView = itemView.findViewById<TextView>(R.id.category_list_row_lbl_category_name)
        val category_list_row_lbl_accuracy: TextView = itemView.findViewById<TextView>(R.id.category_list_row_lbl_accuracy)

        fun bindItems(item: Category) {
            category_list_row_lbl_category_name.text= item.categoryName
            category_list_row_lbl_accuracy.text= item.accuracy1.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_row, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.bindItems(categoryArray[position])
    }
}