package lib.leifeng.com.module_mvp.model;

import com.leifeng.lib.OnLineOrderListBean;
import com.leifeng.lib.net.RetrofitFactory;
import com.leifeng.lib.net.RetrofitUtils;
import com.leifeng.lib.net.observer.BaseObserver;

import io.reactivex.annotations.NonNull;
import lib.leifeng.com.module_mvp.contract.TestContract;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/6/19 11:46
 */

public class TestModel implements TestContract.Model {

    @NonNull
    public static TestModel newInstance() {
        return new TestModel();
    }

    @Override
    public void testM(String page, String store_id, String stoptime, String starttime,BaseObserver<OnLineOrderListBean> observer) {
        RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getOrderShouyin("1", "2222332", "1522722440", "1514946450"),observer);
    }
}
