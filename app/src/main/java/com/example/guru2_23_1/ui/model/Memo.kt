package com.example.guru2_23_1.ui.model

import java.io.Serializable

class Memo (id:Int, title:String, memo:String, datetime: Int) : Serializable {

    val id = id;
    val title = title
    val memo = memo
    val datetime = datetime
}