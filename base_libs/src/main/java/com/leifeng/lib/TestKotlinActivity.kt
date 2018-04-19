package com.leifeng.lib

import com.leifeng.lib.base.BaseActivity
import com.leifeng.lib.utils.LogUtil

class TestKotlinActivity : BaseActivity() {
    internal var zz: Int = 1110

    override fun loadViewLayout() {
        setContentView(R.layout.activity_test_kotlin)
    }

    override fun initView() {
    }

    override fun initData() {
        arryTest()
        controlFlowTest()
        var demo = Demo("张三", "女")
        demo.test()
    }

    override fun setListener() {

    }

    override fun loadData() {
        val list = mutableListOf(1, 2, 3)
        LogUtil.e("==============${list.getToString(zz)}")
        var a: A? =null

        LogUtil.e("==============Any${Any@a.toString()}")
    }

    private fun <T> MutableList<T>.getToString(t: T): String {
        return t.toString() + this.toString()
    }

    private fun Any?.toString(): String {
        if (this == null) return "null"
        return this.toString()
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
        val arryStr = arrayOf('0', '1', '9')
        forTest(arryStr)
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
        when (x) {
            in 1..99 -> LogUtil.e("x == $x")
            x is String -> LogUtil.e("===is String")
            else -> {
                LogUtil.e("x is neither")
            }
        }
    }

    private fun <T> forTest(list: Array<T>) {
//        for (t in list) {
//            LogUtil.e("============1$t")
//        }
//        for (t in list.iterator()) {
//            LogUtil.e("============2$t")
//        }
//
        /**/
//        aa@ for (i in 1..100) {
//            for (j in 1..100) {
//                if (i == 20) break@aa
//                LogUtil.e("=====i==$i")
//            }
//        }
//        for (i in 1..100) {
//            for (j in 1..100) {
//                if (i == 20)
//                    break
//                LogUtil.e("=====i==$i")
//            }
//        }
//        // 输出0
//        list.forEach {
//            if (it == '1')
//                return
//            LogUtil.e("=====it==$it")
//        }
//        // 输出0,9
//        list.forEach lit@{
//            if (it == '1')
//                return@lit
//            LogUtil.e("=====it==$it")
//        }
//        // 输出0,9
//        list.forEach {
//            if (it == '1')
//                return@forEach
//            LogUtil.e("=====it==$it")
//        }

        val demo = Demo("张三", "男")
        demo.test()
    }


    private class Demo(name: String) {
        init {
            LogUtil.e("===========init$name")
        }

        constructor(name: String, sex: String) : this(name) {
            LogUtil.e("===========init$name$sex")
        }

        fun test() {
            LogUtil.e("===========name")
        }
    }

    /**************继承*****************/
    open class Base(name: String) {
        open val x: Int = 0
        open var y: Int = 0
        open fun test() {
            LogUtil.e("====$y")
        }

        fun baseTest() {

        }
    }

    class Test(name: String, sex: String) : Base(name) {
        constructor(name: String, sex: String, age: Int) : this(name, sex) {
            val b: Int?
        }

        override var x: Int
            get() = super.x
            set(value) {}

        var a: Int? = null
        var counter = 0 // 此初始器值直接写入到幕后字段
            set(value) {
                if (value >= 0)
                    field = value
            }

        override fun test() {
            super.test()
            LogUtil.e("$x")
        }
    }

    open class A {
        open fun a() {}
    }

    interface B {
        fun a() {
        }
    }

    class C() : A(), B {
        override fun a() {
            super<A>.a()
            super<B>.a()
        }
    }

    abstract class D(name: String) : Base(name) {
        override fun test() {
            super.test()
        }
    }
}
