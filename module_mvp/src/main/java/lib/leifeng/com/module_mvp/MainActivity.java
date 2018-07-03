package lib.leifeng.com.module_mvp;


import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;

import com.leifeng.lib.OnLineOrderListBean;
import com.leifeng.lib.utils.LogUtil;

import java.util.Arrays;
import java.util.Random;

import lib.leifeng.com.module_mvp.base.BaseMvpActivity;
import lib.leifeng.com.module_mvp.contract.TestContract;
import lib.leifeng.com.module_mvp.presenter.TestPresenter;

public class MainActivity extends BaseMvpActivity<TestPresenter> implements TestContract.View {

    @Override
    protected void loadViewLayout() {
        super.loadViewLayout();
        setContentView(R.layout.activity_main);
        /*************************和mvp无关****************************/
        final Button mButton = findViewById(R.id.iv_image);
        final ViewWrapper viewWrapper = new ViewWrapper(mButton);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(viewWrapper, "width", 500, 1000).setDuration(30000).start();
            }
        });

        int[] testArray = new int[5000];
        Random random = new Random();
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = random.nextInt(10000);
        }
        LogUtil.e("============" + System.currentTimeMillis() + Arrays.toString(testArray));
        maoPao(testArray);
        xuanZe(testArray);
        zhiJieChaRu(testArray);
        xiEr(testArray);
        //[10, 70, 75, 53, 25, 78, 45
        /*************************和mvp无关****************************/
    }


    /**
     * 冒泡排序
     *
     * @param array 排序数组
     */
    private void maoPao(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array.length - i; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        LogUtil.e("============1" + System.currentTimeMillis() + Arrays.toString(array));
    }

    /**
     * 选择排序
     *
     * @param array 排序数组
     */
    private void xuanZe(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int index = i;

            for (int j = i + 1; j < array.length; j++) {
                int temp = array[index];
                index = temp > array[j] ? j : index;
            }

            if (index != i) {
                int temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }

        }
        LogUtil.e("============2" + System.currentTimeMillis() + Arrays.toString(array));


    }

    /**
     * 直接插入排序
     *
     * @param array 排序数组
     */
    private void zhiJieChaRu(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= 0 && temp < array[j]) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        }
        LogUtil.e("============3" + System.currentTimeMillis() + Arrays.toString(array));
    }


    /**
     * 希尔排序
     *
     * @param array 排序数组
     */
    private void xiEr(int[] array) {
//        int[] array = new int[]{10, 70, 75, 53, 25, 78, 45};
        int j = 0;
        int temp = 0;
        for (int increment = array.length / 2; increment > 0; increment /= 2) {
            for (int i = increment; i < array.length; i++) {
                temp = array[i];
                for (j = i - increment; j >= 0; j -= increment) {
                    if (temp < array[j]) {
                        array[j + increment] = array[j];
                    } else {
                        break;
                    }
                }
                array[j + increment] = temp;
            }
        }

        LogUtil.e("============4" + System.currentTimeMillis() + Arrays.toString(array));
    }

    @Override
    protected void initView() {
        p.start(this,"1", "2222332", "1522722440", "1514946450");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected TestPresenter createPresenter() {
        return TestPresenter.newInstance();
    }

    @Override
    public void testOne(OnLineOrderListBean onLineOrderListBean) {
        LogUtil.e("======================testOne" + onLineOrderListBean.toString());
    }

    @Override
    public void testTWo(int number) {
        LogUtil.e("======================testTWo" + number);
    }

    // 提供ViewWrapper类,用于包装View对象
    // 本例:包装Button对象
    private static class ViewWrapper {
        private View mTarget;

        // 构造方法:传入需要包装的对象
        public ViewWrapper(View target) {
            mTarget = target;
        }

        // 为宽度设置get（） & set（）
        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

    }

}
