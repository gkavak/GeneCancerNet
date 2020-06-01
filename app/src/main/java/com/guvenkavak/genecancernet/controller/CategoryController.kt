package com.guvenkavak.genecancernet.controller

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.model.Category

class CategoryController {
    private val db = Firebase.firestore
    val categoryList= ArrayList<Category>()
    fun getCategoryList() {
        categoryList.clear()
        db.collection(Category.Contract.toString()).get()
            .addOnSuccessListener {
                OnSuccessListener<QuerySnapshot> { qs ->
                    qs.documents.forEach(){item->
                        val category=Category(item[Category.Contract.categoryName].toString()
                            ,item[Category.Contract.categoryNo].toString().toInt()
                            ,item[Category.Contract.accuracy1].toString().toFloat())
                        category.id =item[Category.Contract.id].toString()
                        category.description=item[Category.Contract.description].toString()
                        categoryList.add(category)
                        println("Category Added!!")
                    }
                }
            }
    }
}