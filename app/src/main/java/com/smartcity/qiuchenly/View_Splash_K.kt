package com.smartcity.qiuchenly

import android.os.Handler
import android.os.Looper
import android.view.View
import com.smartcity.qiuchenly.Base.*
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Author: QiuChenly
 * Date   : 2017/12/17
 * Usage :
 * Lasted:2017 12 17
 * ProjectName:SmartRoadSystem
 * Create: 2017 12 17 , on 20:04
 */
class View_Splash_K : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun getLayoutSetting(): ActivitySet {
        return ActivitySet().apply {
            this.TranslateBar = true
            this.doubleClickExitActivity = false
            this.noClickBack = true
        }
    }


    val time = object : ThreadTimer(Handler(Looper.getMainLooper()),
            1000, 3000, false,
            true) {
        override fun TimeCallBack(totalTime: Long) {
            jmp.text = "跳过 (" + totalTime / 1000 + "s)"
            if (isJmp)
                return
            if (totalTime <= 0L) {
                click(jmp)
            }
        }
    }

    var isJmp = false

    override fun ready() {
        ShareUtils.getSharePreferences()
        Utils.mInitDataBase()
        val isTwiceOpen = Utils.isTwiceOpen()
        if (isTwiceOpen)
            go(View_LoginPage_K::class.java, true)
        else {
            Utils_K.InitDataBaseHelper()//顺便初始化表数据
            time.Start()
        }
    }


    /**
    全局点击监听代理
     */
    override fun click(v: View?) {
        isJmp = true
        go(View_LoginPage_K::class.java, true)
    }


    /**
    使用Kotlin后只需要设置监听事件
     */
    override fun findID() {
        jmp.setOnClickListener(this)
    }
}