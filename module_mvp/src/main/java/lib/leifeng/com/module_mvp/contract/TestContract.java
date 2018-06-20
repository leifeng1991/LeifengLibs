package lib.leifeng.com.module_mvp.contract;

import android.app.Activity;

import com.leifeng.lib.OnLineOrderListBean;
import com.leifeng.lib.net.observer.BaseObserver;

import lib.leifeng.com.module_mvp.base.IBaseModel;
import lib.leifeng.com.module_mvp.base.IBasePresenter;
import lib.leifeng.com.module_mvp.base.IBaseView;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/6/19 10:48
 */

public interface TestContract {

    abstract class Presenter extends IBasePresenter<Model, View> {
        public abstract void start(Activity activity,String page, String store_id, String stoptime, String starttime);
    }

    interface View extends IBaseView {
        void testOne(OnLineOrderListBean onLineOrderListBean);
        void testTWo(int number);
    }

    interface Model extends IBaseModel {
        void testM(String page, String store_id, String stoptime, String starttime,BaseObserver<OnLineOrderListBean> observer);
    }

}
