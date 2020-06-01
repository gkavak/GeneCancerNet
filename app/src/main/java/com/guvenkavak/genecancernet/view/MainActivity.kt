package com.guvenkavak.genecancernet.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.adapter.CategoryAdapter
import com.guvenkavak.genecancernet.controller.CategoryController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val categoryController=CategoryController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility=View.VISIBLE
        categoryController.getCategoryList()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = CategoryAdapter(categoryController.categoryList)
        progressBar.visibility=View.INVISIBLE
    }
}