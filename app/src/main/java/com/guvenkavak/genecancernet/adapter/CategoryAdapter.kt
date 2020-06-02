package com.guvenkavak.genecancernet.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.model.Category
import com.guvenkavak.genecancernet.model.ColorClass
import kotlinx.android.synthetic.main.activity_gene_list.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class CategoryAdapter(
    val categoryArray: ArrayList<Category>
    , val colorArray: ArrayList<ColorClass>
    , private val clickListener: (Category,Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_list_row, parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.parseColor(colorArray.get(position).hexCode))
        holder.bindItems(categoryArray[position],position,clickListener)
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category_list_row_lbl_category_name: TextView =
            itemView.findViewById<TextView>(R.id.category_list_row_lbl_category_name)
        val category_list_row_lbl_accuracy: TextView =
            itemView.findViewById<TextView>(R.id.category_list_row_lbl_accuracy)
        val category_list_row_lbl_category_count: TextView =
            itemView.findViewById<TextView>(R.id.category_list_row_lbl_category_count)

        fun bindItems(item: Category, position:Int,clickListener: (Category,Int) -> Unit) {
            category_list_row_lbl_category_name.text = item.categoryName
            val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
            df.maximumFractionDigits = 340
            category_list_row_lbl_accuracy.text = df.format(item.accuracy1)
            category_list_row_lbl_category_count.text = "Category No ( ${item.categoryNo} )"
            itemView.setOnClickListener { clickListener(item,position) }
        }
    }
}