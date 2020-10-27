package com.andr.view.recy;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.andr.common.tool.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MMRecyclerView extends LinearLayout
{
    private Context context;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private ClassicsHeader refresh_header;

    public MMRecyclerView(@NonNull Context context)
    {
        super(context);
        initView(context);
    }

    public MMRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public MMRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context ctx)
    {
        context = ctx;
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(ctx).inflate(R.layout.view_mm_recycler_view, this);
        recyclerView = view.findViewById(R.id.recv_view);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refresh_header = view.findViewById(R.id.refresh_header);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor)
    {
        recyclerView.addItemDecoration(decor);
    }

    public void smoothScrollToPosition(int position)
    {
        recyclerView.smoothScrollToPosition(position);
    }

    /**
     * 外部初始化方法1--单布局
     *
     * @param adapter                 适配器
     * @param layout                  布局管理器
     * @param listener                下拉刷新监听
     * @param requestLoadMoreListener 下拉加载监听
     */
    public void initBaseLayout(MMBaseQuickAdapter adapter, RecyclerView.LayoutManager layout,
                               MMRefreshListener listener,
                               MMLoadMoreListener requestLoadMoreListener)
    {
        init(adapter, layout, listener, requestLoadMoreListener);
        adapter.setRefreshLayout(refreshLayout);
        adapter.setOnErrorListener(v -> listener.onRefresh());

    }

    /**
     * 外部初始化方法1--多布局
     *
     * @param adapter  d适配器
     * @param listener 下拉刷新监听
     */
    public void initMultiLayout(MMBaseMultiItemQuickAdapter adapter,
                                RecyclerView.LayoutManager layout,
                                MMRefreshListener listener,
                                MMLoadMoreListener requestLoadMoreListener)
    {
        init(adapter, layout, listener, requestLoadMoreListener);
        adapter.setRefreshLayout(refreshLayout);
        adapter.setOnErrorListener(v -> listener.onRefresh());
    }

    private void init(BaseQuickAdapter adapter, RecyclerView.LayoutManager layout,
                      MMRefreshListener listener,
                      MMLoadMoreListener requestLoadMoreListener
    )
    {
        if (listener == null)
        {
            refreshLayout.setEnableRefresh(false);
        }
        recyclerView.setLayoutManager(layout);  //这里一定要在绑定前设置管理器,否者会导致头部视图显示异常
        adapter.bindToRecyclerView(recyclerView);
        if (listener != null)
            refreshLayout.setOnRefreshListener(refreshLayout -> listener.onRefresh());
        if (requestLoadMoreListener != null)
            adapter.setOnLoadMoreListener(() -> requestLoadMoreListener.onLoadMore(), recyclerView);
    }


    public interface MMRefreshListener
    {
        void onRefresh();
    }

    public interface MMLoadMoreListener
    {
        void onLoadMore();
    }
}
