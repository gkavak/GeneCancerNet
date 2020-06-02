package com.guvenkavak.genecancernet.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.guvenkavak.genecancernet.R
import com.guvenkavak.genecancernet.controller.CategoryController
import com.guvenkavak.genecancernet.model.Gene
import kotlinx.android.synthetic.main.activity_gene_detail.*
import org.jsoup.Jsoup
import java.lang.Exception

class GeneDetailActivity : AppCompatActivity() {
    var geneItem: Gene =Gene()
    var positionItem:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gene_detail)
        geneItem=intent.getSerializableExtra("GeneItem") as Gene
        positionItem= intent.getIntExtra("PositionItem", 0)
        getWebsite(geneItem.geneCode)
        activity_gene_detail_btn_navigate.visibility=View.INVISIBLE
        var spinner:ProgressBar=findViewById(R.id.progressBar1)
        spinner.setVisibility(View.VISIBLE);
        val someView: View = findViewById<TextView>(R.id.activity_gene_detail_gene_name)
        val root: View = someView.rootView
        root.setBackgroundColor(
            Color.parseColor(
                CategoryController().insertColorClass().get(positionItem).hexCode
            )
        )
    }
    fun navigateGene(view:View){

        var sUrl= "https://www.ncbi.nlm.nih.gov/search/all/?term=${geneItem.geneCode}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sUrl))
        startActivity(browserIntent)
    }
    private fun getWebsite(sCode:String?) {
        Thread(Runnable {
            var geneID=""
            var title=""
            var lineage=""
            var summary=""
            var organizm=""
            var geneType=""
            var primary_source=""
            try {
                val builder = StringBuilder()
                var sUrl= "https://www.ncbi.nlm.nih.gov/search/all/?term=$sCode"
                val doc = Jsoup.connect(sUrl).get()
                val links = doc.select("ul[class=ncbi-inline-list]")[0]
                geneID=links.select("li").text().split(":")[1]
                title=doc.select("#gene_title")[0].text()
                val m_strFilePath=
                    "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=gene&id=$geneID&format=xml"
                val xmlDoc = Jsoup.connect(m_strFilePath).get()
                lineage=xmlDoc.getElementsByTag("OrgName_lineage")[0].text()
                summary=xmlDoc.getElementsByTag("Entrezgene_summary")[0].text()
                organizm=xmlDoc.getElementsByTag("Org-ref_taxname")[0].text()
                geneType=xmlDoc.getElementsByTag("Entrezgene_type")[0].attr("value").toString()
                primary_source=xmlDoc.getElementsByTag("Entrezgene_unique-keys")[0].getElementsByTag("Object-id_str")[0].text()
                /*
                XmlNodeList nodeListPrimarySource = xmlDoc.GetElementsByTagName("Entrezgene_unique-keys");
                var primarySource1 = nodeListPrimarySource[0].SelectNodes(".//Object-id_str");
                if (primarySource1.Count > 0)
                    ((Gene)row.DataBoundItem).PrimarySource = primarySource1[0].InnerText;
                */

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            runOnUiThread {
                try {
                    val lblGeneName=findViewById<TextView>(R.id.activity_gene_detail_gene_name)
                    val lblGeneCode=findViewById<TextView>(R.id.activity_gene_detail_gene_code)
                    val lblGeneId=findViewById<TextView>(R.id.activity_gene_detail_gene_id)
                    val lblGeneSummary=findViewById<TextView>(R.id.activity_gene_detail_gene_summary)
                    lblGeneSummary.text="Summary : " + summary
                    lblGeneName.text="Title : " + title
                    lblGeneId.text="Gene Id: " + geneID
                    activity_gene_detail_gene_lineage.text="Lineage : " + lineage
                    lblGeneCode.text="Code : " + geneItem.geneCode
                    activity_gene_detail_gene_organism.text="Organizm : " + organizm
                    activity_gene_detail_gene_gene_type.text="Gene Type : "+ geneType
                    activity_gene_detail_gene_primary_source.text="Primary Source : " + primary_source

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                activity_gene_detail_btn_navigate.visibility= View.VISIBLE
                var spinner: ProgressBar =findViewById(R.id.progressBar1)
                spinner.setVisibility(View.GONE);
            }
        }).start()
    }


}