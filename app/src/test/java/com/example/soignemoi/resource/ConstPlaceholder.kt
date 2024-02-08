package com.example.soignemoi.resource

import com.example.soignemoi.util.DateUtil.toDate

object ConstPlaceholder {

    const val VISITOR_ID = 1

    const val START_DATE_STR = "2023-12-16T23:00:00.000+00:00"
    const val END_DATE_STR = "2023-12-27T23:00:00.000+00:00"

    val START_DATE = START_DATE_STR.toDate
    val END_DATE = END_DATE_STR.toDate

}