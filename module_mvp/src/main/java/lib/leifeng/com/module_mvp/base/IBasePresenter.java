package lib.leifeng.com.module_mvp.base;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/6/14 17:17
 */

public abstract class IBasePresenter<M, V> {
    public M mBaseModel;
    public V mBaseView;

    /**
     * 绑定
     *
     * @param mView MVP-V
     */
    public void attachView(V mView) {
        this.mBaseView = mView;
        mBaseModel = getModel();
        if (mBaseModel == null)
            throw new NullPointerException("Model 不能为空");
    }

    /**
     * 解绑
     */
    public void detachView() {
        if (mBaseModel != null)
            mBaseModel = null;
        if (mBaseView != null)
            mBaseView = null;
    }

    protected abstract M getModel();

}
