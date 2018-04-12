package com.leifeng.lib;

import android.Manifest;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leifeng.lib.base.PermissionActivity;
import com.leifeng.lib.glide.GlideImageLoader;
import com.leifeng.lib.recyclerview.BaseAdapter;
import com.leifeng.lib.recyclerview.BaseViewHolder;
import com.leifeng.lib.recyclerview.HeaderAndFooterWrapper;
import com.leifeng.lib.recyclerview.ItemViewDelegate;
import com.leifeng.lib.recyclerview.MultiItemTypeAdapter;
import com.leifeng.lib.utils.PermissionHelper;
import com.leifeng.lib.utils.VersionUpdateUtils;
import com.leifeng.lib.weight.RefreshLoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.Arrays;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class MainActivity extends PermissionActivity {
    private View mHeaderView;
    private Banner mBanner;
    private RefreshLoadView mRefreshLoadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyAdapter adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mHeaderView = getLayoutInflater().inflate(R.layout.view_header, null);
        mBanner = mHeaderView.findViewById(R.id.banner);
        mRefreshLoadView = findViewById(R.id.id_refresh_recycler_view);
        mRecyclerView = mRefreshLoadView.getRecyclerView();
        mSmartRefreshLayout = mRefreshLoadView.getSmartRefreshLayout();
    }

    @Override
    protected void initData() {
        super.initData();
        /***************************banner相关************************/
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(getResources().getStringArray(R.array.url)));
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(getResources().getStringArray(R.array.titles)));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        /***************************RefreshLoadView*******************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        adapter = new MyAdapter(getApplicationContext(), R.layout.adapter_list_item);
//        mRecyclerView.setAdapter(adapter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        /************************权限申请********************************/
        // 权限申请
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
        /******************************线程池*****************************/
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
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<OnLineOrderListBean.DataBean>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
                /********************************WebView**************************************/
//                startActivity(BaseWebActivity.newIntent(mContext,"详情","https://www.baidu.com/"));
                /*********************************Router***************************************/
                // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance().build("/news/list").navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        /********************************adapter添加头部和尾部**************************************/
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderView);
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);

        /*************************多种样式adapter**************************************/
        adapter.addItemViewDelegate(new ItemViewDelegate<OnLineOrderListBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item1;
            }

            @Override
            public boolean isForViewType(OnLineOrderListBean.DataBean item, int position) {
                return position % 3 == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean s, int position) {

            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<OnLineOrderListBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item2;
            }

            @Override
            public boolean isForViewType(OnLineOrderListBean.DataBean item, int position) {
                return position % 3 == 2;
            }

            @Override
            public void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean s, int position) {

            }
        });
        // 刷新和加载
       /* mRefreshLoadView.setLoadingListener(new RefreshLoadView.OnLoadingListener() {
            @Override
            public void onRefresh() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add("");
                }

                mRefreshLoadView.handleSuccess(adapter, list);

            }

            @Override
            public void onLoadMore() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("");
                }
                mRefreshLoadView.handleSuccess(adapter, list);
            }
        });*/
        /**********************************网络请求**************************************/
       /* RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getUser("2222332"), new BaseObserver<UserBean>(mActivity) {
            @Override
            public void onSuccess(UserBean bean) {
                List<UserBean.DataBean> data = bean.getNetData();
                for (int i = 0; i < data.size(); i++) {
                    LogUtil.e("=============" + data.get(i).getName());
                }
            }

            @Override
            public void onFailed(BaseBean bean) {

            }
        });*/
        /******************************刷新和加载***************************************/
        mRefreshLoadView.setLoadingListener(new RefreshLoadView.OnLoadingListener() {
            @Override
            public void onRefresh() {
                getNetData();
            }

            @Override
            public void onLoadMore() {
                getNetData();
            }
        });

//        getNetData();

    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    /**
     * 网络请求
     */
    private void getNetData() {

       /* RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getOrderShouyin(
                mRefreshLoadView.getPage() + "", "2222332", "1522722440", "1514946450"), new BaseObserver<OnLineOrderListBean>(mContext) {
            @Override
            public void onSuccess(OnLineOrderListBean onLineOrderListBean) {
                mRefreshLoadView.handleSuccess(adapter, onLineOrderListBean.getData());
                // 有头部或者尾部时必须加上这一行
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailed(OnLineOrderListBean bean) {
                mRefreshLoadView.handleFail();
                // 有头部或者尾部时必须加上这一行
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
        });*/


        VersionUpdateUtils.requestStoragePermission(mActivity, mHelper, "http:\\/\\/imtt.dd.qq.com\\/16891\\/2C61DFB307875A308A12652B9FF9EC78.apk?fsname=com.xxzlkj.zhaolin_1.2.1_9.apk&amp;csr=1bbd", 0);

       /* RetrofitUtils.downLoad(mContext, RetrofitFactory.getInstance().getAPI().downLoadFile("http:\\/\\/imtt.dd.qq.com\\/16891\\/2C61DFB307875A308A12652B9FF9EC78.apk?fsname=com.xxzlkj.zhaolin_1.2.1_9.apk&amp;csr=1bbd")
                , new FileDownLoadObserver<File>() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                        LogUtil.e("===========下载完成");
                    }

                    @Override
                    public void onDownLoadFail(Throwable throwable) {
                        LogUtil.e("===========下载出错");
                    }

                    @Override
                    public void onProgress(int progress, long total) {
                        LogUtil.e("===========大小" + total + "==百分比" + progress);
                    }
                });*/
    }

    /*********************************adapter***********************************/
    class MyAdapter extends BaseAdapter<OnLineOrderListBean.DataBean> {

        public MyAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
            holder.getConvertView().setBackgroundColor(ContextCompat.getColor(mContext, position % 2 == 0 ? R.color.colorAccent : R.color.colorPrimaryDark));

        }
    }

}
