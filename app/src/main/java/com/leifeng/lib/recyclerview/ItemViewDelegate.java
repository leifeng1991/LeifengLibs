package com.leifeng.lib.recyclerview;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/14 10:13
 */


public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(BaseViewHolder holder, T t, int position);

}
