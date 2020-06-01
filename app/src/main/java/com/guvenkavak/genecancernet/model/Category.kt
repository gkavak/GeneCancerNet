package com.guvenkavak.genecancernet.model

import java.io.Serializable

class Category (private val _categoryName:String,private val _categoryNo:Int,private val _accuaricy:Float):
    Serializable {
    var id:String=""
    var categoryName:String=_categoryName
    var accuracy1:Float=_accuaricy
    var categoryNo:Int=_categoryNo
    var description:String=""
    var geneCount:Int =0

    override fun toString(): String {
        return this.categoryName
    }
    object Contract
    {
        var id="id"
        var categoryName="categoryName"
        var accuracy1="accuracy1"
        var categoryNo="categoryNo"
        var description="description"
        var geneCount="geneCount"
        override fun toString(): String {
            return "categories"
        }
    }
}