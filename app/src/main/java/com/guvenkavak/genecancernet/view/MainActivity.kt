package com.guvenkavak.genecancernet.view

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.adapter.CategoryAdapter
import com.guvenkavak.genecancernet.controller.CategoryController
import com.guvenkavak.genecancernet.model.Category
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    val categoryList = ArrayList<Category>()
    var adapter = CategoryAdapter(
        categoryList,
        CategoryController().insertColorClass()
    ) { category, position -> categoryItemClicked(category,position) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getCategoryData()
    }

    private fun categoryItemClicked(category: Category,position:Int) {
        //Toast.makeText(this, "Clicked: ${category.categoryName}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity, GeneListActivity::class.java)
        intent.putExtra("CategoryItem",category)
        intent.putExtra("PositionItem",position)
        startActivity(intent)
    }

    fun getCategoryData() {
        categoryList.clear()
        println(Category.Contract.toString())
        db.collection(Category.Contract.toString())
            .orderBy(Category.Contract.categoryNo, Query.Direction.ASCENDING)
            .orderBy(Category.Contract.categoryName, Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener(OnCompleteListener { qs ->
                println("Not completed yet")
                if (qs.isComplete) {
                    println("Completed!!")
                    qs.result?.documents?.forEach() { item ->
                        val category = item.toObject(Category::class.java) as Category
                        categoryList.add(category)
                        println(category.id)
                    }
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.INVISIBLE
                }
            }).addOnFailureListener(OnFailureListener { ex ->
                println(ex.message)
            })
    }
}

