package com.smartcity.qiuchenly.function.PersonalCenter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.smartcity.qiuchenly.Base.SQ_PayHistoryCursor
import com.smartcity.qiuchenly.R
import kotlinx.android.synthetic.main.personal_page_prepaid_rv_view.view.*

/**
 * Author: QiuChenly
 * Date   : 2017/12/18
 * Usage :
 * Lasted:2017 12 18
 * ProjectName:SmartRoadSystem
 * Create: 2017 12 18 , on 20:30
 */
class personal_payHistoryRV(private var mList: List<SQ_PayHistoryCursor>) : RecyclerView.Adapter<personal_payHistoryRV.VH>() {

    override fun onBindViewHolder(h: VH?, position: Int) {
        with(h?.itemView) {
            this!!.prepaid_payUser?.text = "充值人:" + mList[position].whoPay
            this.prepaid_payCarID?.text = "车牌号:" + mList[position].carID
            this.prepaid_payPays?.text = "充值:" + mList[position].pay
            this.prepaid_payTotals?.text = "余额:" + mList[position].totalMoney
            this.prepaid_payTime?.text = mList[position].id.toString()
            this.prepaid_payTimes?.text = "充值时间：\n" + mList[position].payTime
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(View.inflate(
                parent.context,
                R.layout.personal_page_prepaid_rv_view,
                null))
    }

    class VH(var v: View) : RecyclerView.ViewHolder(v)

    fun updateData(mList: List<SQ_PayHistoryCursor>) {
        this.mList = mList
    }
}