package com.guvenkavak.genecancernet.model

import java.io.Serializable

data class Gene (private val _geneCode:String): Serializable {
    var id:Int = 0
    var geneId:String=""
    var categoryNo:Int=0
    var categoryId:Int=0
    var geneSumName:String=""
    var geneName:String=""
    var summary:String=""
    var description:String=""
    var geneCode:String=""
    var primarySource:String=""
    var geneType:String=""
    var organism:String=""
    var lineage:String=""
    var weight:Float=0.0f
    var scoreOverAll:Float=0.0f
    var scoreByCategory:Float=0.0f

    object Contract
    {
        var id="id"
        var geneId="geneId"
        var categoryNo="categoryNo"
        var categoryId="categoryId"
        var geneSumName="geneSumName"
        var geneName="geneName"
        var summary="summary"
        var description="description"
        var geneCode="geneCode"
        var primarySource="primarySource"
        var geneType="geneType"
        var organism="organism"
        var lineage="lineage"
        var weight="weight"
        var scoreOverAll="scoreOverAll"
        var scoreByCategory="scoreByCategory"
        override fun toString(): String {
            return "genes"
        }
    }

    constructor(_geneCode: String,_scoreOverAll:Float) : this(_geneCode) {
        this.geneCode=_geneCode
        this.scoreOverAll=_scoreOverAll
    }
    constructor(_geneCode: String,_scoreOverAll:Float,_scoreByCategory:Float) : this(_geneCode) {
        this.geneCode=_geneCode
        this.scoreOverAll=_scoreOverAll
        this.scoreByCategory=_scoreByCategory
    }

    override fun toString(): String {
        return "${this.geneCode}\t${this.scoreOverAll}"
    }

}