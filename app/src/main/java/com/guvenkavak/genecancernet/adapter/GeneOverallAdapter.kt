package com.guvenkavak.genecancernet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.model.Gene


class GeneOverallAdapter(
    val geneArray: ArrayList<Gene>
    , private val clickListener: (Gene) -> Unit
) : RecyclerView.Adapter<GeneOverallAdapter.GeneOverallPostHolder>() {

    class GeneOverallPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: Gene, clickListener:(Gene) -> Unit) {
            itemView.findViewById<TextView>(R.id.gene_row_lbl_gene_code).text =
                item.geneCode
            itemView.findViewById<TextView>(R.id.gene_row_lbl_score).text =
                item.scoreOverAll.toString()
            itemView.setOnClickListener { clickListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneOverallPostHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gene_row, parent, false)
        return GeneOverallPostHolder(view)
    }

    override fun getItemCount(): Int {
        return geneArray.size
    }

    override fun onBindViewHolder(holder: GeneOverallPostHolder, position: Int) {
        holder.bindItem(geneArray[position],clickListener)
    }




}

