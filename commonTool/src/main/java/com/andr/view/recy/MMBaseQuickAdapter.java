package com.andr.view.recy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.common.tool.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 基于BaseQuickAdapter封装类,可实现空视图,错误视图,加载视图切换
 *
 * @param <T>
 * @param <K>
 */
public abstract class MMBaseQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K>
{
    private Context context;

    private View empty_view;
    private View load_view;
    private View error_view;
    private View.OnClickListener onErrorListener;

    private SmartRefreshLayout refreshLayout=null;

    public Context getContext()
    {
        return context;
    }

    public MMBaseQuickAdapter(Context context, int layoutResId, @Nullable List data)
    {
        super(layoutResId, data);
        this.context = context;
        initEmptyView(context);
        initLoadView(context);
        initErrorView(context);

    }

    public void setRefreshLayout(SmartRefreshLayout refreshLayout)
    {
        this.refreshLayout = refreshLayout;
    }

    public void setOnErrorListener(View.OnClickListener onErrorListener)
    {
        this.onErrorListener = onErrorListener;
    }

    @SuppressLint("InflateParams")
    private void initEmptyView(Context context)
    {
        empty_view = LayoutInflater.from(context).inflate(R.layout.list_empty_view, null);
    }

    @SuppressLint("InflateParams")
    private void initLoadView(Context context)
    {
        load_view = LayoutInflater.from(context).inflate(R.layout.list_load_view, null);
    }

    @SuppressLint("InflateParams")
    private void initErrorView(Context context)
    {
        error_view = LayoutInflater.from(context).inflate(R.layout.list_error_view, null);
    }


    /**
     * 自创方法,处理list数据装载
     *
     * @param page    当前页数
     * @param hasNext true还有下一页,false没有下一页
     * @param mList   数据
     */
    public void setMMData(int page, boolean hasNext, List<T> mList)
    {
        setMMData(page, hasNext, mList,"网络好像开小差了!","数据空空如也");
    }


    /**
     * 自创方法,处理list数据装载
     *
     * @param page    当前页数
     * @param hasNext true还有下一页,false没有下一页
     * @param mList   数据
     */
    public void setMMData(int page, boolean hasNext, List<T> mList, String emptyMsg,
                          String errorMsg)
    {
        if (page == 1)
        {

            if (mList == null)
            {
                if(refreshLayout!=null){
                    refreshLayout.finishRefresh(false);
                }
                if (getData().size() == 0)
                    showErrorView(errorMsg);
                return;
            }
            if(refreshLayout!=null){
                refreshLayout.finishRefresh(true);
            }
            //刷新
            if (mList.size() == 0)
            {
                showEmptyView(emptyMsg);
                return;
            }
            setNewData(mList);

            if (hasNext)  //有下一页
            {
                setEnableLoadMore(true);
                loadMoreComplete();
            } else
            { //无下一页
                setEnableLoadMore(false);
                loadMoreEnd();
            }


        } else
        {
            if (mList == null || mList.size() == 0)
            {
                loadMoreFail();
            } else
            {
                addData(mList);
                if (hasNext)  //有下一页
                {
                    setEnableLoadMore(true);
                    loadMoreComplete();
                } else
                { //无下一页
                    setEnableLoadMore(false);
                    loadMoreEnd();
                }
            }
        }
    }

    @Override
    public void setData(int index, @NonNull T data)
    {
        super.setData(index, data);
        disableLoadMoreIfNotFullPage();
    }

    /**
     * 显示空视图
     *
     * @param msg
     * @param
     */
    public void showEmptyView(String msg)
    {
        if (empty_view == null) return;
        ImageView image_empty_view = empty_view.findViewById(R.id.image_empty_view);
        TextView text_empty_view = empty_view.findViewById(R.id.text_empty_view);
//        if (iconRes != 0)
//            image_empty_view.setImageResource(iconRes);
        text_empty_view.setText(msg);
        //数据得清空才会显示空布局
        getData().clear();
        setEmptyView(empty_view);
        notifyDataSetChanged();
    }

    /**
     * 显示加载视图
     *
     * @param msg
     * @param
     */
    public void showLoadView(String msg)
    {
        if (load_view == null) return;
        ImageView image_load_view = load_view.findViewById(R.id.image_load_view);
        TextView text_load_view = load_view.findViewById(R.id.text_load_view);
//        if (iconRes != 0)
//            image_load_view.setImageResource(iconRes);
        text_load_view.setText(msg);
        //数据得清空才会显示空布局
        getData().clear();
        setEmptyView(load_view);
        notifyDataSetChanged();
    }


    /**
     * 显示错误视图
     *
     * @param msg
     * @param
     */
    public void showErrorView(String msg)
    {
        if (error_view == null) return;
        ImageView image_error_view = error_view.findViewById(R.id.image_error_view);
        TextView text_error_view = error_view.findViewById(R.id.text_error_view);
        TextView btn_error_view = error_view.findViewById(R.id.btn_error_view);
        if (onErrorListener != null)
            btn_error_view.setOnClickListener(onErrorListener);
        text_error_view.setText(msg);
        //数据得清空才会显示空布局
        getData().clear();
        setEmptyView(error_view);
        notifyDataSetChanged();
    }
}
