package com.phngsapplication.app.ui

import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class HomepageRowModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtHomePlants: String? = MyApp.getInstance().resources.getString(R.string.lbl_home_plants)
    ,
    /**
     * TODO Replace with dynamic value
     */
    var txt68TypesofPla: String? =
        MyApp.getInstance().resources.getString(R.string.msg_68_types_of_pla)

)