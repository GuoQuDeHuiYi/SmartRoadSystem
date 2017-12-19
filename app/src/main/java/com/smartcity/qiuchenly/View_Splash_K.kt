package com.smartcity.qiuchenly

import android.os.Handler
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
        val set = ActivitySet()
        set.TranslateBar = true//此选项表示是否透明状态栏
        set.doubleClickExitActivity = false//此选项设置表示双击退出整个App
        set.noClickBack = true//设置此选项表示在此界面时点击返回键无其他操作
        return set
    }

    val time = object : ThreadTimer(Handler(mainLooper),
            1000, 3000, false,
            true) {
        override fun TimeCallBack(totalTime: Long) {
            jmp.text = "跳过 (" + totalTime / 1000 + "s)"
            if (totalTime <= 0L) {
                click(jmp)
            }
        }
    }

    override fun ready() {
        Utils.mInitDataBase()
        ShareUtils.getSharePreferences()
        val isTwiceOpen = Utils.isTwiceOpen()
        if (isTwiceOpen)
            go(View_LoginPage_K::class.java, true)
        else {
            time.Start()
        }

    }

    override fun click(v: View?) {
        time.Stop()
        go(View_LoginPage_K::class.java, true)
    }

    override fun findID() {
        jmp.setOnClickListener(this)
    }

}