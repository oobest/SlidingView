package com.albert.study.slidingviewlib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * <p>
 * 文件名称：DrawWithHandlerLayout
 * </p>
 * <p>
 * 文件描述：//带手柄的抽屉
 * </p>
 * <p>
 * 内容摘要：// 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * </p>
 * <p>
 * 其他说明：// 其它内容的说明
 * </p>
 * <p>
 * 修改记录1：//修改历史记录，包括修改日期、修改者及修改内容
 * </p>
 * <p>
 * <pre>
 * 修改日期：
 * 版 本 号：
 * 修 改 人：
 * 修改内容：
 * </pre>
 * <p>
 * 修改记录2：//修改历史记录，包括修改日期、修改者及修改内容
 * </p>
 *
 * @author oujf
 * @version 创建时间：2018/9/4 11:12
 */
public class DrawerWithHandlerLayout extends CoordinatorLayout {

    private int touchSlop;

    private View mHandlerView; //手柄

    private View mDrawerContentView; //抽屉内容

    private GestureDetector mGestureDetector;

    private OnDrawerListener mOnDrawerListener;

    public void setOnDrawerListener(OnDrawerListener onDrawerListener) {
        mOnDrawerListener = onDrawerListener;
    }

    public DrawerWithHandlerLayout(Context context) {
        this(context, null);
    }

    public DrawerWithHandlerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerWithHandlerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public interface OnDrawerListener {
        void onDrawStatus(int status);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandlerView = findViewById(R.id.albert_sliding_view_id_drawer_handler_view);
        mDrawerContentView = findViewById(R.id.albert_sliding_view_id_drawer_content_view);

        if (this.mHandlerView == null) {
            throw new NullPointerException("this.mHandlerView=null,can not find @id/albert_sliding_view_id_drawer_handler_view");
        }

        if (this.mDrawerContentView == null) {
            throw new NullPointerException("this.mHandlerView=null,can not find @id/albert_sliding_view_id_drawer_content_view");
        }

        if (mDrawerContentView != null) {
            CoordinatorLayout.LayoutParams rightViewLayoutParams = (LayoutParams) mDrawerContentView.getLayoutParams();
            rightViewLayoutParams.setBehavior(new DrawerContentViewBehavior());
        }

        mHandlerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandlerView.getTranslationX() == -mDrawerContentView.getWidth()) {
                    closeDrawer();
                } else if (mHandlerView.getTranslationX() == 0) {
                    openDrawer();
                }
            }
        });
        mGestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mHandlerView.getTranslationX() == -mDrawerContentView.getWidth()) {
                    closeDrawer();
                }
                return super.onSingleTapUp(e);
            }
        });
    }

    /**
     * 打开抽屉
     */
    public void openDrawer() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHandlerView, "TranslationX", mHandlerView.getTranslationX(), -mDrawerContentView.getWidth());
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(this, "BackgroundColor", 0x00000000, 0x9a000000);
        animatorSet.playTogether(animator, colorAnimator);
        animatorSet.setDuration(200);
        animatorSet.start();
        if (mOnDrawerListener != null) {
            mOnDrawerListener.onDrawStatus(1);
        }
    }

    /**
     * 关闭抽屉
     */
    public void closeDrawer() {
        if (mHandlerView.getTranslationX() != 0) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator animator = ObjectAnimator.ofFloat(mHandlerView, "TranslationX", mHandlerView.getTranslationX(), 0);
            ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(this, "BackgroundColor", 0x9a000000, 0x00000000);
            animatorSet.playTogether(animator, colorAnimator);
            animatorSet.setDuration(200);
            animatorSet.start();
        }
        if (mOnDrawerListener != null) {
            mOnDrawerListener.onDrawStatus(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 如果抽屉打开，则事件在抽屉图层消耗掉，并没抽屉下面的图层获取点击事件
        if (mHandlerView.getTranslationX() == -mDrawerContentView.getWidth()) {
            super.dispatchTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果点击区域外，抽屉区域外，则关闭抽屉
        if (mHandlerView.getTranslationX() == -mDrawerContentView.getWidth() && ev.getX() < mHandlerView.getX()) {
            mGestureDetector.onTouchEvent(ev);
            return true;
        }
        return false;
    }
}
