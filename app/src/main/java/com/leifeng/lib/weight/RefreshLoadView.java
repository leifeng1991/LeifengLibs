package com.leifeng.lib.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.leifeng.lib.R;
import com.leifeng.lib.recyclerview.BaseAdapter;
import com.leifeng.lib.recyclerview.progress.ProgressRelativeLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/15 11:13
 */


public class RefreshLoadView extends RelativeLayout {
    private ProgressRelativeLayout mLoadingLayout;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseAdapter adapter;
    private OnLoadingListener loadingListener;
    public int page = 1;
    public int loadSize = 20;

    public RefreshLoadView(Context context) {
        this(context, null);
    }

    public RefreshLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLoadView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = View.inflate(context, R.layout.layout_refresh, null);
        mLoadingLayout = rootView.findViewById(R.id.id_progress_layout);
        mSmartRefreshLayout = rootView.findViewById(R.id.id_smart_refresh_layout);
        mRecyclerView = rootView.findViewById(R.id.id_recycler_view);
        removeAllViews();
        addView(rootView);
    }

    /**
     * 设置刷新和加载监听
     *
     * @param loadingListener 监听
     */
    public void setLoadingListener(final OnLoadingListener loadingListener) {
        this.loadingListener = loadingListener;
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page = 1;
                            // 设置有更多数据
                            mSmartRefreshLayout.setNoMoreData(false);
                            if (loadingListener != null)
                                loadingListener.onRefresh(page);
                        }
                    },2000);

                }

                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null && adapter.getItemCount() > 0) {
                                // 页数
                                page = adapter.getItemCount() / loadSize + 1;
                                if (loadingListener != null)
                                    loadingListener.onLoadMore(page);
                            }
                        }
                    },2000);

                }
            });
        }
    }

    /**
     * 加载成功
     *
     * @param adapter 适配器
     * @param list    数据集合
     */
    public <T> void handleSuccess(BaseAdapter<T> adapter, List<T> list) {
        this.adapter = adapter;
        if (page == 1) {
            if (list == null || list.size() == 0) {
                // 显示空白布局(布局根据需求进行修改)
                mLoadingLayout.showEmpty(R.mipmap.ic_launcher, "空白页", "暂无数据");
            } else {
                // 显示数据界面
                mLoadingLayout.showContent();
                // 添加数据
                adapter.clearAndAddList(list);
            }
            // 刷新完成
            mSmartRefreshLayout.finishRefresh();
        } else {
            // 显示数据界面
            mLoadingLayout.showContent();
            // 添加数据
            adapter.addList(list);
            // 加载完成
            mSmartRefreshLayout.finishLoadMore(0,true,list != null && list.size() < 20);
        }
    }

    /**
     * 加载失败
     */
    public void handleFail() {
        if (page == 1) {
            // 刷新完成
            mSmartRefreshLayout.finishRefresh(false);
            // 刷新失败(布局根据需求进行修改)
            mLoadingLayout.showError(R.mipmap.ic_launcher, "失败页", "加载出错", "刷新", new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 重新刷新数据
                    page = 1;
                    if (loadingListener != null)
                        loadingListener.onRefresh(page);
                }
            });
        }else {
            // 加载完成
            mSmartRefreshLayout.finishLoadMore(0,false,false);
        }
    }

    /**
     * 获取 RecyclerView
     *
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 获取 SmartRefreshLayout
     *
     * @return SmartRefreshLayout
     */
    public SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    /**
     * 获取一页加载的数量
     */
    public int getLoadSize() {
        return loadSize;
    }

    /**
     * 设置一页加载的数量
     *
     * @param loadSize 数量
     */
    public void setLoadSize(int loadSize) {
        this.loadSize = loadSize;
    }

    public interface OnLoadingListener {
        /**
         * 刷新
         *
         * @param page 分页
         */
        void onRefresh(int page);

        /**
         * 加载
         *
         * @param page 分页
         */
        void onLoadMore(int page);
    }
}
