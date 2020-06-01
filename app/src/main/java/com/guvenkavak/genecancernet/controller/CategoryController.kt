package com.guvenkavak.genecancernet.controller

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.guvenkavak.genecancernet.model.Category
import com.guvenkavak.genecancernet.model.ColorClass

class CategoryController {
    val colorClassArrayList=ArrayList<ColorClass>()
    fun insertColorClass() :ArrayList<ColorClass> {
        //Cat No : 1
        colorClassArrayList.clear()
        colorClassArrayList.add(ColorClass(1,0,"#3b5998"))
        colorClassArrayList.add(ColorClass(1,1,"#8b9dc3"))
        colorClassArrayList.add(ColorClass(1,2,"#dfe3ee"))
        colorClassArrayList.add(ColorClass(1,3,"#f7f7f7"))
        colorClassArrayList.add(ColorClass(1,4,"#ffffff"))

        //Cat No : 2
        colorClassArrayList.add(ColorClass(2,0,"#ffe0bd"))
        colorClassArrayList.add(ColorClass(2,1,"#ffcd94"))
        colorClassArrayList.add(ColorClass(2,2,"#eac086"))
        colorClassArrayList.add(ColorClass(2,3,"#ffad60"))

        //Cat No : 3
        colorClassArrayList.add(ColorClass(3,0,"#999999"))
        colorClassArrayList.add(ColorClass(3,1,"#777777"))
        colorClassArrayList.add(ColorClass(3,2,"#555555"))

        //Cat No : 4
        colorClassArrayList.add(ColorClass(4,0,"#b2d8d8"))
        colorClassArrayList.add(ColorClass(4,1,"#66b2b2"))

        //Cat No : 5
        colorClassArrayList.add(ColorClass(5,0,"#eec1ad"))
        colorClassArrayList.add(ColorClass(5,1,"#dbac98"))

        //Cat No : 6
        colorClassArrayList.add(ColorClass(6,0,"#aa6f73"))
        colorClassArrayList.add(ColorClass(6,1,"#eea990"))

        //Cat No : 7
        colorClassArrayList.add(ColorClass(7,0,"#b3ecec"))
        colorClassArrayList.add(ColorClass(7,1,"#89ecda"))
        colorClassArrayList.add(ColorClass(7,2,"#43e8d8"))

        //Cat No : 8
        colorClassArrayList.add(ColorClass(8,0,"#ff0000"))
        colorClassArrayList.add(ColorClass(8,1,"#bf0000"))

        //Cat No : 9
        colorClassArrayList.add(ColorClass(9,0,"#a3c1ad"))
        colorClassArrayList.add(ColorClass(9,1,"#a0d6b4"))
        colorClassArrayList.add(ColorClass(9,1,"#5f9ea0"))
        colorClassArrayList.add(ColorClass(9,1,"#317873"))

        //Cat No : 10
        colorClassArrayList.add(ColorClass(10,0,"#107dac"))
        colorClassArrayList.add(ColorClass(10,1,"#189ad3"))

        return colorClassArrayList
    }
}