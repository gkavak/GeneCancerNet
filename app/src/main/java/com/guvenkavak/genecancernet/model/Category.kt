package com.guvenkavak.genecancernet.model

import java.io.Serializable

class Category ():
    Serializable {
    constructor(_categoryName:String, _categoryNo:Int, _accuracy:Float) : this() {
        categoryName=_categoryName
        categoryNo=_categoryNo
        accuracy1=_accuracy
    }
    var id:String=""
    var categoryName:String=""
    var accuracy1:Float = 0.0f
    var categoryNo:Int=0
    var description:String=""

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