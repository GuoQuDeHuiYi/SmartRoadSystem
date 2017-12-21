package com.smartcity.qiuchenly.function

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.smartcity.qiuchenly.Adapter.mFun_nav_RlAdapter
import com.smartcity.qiuchenly.R


/**
 * Author: QiuChenly
 * Date   : 2017/12/17
 * Usage :
 * Lasted:2017 12 17
 * ProjectName:SmartRoadSystem
 * Create: 2017 12 17 , on 20:30
 */
class fun_navogation_itemSelect_K(con: Activity,
                                  list: List<String>,
                                  iNavigation_items_Click: iNavigation_items_Click) {
    init {
        val rec: RecyclerView =
                con.findViewById(R.id.navigation_body_selectItems)
        rec.setHasFixedSize(false)
        rec.layoutManager = LinearLayoutManager(con)
        rec.adapter = mFun_nav_RlAdapter(list, iNavigation_items_Click)
    }

}