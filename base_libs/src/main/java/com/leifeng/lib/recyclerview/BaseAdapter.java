package com.leifeng.lib.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/14 10:07
 */


public abstract class BaseAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public BaseAdapter(final Context context, final int layoutId) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                BaseAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);

    @Override
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }
}
