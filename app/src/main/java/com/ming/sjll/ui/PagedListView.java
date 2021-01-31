package com.ming.sjll.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ming.sjll.R;

public class PagedListView extends ListView implements AbsListView.OnScrollListener {
    private View footer; // 底部布局
    private LayoutInflater inflater;
    private int totalItemCount; // 总的Item的数量
    private int lastVisibleItemIndex; // 最后一个可见的Item的索引
    private boolean isLoading; // 是否正在加载
    private ListViewLoadListener listener; // 加载更多数据的回掉接口
    private boolean isNoData = false;//是否没有更多数据

    public PagedListView(Context context) {
        this(context, null);
    }

    public PagedListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    // 加载底部布局到ListView中
    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.sideworks_layout_footer, null);
//        footer.findViewById(R.id.control_layout_footer).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.e("huad","onScrollonScrollonScrollonScrollonScrollonScrollonScrollonScroll");
        this.lastVisibleItemIndex = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (totalItemCount == lastVisibleItemIndex && scrollState == SCROLL_STATE_IDLE) {
        if (totalItemCount == lastVisibleItemIndex) {
            if (!isLoading) {
                isLoading = true;
//                footer.findViewById(R.id.control_layout_footer).setVisibility(View.VISIBLE);
                if (listener != null && !isNoData )
                    listener.loadData(); // 调用接口中的回调方法进行数据更新s
            }
        }
    }

    // 加载更多数据的回掉接口
    public interface ListViewLoadListener {
        public void loadData();
    }

    public void setListViewLoadInterface(ListViewLoadListener listener) {
        this.listener = listener;
    }

    // 数据加载完成之后，隐藏footer布局
    public void onLoadComplete() {
        isLoading = false;
//        footer.findViewById(R.id.control_layout_footer).setVisibility(View.GONE);
    }

    // 数据加载完成之后，隐藏footer布局
    public void setNoData() {
        isNoData = true;
        footer.findViewById(R.id.control_layout_footer).setVisibility(View.GONE);
    }

    public void showFooter(boolean show) {
        if(show)
        {
            footer.findViewById(R.id.control_layout_footer).setVisibility(View.VISIBLE);
        }
        else
        {
            footer.findViewById(R.id.control_layout_footer).setVisibility(View.GONE);
        }
    }

    //    @Override
//  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//    int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, // 设计一个较大的值和AT_MOST模式
//
//                        MeasureSpec.AT_MOST);
//
//    super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);//再调用原方法测量
//
//  }

}