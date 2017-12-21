package com.smartcity.qiuchenly.function.PersonalCenter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.smartcity.qiuchenly.Base.SharedContext
import com.smartcity.qiuchenly.Base.Utils
import com.smartcity.qiuchenly.DataModel.userCars
import com.smartcity.qiuchenly.R
import kotlinx.android.synthetic.main.personal_page_infomation_usercars.view.*

/**
 * Author: QiuChenly
 * Date   : 2017/12/19
 * Usage :
 * Lasted:2017 12 19
 * ProjectName:SmartRoadSystem
 * Create: 2017 12 19 , on 16:55
 */
class personal_myCars(private var mList: List<userCars>) : RecyclerView.Adapter<personal_myCars.personal_myCars_VH>() {

    init {
        //无实现
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getIDByIndex(i: Int): Int {
        return when (i) {
            1 ->
                R.drawable.bwm
            2 ->
                R.drawable.china
            3 ->
                R.drawable.mazoa
            4 ->
                R.drawable.sanling
            else -> {
                R.drawable.bwm
            }
        }
    }

    override fun onBindViewHolder(holder: personal_myCars_VH, position: Int) {
        with(holder.itemView) {
            val bitmap = BitmapFactory.decodeStream(SharedContext.getContext().
                    resources.openRawResource(getIDByIndex(mList[position].carPic)))
            mPersonalInfomationUserCars.setImageBitmap(bitmap)
            mPersonalInfomationUserCarName.text = mList[position].carName
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            personal_myCars_VH {
        return personal_myCars_VH(View.inflate(parent.context,
                R.layout.personal_page_infomation_usercars, null))
    }


    class personal_myCars_VH(mView: View) : RecyclerView.ViewHolder(mView)

    fun setCarData(mList: List<userCars>) {
        this.mList = mList
        notifyDataSetChanged()
    }
}