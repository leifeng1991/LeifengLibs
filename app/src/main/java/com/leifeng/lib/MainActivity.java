package com.leifeng.lib;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leifeng.lib.base.BaseActivity;
import com.leifeng.lib.glide.GlideApp;
import com.leifeng.lib.net.BaseBean;
import com.leifeng.lib.net.BaseObserver;
import com.leifeng.lib.net.RetrofitFactory;
import com.leifeng.lib.net.RetrofitUtils;
import com.leifeng.lib.recyclerview.BaseAdapter;
import com.leifeng.lib.recyclerview.BaseViewHolder;
import com.leifeng.lib.recyclerview.ItemViewDelegate;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.lib.utils.PermissionHelper;
import com.leifeng.lib.weight.RefreshLoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class MainActivity extends BaseActivity {
    private RefreshLoadView mRefreshLoadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyAdapter adapter;
    private PermissionHelper mHelper;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mRefreshLoadView = findViewById(R.id.id_refresh_recycler_view);
        mRecyclerView = mRefreshLoadView.getRecyclerView();
        mSmartRefreshLayout = mRefreshLoadView.getSmartRefreshLayout();
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        adapter = new MyAdapter(getApplicationContext(), R.layout.adapter_list_item);
        mRecyclerView.setAdapter(adapter);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mHelper = new PermissionHelper(this);
        mHelper.requestPermissions("请授予[位置]权限", new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        //开始定位
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION);
        // 线程池
       /* ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("===============" + Thread.currentThread().getName() + "==" + finalI);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }*/

       /* ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("===============" + Thread.currentThread().getName() + "==" + finalI);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {

//        EmptyWrapper emptyWrapper = new EmptyWrapper(adapter);
//        emptyWrapper.setEmptyView("你的布局");
        adapter.addItemViewDelegate(new ItemViewDelegate<String>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item1;
            }

            @Override
            public boolean isForViewType(String item, int position) {
                return position % 3 == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, String s, int position) {

            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<String>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item2;
            }

            @Override
            public boolean isForViewType(String item, int position) {
                return position % 3 == 2;
            }

            @Override
            public void convert(BaseViewHolder holder, String s, int position) {

            }
        });
        mRefreshLoadView.setLoadingListener(new RefreshLoadView.OnLoadingListener() {
            @Override
            public void onRefresh(int page) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add("");
                }

                mRefreshLoadView.handleSuccess(adapter, list);

            }

            @Override
            public void onLoadMore(int page) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("");
                }
                mRefreshLoadView.handleSuccess(adapter, list);
            }
        });
        // 网络请求
        RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getUser("2222332"), new BaseObserver<UserBean>(mActivity) {
            @Override
            public void onSuccess(UserBean bean) {
                List<UserBean.DataBean> data = bean.getData();
                for (int i = 0; i < data.size(); i++) {
                    LogUtil.e("=============" + data.get(i).getName());
                }
            }

            @Override
            public void onFailed(BaseBean bean) {

            }
        });

    }

    class MyAdapter extends BaseAdapter<String> {

        public MyAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder holder, String s, int position) {
            holder.getConvertView().setBackgroundColor(ContextCompat.getColor(mContext, position % 2 == 0 ? R.color.colorAccent : R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
