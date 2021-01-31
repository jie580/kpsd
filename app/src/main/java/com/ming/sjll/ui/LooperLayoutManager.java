package com.ming.sjll.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LooperLayoutManager extends RecyclerView.LayoutManager {
    /**
     * 是否开启循环
     */
    private boolean looperEnable = true;

    /**
     * 事件绑定
     */
    private OnSelectedPositionChangedListener listener;

    /**
     * 屏幕宽度
     */
    private int windowWidth;

    /**
     * 一个屏幕中，第几个位置作为标准选中位置.在屏幕的百分比
     */
    private float selectPoint = 30;

    /**
     * 横向选中位置
     */
    private int point = 0;

    RecyclerView.Recycler myRecycler;

    List<View> list;

    /**
     * 初始化
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 如果总宽度少于屏幕则不滚动
     */

    private void checkLoop() {

        if (getItemCount() == getChildCount()) {
            this.looperEnable = false;
        } else {
            this.looperEnable = true;
        }
    }


    /**
     * 滑动事件结束后
     *
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        Log.e("gundong", state + "");
        super.onScrollStateChanged(state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                //滚动停止时
                fixOffsetWhenFinishScroll();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                //动画滚动时
                break;
        }
    }

    /**
     * 滚动时，执行的
     */
    private void fixOffsetWhenFinishScrollING() {

        View focusView = null;
        int pos = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }

            if (itemIsSelect(view)) {
                focusView = view;
                pos = i;
                break;
            }
        }
        if (listener != null) {
            listener.scrollPositionChanged(pos, focusView);
        }

    }

    /**
     * 当前视图是否被选中
     *
     * @param view
     * @return
     */
    private boolean itemIsSelect(View view) {
        int l = view.getLeft();
        int r = view.getRight();
        int viewWidth = r - l;
        int halfWidth = viewWidth / 2;
        if ((Math.abs(halfWidth + l - point) <= halfWidth) || (Math.abs(point - halfWidth - l) <= halfWidth)) {
            return true;
        }
        return false;
    }

    /**
     * 自动选中
     */
    private void fixOffsetWhenFinishScroll() {


////        需要跳转的位置
        int toleft = 0;
        View focusView = null;
        int pos = 0;
////        获取当前跳转的点最近的view
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }

            if (itemIsSelect(view)) {
                focusView = view;
                pos = i;
                toView(pos);
                break;
            }
        }
        offsetChildrenHorizontal(toleft);
        if (listener != null) {
            listener.selectedPositionChanged(pos, focusView);
        }
    }

    /**
     * 跳转到某个View
     *
     * @param position
     */
    public void toView(int position) {

//        fill(-1,myRecycler,null);
//        fill(1,myRecycler,null);
//        this.
        View view = getChildAt(position);
        if (view == null) {
            return;
        }
        int l = view.getLeft();
        int r = view.getRight();
        Log.e("左宽" + l, "右" + r);
        int viewWidth = r - l;
        int halfWidth = viewWidth / 2;
        int toleft = point - l - viewWidth / 2;
        offsetChildrenHorizontal(toleft);
    }

    public void toViewClick(int position) {
//        //向右滚动
//        View firstView = getChildAt(0);
//        View scrap = null;
//        scrap = myRecycler.getViewForPosition(getItemCount() - 1);
//        if (scrap == null) {
//            Log.e("最后一个view空","1111");
//            return ;
//        }
//
//        addView(scrap, 0);
//        measureChildWithMargins(scrap,0,0);
//        int width = getDecoratedMeasuredWidth(scrap);
//        int height = getDecoratedMeasuredHeight(scrap);
//        layoutDecorated(scrap, firstView.getLeft() - width, 0,
//                firstView.getLeft(), height);
//        View firstView2 = getChildAt(0);
//        System.out.println(scrap);

////
//        int autualWidth = 0;
//        for (int i = 0; i < getItemCount(); i++) {
//            //标注3.初始化，将在屏幕内的view填充
////            recycler.getViewForPosition(i) 方法会从缓存中拿到对应索引的 itemView，这个方法内部会先从 scrap 缓存中取 itemView，如果没有则从 recycler 缓存中取，如果还没有则调用 adapter 的 onCreateViewHolder() 去创建 itemView。
////            View itemView = myRecycler.getViewForPosition(i);
//            View itemView = list.get(6);
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    onClick(view);
////                }
////            });
//            addView(itemView);
//
//            //标注4.测量itemView的宽高
//            measureChildWithMargins(itemView, 0, 0);
//            int width = getDecoratedMeasuredWidth(itemView);
//            int height = getDecoratedMeasuredHeight(itemView);
//            //标注5.根据itemView的宽高进行布局
//            layoutDecorated(itemView, autualWidth - width, 0, autualWidth, height);
//
////            autualWidth -= width;
//            //标注6.如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
////            if (autualWidth > getWidth()) {
////                break;
////            }
//        }
//        offsetChildrenHorizontal(200);


        View view = getChildAt(position);
        if (view == null) {
            return;
        }
        int l = view.getLeft();
        int r = view.getRight();
        Log.e("左宽" + l, "右" + r);
        int viewWidth = r - l;
        int halfWidth = viewWidth / 2;
        int toleft = point - l - viewWidth / 2;
        offsetChildrenHorizontal(toleft);


        int off = 0;
//        向左边加数据
        if (l - point > 0) {
//            int  t = list.size()-getChildCount();
            off = getChildCount() * viewWidth;
            for (int t = getChildCount(); t < list.size() - getChildCount(); t++) {
                View itemView = list.get(t);
                addView(itemView);
                measureChildWithMargins(itemView, 0, 0);
                int width = getDecoratedMeasuredWidth(itemView);
                int height = getDecoratedMeasuredHeight(itemView);
                //标注5.根据itemView的宽高进行布局
                layoutDecorated(itemView, off - viewWidth, 0, off, height);
                off += viewWidth;
            }
        } else {
            int t = list.size() - getChildCount();
            off = t * viewWidth;
            for (t = list.size(); t < getChildCount(); t--) {
                View itemView = list.get(t);
                addView(itemView);
                measureChildWithMargins(itemView, 0, 0);
                int width = getDecoratedMeasuredWidth(itemView);
                int height = getDecoratedMeasuredHeight(itemView);
                //标注5.根据itemView的宽高进行布局
                layoutDecorated(itemView, off - viewWidth, 0, off, height);
                off -= viewWidth;
            }
        }
    }

    //    @Override
//    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
//        super.onDetachedFromWindow(view,recycler);
//    }

//    判断向左还是向右添加，如果是向左添加


    /**
     * 获取整个布局的水平空间大小
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 获取整个布局的垂直空间大小
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }


    //    .打开滚动开关
//
//    接着，对滚动方向做处理，重写canScrollHorizontally()方法，打开横向滚动开关。注意我们是实现横向无限循环滚动，所以实现此方法，如果要对垂直滚动做处理，则要实现canScrollVertically()方法。
    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    //    重写 onLayoutChildren() 方法，开始对itemView初始化布局。
//    就是对所有的 itemView 进行布局，一般会在初始化和调用 Adapter 的 notifyDataSetChanged() 方法时调用.
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {


//Log.e("33333333333333333333","sddddddddddddddddddddddddddddddddddddd");
        windowWidth = getHorizontalSpace();
        point = (int) (windowWidth * selectPoint * 0.01);
//        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
//        View scrap = recycler.getViewForPosition(0);
//        addView(scrap);
//        measureChildWithMargins(scrap, 0, 0);
//        //计算测量布局的宽高
//        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
//        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
//        mStartX = Math.round((getHorizontalSpace() - mDecoratedChildWidth) * 1.0f / 2);
//        mStartY = Math.round((getVerticalSpace() - mDecoratedChildHeight) *1.0f / 2);

//Log.e("xxxxxxxxxxxxxxxxx","11111111111111");


        if (getItemCount() <= 0) {
            return;
        }
        //标注1.如果当前时准备状态，直接返回
        if (state.isPreLayout()) {
            return;
        }
        //标注2.将视图分离放入scrap缓存中，以准备重新对view进行排版
//        detachAndScrapAttachedViews(recycler) 方法会将所有的 itemView 从View树中全部detach，然后放入scrap缓存中。了解过RecyclerView的同学应该知道，RecyclerView是有一个二级缓存的，一级缓存是 scrap 缓存,二级缓存是 recycler 缓存，其中从View树上detach的View会放入scrap缓存里，调用removeView()删除的View会放入recycler缓存中。
        detachAndScrapAttachedViews(recycler);

        int autualWidth = 0;
        list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < getItemCount(); i++) {
            //标注3.初始化，将在屏幕内的view填充
//            recycler.getViewForPosition(i) 方法会从缓存中拿到对应索引的 itemView，这个方法内部会先从 scrap 缓存中取 itemView，如果没有则从 recycler 缓存中取，如果还没有则调用 adapter 的 onCreateViewHolder() 去创建 itemView。
            View itemView = recycler.getViewForPosition(i);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClick(view);
//                }
//            });
            addView(itemView);
            list.add(itemView);
            //标注4.测量itemView的宽高
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            //标注5.根据itemView的宽高进行布局
            layoutDecorated(itemView, autualWidth, 0, autualWidth + width, height);

            autualWidth += width;
            //标注6.如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
//            if (autualWidth > getWidth()) {
//                break;
//            }
        }
        myRecycler = recycler;
        checkLoop();
    }

    //    对RecyclerView进行滚动和回收itemView处理
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        System.out.println(recycler.getScrapList());
        System.out.println(state);
        fixOffsetWhenFinishScrollING();
////        this.windowWidth = this.windowWidth*2;
//
        if (looperEnable == false) {//如果不可以循环，则滑动全部数据
            fill(dx, recycler, state);
            int travl = fillRgith(dx, recycler, state);
            offsetChildrenHorizontal(-travl);
            return travl;
        }

//        Log.e("滚动条状态：",state.toString())
        //标注1.横向滑动的时候，对左右两边按顺序填充itemView
        int travl = fill2(dx, recycler, state);
        if (travl == 0) {
            return 0;
        }

        //2.滑动
        offsetChildrenHorizontal(-travl);

        //3.回收已经不可见的itemView
        recyclerHideView(dx, recycler, state);
        return travl;
    }


    /**
     * 在屏幕显示的数量等于实际数量时，向左滑动时，如果右边没有数据，则可以到最左边变
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    private int fillRgith(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {


        if (dx > 0) {
            View lastView = getChildAt(getItemCount() - 1);
            if (lastView == null) {
                return dx;
            }
            Log.e(getItemCount() + "", lastView.getLeft() + ",,,,,,," + getWidth());
            if (lastView.getLeft() < 0) {
                return 0;
            }
        }
        if (dx < 0) {
            int point = (int) (windowWidth * selectPoint * 0.01);
            View firstView = getChildAt(0);
            if (firstView == null) {
                return dx;
            }
            if (firstView.getLeft() >= point) {
                return 0;
            }
        }

        return dx;
    }


    /**
     * 左右滑动的时候，填充
     */
    private int fill2(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            //标注1.向左滚动
//            dx为将要滑动的距离，如果 dx > 0，则是往左边滑动，则需要判断右边的边界，如果最后一个itemView完全显示出来后，在右边填充一个新的itemView。
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);
            //标注2.可见的最后一个itemView完全滑进来了，需要补充新的
            if (lastView.getRight() < getWidth()) {
                View scrap = null;
                //标注3.判断可见的最后一个itemView的索引，
                // 如果是最后一个，则将下一个itemView设置为第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable && false) {
//                        如果索引是最后一个，则需要新填充的itemView为第0个，这样就可以实现往左边滑动时候无限循环了
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dx;
                }
                //标注4.将新的itemViewadd进来并对其测量和布局，将填充进去了。
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, lastView.getRight(), 0,
                        lastView.getRight() + width, height);
                return dx;
            }
        } else {
            //向右滚动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);

            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable && false) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }


    /**
     * 左右滑动的时候，填充
     */
    private int fill(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            //标注1.向左滚动
//            dx为将要滑动的距离，如果 dx > 0，则是往左边滑动，则需要判断右边的边界，如果最后一个itemView完全显示出来后，在右边填充一个新的itemView。
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);
            //标注2.可见的最后一个itemView完全滑进来了，需要补充新的
            if (lastView.getRight() < getWidth()) {
                View scrap = null;
                //标注3.判断可见的最后一个itemView的索引，
                // 如果是最后一个，则将下一个itemView设置为第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
//                        如果索引是最后一个，则需要新填充的itemView为第0个，这样就可以实现往左边滑动时候无限循环了
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dx;
                }
                //标注4.将新的itemViewadd进来并对其测量和布局，将填充进去了。
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, lastView.getRight(), 0,
                        lastView.getRight() + width, height);
                return dx;
            }
        } else {
            //向右滚动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);

            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }


//    遍历所有添加进 RecyclerView 里的item，然后根据 itemView 的顶点位置进行判断，移除不可见的item。移除 itemView 调用 removeAndRecycleView(view, recycler) 方法，会对移除的item进行回收，然后存入 RecyclerView 的缓存里。

    /**
     * 回收界面不可见的view，只有对不可见的itemView进行回收，才能做到回收利用，防止内存爆增。
     */
    private void recyclerHideView(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (true)
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view == null) {
                    continue;
                }
                if (dx > 0) {
                    //标注1.向左滚动，移除左边不在内容里的view
                    if (view.getRight() < 0) {
                        removeAndRecycleView(view, recycler);
                        Log.d("", "循环: 移除 一个view childCount=" + getChildCount());
                    }
                } else {
                    //标注2.向右滚动，移除右边不在内容里的view
                    if (view.getLeft() > getWidth()) {
                        removeAndRecycleView(view, recycler);
                        Log.d("", "循环: 移除 一个view childCount=" + getChildCount());
                    }
                }
            }

    }


    /***
     * 事件列表
     */
    public interface OnSelectedPositionChangedListener {
        /**
         * 滚动后选中的item
         *
         * @param pos
         */
        void selectedPositionChanged(int pos, View view);

        /**
         * 滚动时选中的item
         *
         * @param pos
         */
        void scrollPositionChanged(int pos, View view);

    }

    public void setOnSelectedPositionChangedListener(OnSelectedPositionChangedListener listener) {
        this.listener = listener;
    }


}