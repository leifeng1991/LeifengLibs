package lib.leifeng.com.module_mvp.base;

import com.leifeng.lib.base.BaseActivity;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/6/19 14:05
 */

public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity {
    public P p;

    @Override
    protected void loadViewLayout() {
        p = createPresenter();
        if (p == null)
            throw new NullPointerException("IBasePresenter 不能为空");
        // 绑定
        p.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑
        p.detachView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {

    }

    protected abstract P createPresenter();
}
