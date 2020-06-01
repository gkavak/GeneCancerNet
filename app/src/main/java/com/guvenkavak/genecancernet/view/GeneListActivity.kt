package com.guvenkavak.genecancernet.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.controller.CategoryController
import com.guvenkavak.genecancernet.model.Category
import com.guvenkavak.genecancernet.model.Gene
import kotlinx.android.synthetic.main.activity_gene_list.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class GeneListActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    val categoryGeneList = ArrayList<Gene>()
    val overAllGeneList = ArrayList<Gene>()
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

    }

    fun getCategoryData(category: Category) {
        categoryGeneList.clear()
        db.collection(Gene.Contract.toString())
            .whereGreaterThan(Gene.Contract.scoreByCategory, 0)
            .whereEqualTo(Gene.Contract.categoryId, category.id)
            .get().addOnCompleteListener(
                OnCompleteListener { qs ->
                    if (qs.isComplete) {
                        qs.result?.documents?.forEach() { item ->
                            val gene = item.toObject(Category::class.java) as Gene
                            categoryGeneList.add(gene)
                        }
                    }
                })
    }

    fun getOverallData(category: Category) {

    }


}