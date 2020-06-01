package com.guvenkavak.genecancernet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.model.Category
import com.guvenkavak.genecancernet.model.ColorClass
import com.guvenkavak.genecancernet.model.Gene


class GeneCategoryAdapter(
    val geneArray: ArrayList<Gene>
    , private val clickListener: (Gene) -> Unit
) : RecyclerView.Adapter<GeneCategoryAdapter.GeneCategoryPostHolder>() {

    class GeneCategoryPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: Gene,clickListener:(Gene) -> Unit) {
            itemView.findViewById<TextView>(R.id.gene_row_lbl_gene_code).text =
                item.geneCode
            itemView.findViewById<TextView>(R.id.gene_row_lbl_score).text =
                item.scoreByCategory.toString()
            itemView.setOnClickListener { clickListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneCategoryPostHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gene_row, parent, false)
        return GeneCategoryPostHolder(view)
    }

    override fun getItemCount(): Int {
        return geneArray.size
    }

    override fun onBindViewHolder(holder: GeneCategoryPostHolder, position: Int) {
        holder.bindItem(geneArray[position],clickListener)
    }
}