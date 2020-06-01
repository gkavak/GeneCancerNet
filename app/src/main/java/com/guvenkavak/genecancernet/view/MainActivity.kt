package com.guvenkavak.genecancernet.view

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    var adapter = CategoryAdapter(categoryList,CategoryController().insertColorClass())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getCategoryData()
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

