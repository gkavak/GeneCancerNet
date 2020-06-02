package com.guvenkavak.genecancernet.view

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    var positionItem:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gene_list)
        val categoryItem: Category = intent.getSerializableExtra("CategoryItem") as Category
        positionItem= intent.getIntExtra("PositionItem", 0)
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
        //Toast.makeText(this, "Clicked: ${gene.geneCode}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@GeneListActivity, GeneDetailActivity::class.java)
        intent.putExtra("GeneItem",gene)
        intent.putExtra("PositionItem",positionItem)
        startActivity(intent)
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
                        adapter.notifyDataSetChanged()
                        gene_list_lbl_category.text="Category ( " + categoryGeneList.size + " )"
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
                        adapter.notifyDataSetChanged()
                        gene_list_lbl_overall.text="Overall ( " + overAllGeneList.size + " )"
                    }
                })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mnu_gene_list,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.mnu_share_over_all_content){
            var exportText:String=""
            for (gene in overAllGeneList)
            {
                exportText+="${gene.geneCode} \t ${gene.scoreOverAll}\n"
            }
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, exportText)
            sendIntent.type = "text/plain"

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }else if(item.itemId==R.id.mnu_share_category_content){

            var exportText:String=""

            for (gene in categoryGeneList)
            {
                exportText+="${gene.geneCode} \t ${gene.scoreByCategory}\n"
            }

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, exportText)
            sendIntent.type = "text/plain"

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        /*
        }else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_REQUEST_CODE)
        }*/
        return super.onOptionsItemSelected(item)
    }

}