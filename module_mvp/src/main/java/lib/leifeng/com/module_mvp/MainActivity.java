package lib.leifeng.com.module_mvp;


import com.leifeng.lib.OnLineOrderListBean;
import com.leifeng.lib.utils.LogUtil;

import java.util.ArrayList;

import lib.leifeng.com.module_mvp.base.BaseMvpActivity;
import lib.leifeng.com.module_mvp.contract.TestContract;
import lib.leifeng.com.module_mvp.presenter.TestPresenter;

public class MainActivity extends BaseMvpActivity<TestPresenter> implements TestContract.View {
    TestPresenter testPresenter;

    @Override
    protected void loadViewLayout() {
        super.loadViewLayout();
        setContentView(R.layout.activity_main);
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
    protected void onDestroy() {
        super.onDestroy();
        testPresenter.detachView();
    }


    @Override
    public void testOne(OnLineOrderListBean onLineOrderListBean) {
        LogUtil.e("======================testOne" + onLineOrderListBean.toString());
    }

    @Override
    public void testTWo(int number) {
        LogUtil.e("======================testTWo" + number);
    }
}
