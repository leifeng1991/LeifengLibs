package lib.leifeng.com.module_mvp.presenter;

import android.app.Activity;

import com.leifeng.lib.OnLineOrderListBean;
import com.leifeng.lib.net.observer.BaseObserver;
import com.leifeng.lib.utils.LogUtil;

import lib.leifeng.com.module_mvp.contract.TestContract;
import lib.leifeng.com.module_mvp.model.TestModel;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/6/19 11:15
 */

public class TestPresenter extends TestContract.Presenter {
    public static TestPresenter newInstance() {
        return new TestPresenter();
    }

    @Override
    protected TestContract.Model getModel() {
        return TestModel.newInstance();
    }


    @Override
    public void start(Activity activity, String page, String store_id, String stoptime, String starttime) {
        mBaseModel.testM(page, store_id, stoptime, starttime, new BaseObserver<OnLineOrderListBean>(activity) {
            @Override
            public void onSuccess(OnLineOrderListBean onLineOrderListBean) {
                mBaseView.testOne(onLineOrderListBean);
            }
        });
    }
}
