package com.leifeng.lib

import com.leifeng.lib.base.BaseActivity
import com.leifeng.lib.utils.LogUtil
import java.util.concurrent.locks.Lock
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

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
        // 内部类调用
        Outer.Inner().test()
        Outer().Inner1().test()
        // 枚举类使用
        color(Color.BLACK)
        // 委托属性、延迟属性、可观察属性
        val emp = Emp()
//        LogUtil.e("========p${emp.p}")
        emp.p = "NEW"
        emp.n = "name"
        LogUtil.e("========p${emp.p}==l${emp.l}==n${emp.n}")
        val a = arrayOf(1, 2, 3)
        val b = asList(1, 2, *a, 2)
    }

    fun <T> asList(vararg ts: T): List<T> {
        val result = ArrayList<T>()
        for (t in ts) // ts is an Array
            result.add(t)
        return result
    }

    private fun color(x: Color) {
        when (x) {
            Color.BLACK -> LogUtil.e("x == ${Color.BLACK}")
            else -> { // 注意这个块
                LogUtil.e("===Color")
            }
        }

        LogUtil.e("=========${1.sum(2)}")
    }


    override fun setListener() {

    }


    override fun loadData() {
        val list = mutableListOf(1, 2, 3)
        LogUtil.e("==============${list.getToString(zz)}")
        var a: A? = null

        LogUtil.e("==============Any${Any@ a.toString()}")
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

        fun test(s: E<String>) {
            val objects = s
        }

        fun fill(indexs: Array<out Int>) {

        }


    }

    abstract class D(name: String) : Base(name) {
        override fun test() {
            super.test()
        }
    }

    interface E<out T> {
        fun next(): T
    }

    class Outer {
        val outer: Int = 1

        class Inner {
            fun test(): Int {
                return 0
            }
        }

        inner class Inner1 {
            fun test(): Int {
                return outer
            }
        }
    }

    enum class Color(val rgb: String) {
        RED("0xff0000"),
        WHITE("0xffffff"),
        BLACK("0x000000")
    }

    class Emp {
        var p: String by Delegate()
        val l: String by lazy {
            LogUtil.e("=================lazy")
            "lazy"
        }
        var n: String by Delegates.observable("ooo") { kProperty: KProperty<*>, s: String, s1: String ->
            LogUtil.e("=======s==$s==s1==$s1")
        }

    }

    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            LogUtil.e("$value has been assigned to '${property.name} in $thisRef.'")
        }
    }
}

fun Int.sum(i: Int): Int {
    return this + i

}

inline fun <T> lock(lock: Lock, body: () -> T) {
}

