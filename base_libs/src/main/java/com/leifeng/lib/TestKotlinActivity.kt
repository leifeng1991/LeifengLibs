package com.leifeng.lib

import android.util.Log
import com.leifeng.lib.base.BaseActivity
import com.leifeng.lib.utils.LogUtil

class TestKotlinActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_test_kotlin)
    }

    override fun initView() {

    }

    override fun initData() {
        arryTest()
        controlFlowTest()
    }

    override fun setListener() {

    }

    override fun loadData() {

    }

    private fun arryTest() {
        val arryStr = arrayOf('0', '1', '9')
        val asc = Array(arryStr.size, { i -> arryStr[i] })
        val arryInt = arrayOfNulls<Int>(5)
        for (i in asc) {
//            LogUtil.e("=====$i")
        }
        var s = "abcdefghijk"
        s += "mn"
        for (c in s) {
//            LogUtil.e("=======$c")
        }
    }

    private fun decimalDigitValue(c: Char): Int {
        if (c !in '0'..'9') throw IllegalArgumentException("Out of range")
        return c.toInt() - '0'.toInt()
    }

    private fun controlFlowTest() {
        LogUtil.e("==========${getMaxValue(3, 4)}")
        /*when  取代了类 C 语言的 switch 操作符*/
        whenTest(1)
    }

    private fun getMaxValue(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    private fun whenTest(x: Any) {
        when (x) {
            1, 2 -> LogUtil.e("x == $x")
            else -> { // 注意这个块
                LogUtil.e("x is neither 1 nor 2")
            }
        }
        when(x){
            in 1..99 -> LogUtil.e("x == $x")
            x is String -> LogUtil.e("===is String")
            else -> {
                LogUtil.e("x is neither")
            }
        }
    }
}
