package com.guvenkavak.genecancernet.view

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.adapter.CategoryAdapter
import com.guvenkavak.genecancernet.adapter.GeneCategoryAdapter
import com.guvenkavak.genecancernet.adapter.GeneOverallAdapter
import com.guvenkavak.genecancernet.controller.CategoryController
import com.guvenkavak.genecancernet.model.Category
import com.guvenkavak.genecancernet.model.Gene
import kotlinx.android.synthetic.main.activity_gene_list.*
import kotlinx.android.synthetic.main.activity_gene_list.progressBar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class GeneListActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val categoryGeneList = ArrayList<Gene>()
    private val overAllGeneList = ArrayList<Gene>()

    var adapterGeneCategory = GeneCategoryAdapter(
        categoryGeneList
    ) { gene -> geneItemClicked(gene) }

    var adapterGeneOverall = GeneOverallAdapter(
        overAllGeneList
    ) { gene -> geneItemClicked(gene) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gene_list)
        val categoryItem: Category = intent.getSerializableExtra("CategoryItem") as Category
        val positionItem: Int = intent.getIntExtra("PositionItem", 0)
        val someView: View = findViewById<TextView>(R.id.lbl_category_name)
        val root: View = someView.rootView
        root.setBackgroundColor(
            Color.parseColor(
                CategoryController().insertColorClass().get(positionItem).hexCode
            )
        )

        Toast.makeText(this, "Clicked: ${categoryItem.categoryName}", Toast.LENGTH_SHORT).show()
        lbl_category_name.text = categoryItem.categoryName + " ( ${categoryItem.categoryNo} )"
        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        df.setMaximumFractionDigits(340)
        lbl_accuaricy.text = df.format(categoryItem.accuracy1)

        progressBar.visibility = View.VISIBLE

        getCategoryData(categoryItem, adapterGeneCategory)
        getOverallData(categoryItem, adapterGeneOverall)

        gene_list_recycler_view_category.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gene_list_recycler_view_over_all.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        gene_list_recycler_view_over_all.adapter = adapterGeneOverall
        gene_list_recycler_view_category.adapter = adapterGeneCategory
    }

    private fun geneItemClicked(gene: Gene) {
        Toast.makeText(this, "Clicked: ${gene.geneCode}", Toast.LENGTH_SHORT).show()
        /*val intent = Intent(this@GeneListActivity, GeneDetailActivity::class.java)
        intent.putExtra("GeneItem",gene)
        startActivity(intent)*/
    }

    private fun getCategoryData(category: Category, adapter: GeneCategoryAdapter) {
        categoryGeneList.clear()
        db.collection(Gene.Contract.toString())
            .whereGreaterThan(Gene.Contract.scoreByCategory, 0)
            .whereEqualTo(Gene.Contract.categoryId, category.id)
            .orderBy(Gene.Contract.scoreByCategory, Query.Direction.DESCENDING)
            .get().addOnCompleteListener(
                OnCompleteListener { qs ->
                    if (qs.isComplete) {
                        println("document count:"+qs.result?.documents?.size)
                        qs.result?.documents?.forEach() { item ->
                            val gene = item.toObject(Gene::class.java) as Gene
                            categoryGeneList.add(gene)
                        }
                        progressBar.visibility = View.INVISIBLE
                        adapter.notifyDataSetChanged()
                    }
                })
    }

    private fun getOverallData(category: Category, adapter: GeneOverallAdapter) {
        overAllGeneList.clear()
        db.collection(Gene.Contract.toString())
            .whereGreaterThan(Gene.Contract.scoreOverAll, 0)
            .whereEqualTo(Gene.Contract.categoryId, category.id)
            .orderBy(Gene.Contract.scoreOverAll, Query.Direction.DESCENDING)
            .get().addOnCompleteListener(
                OnCompleteListener { qs ->
                    if (qs.isComplete) {
                        qs.result?.documents?.forEach() { item ->
                            val gene = item.toObject(Gene::class.java) as Gene
                            overAllGeneList.add(gene)
                        }
                        progressBar.visibility = View.INVISIBLE
                        adapter.notifyDataSetChanged()
                    }
                })
    }
}