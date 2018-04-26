package com.albert.study.slidingview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class SlidingView extends CoordinatorLayout {


    private View mRightView;

    private View mLeftView;

    private View mMoveView;

    private float mLastX;

    private float mDistance;

    private boolean isSliding = false; //是否处于滑动状态


    private boolean rightSlideEnable = true;

    private boolean leftSlideEnable = false;


    public SlidingView(Context context) {
        this(context, null);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDistance = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean isRightSlideEnable() {
        return rightSlideEnable;
    }

    /**
     * 是否可以右滑
     *
     * @param rightSlideEnable
     */
    public void setRightSlideEnable(boolean rightSlideEnable) {
        if (this.mLeftView == null) {
            this.rightSlideEnable = false;
        } else {
            this.rightSlideEnable = rightSlideEnable;
        }
    }

    public boolean isLeftSlideEnable() {
        return leftSlideEnable;
    }

    /**
     * 是否可以左滑
     *
     * @param leftSlideEnable
     */
    public void setLeftSlideEnable(boolean leftSlideEnable) {
        if (this.mRightView == null) {
            this.leftSlideEnable = false;
        } else {
            this.leftSlideEnable = leftSlideEnable;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mRightView = findViewById(R.id.id_sliding_right_view);
        this.mLeftView = findViewById(R.id.id_sliding_left_view);
        this.mMoveView = findViewById(R.id.id_sliding_move_view);

        if (this.mMoveView == null) {
            throw new NullPointerException("this.mMoveView=null,can not find @id/id_sliding_move_view");
        }

        if (mRightView != null) {
            CoordinatorLayout.LayoutParams rightViewLayoutParams = (LayoutParams) mRightView.getLayoutParams();
            rightViewLayoutParams.setBehavior(new RightViewBehavior());
            leftSlideEnable = true;
        } else {
            leftSlideEnable = false;
        }

        if (mLeftView != null) {
            CoordinatorLayout.LayoutParams leftViewLayoutParams = (LayoutParams) mLeftView.getLayoutParams();
            leftViewLayoutParams.setBehavior(new LeftViewBehavior());
            rightSlideEnable = true;
        } else {
            rightSlideEnable = false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isSliding && Math.abs(x - mLastX) > mDistance) {
                    isSliding = true;
                }

                if (isSliding) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float moveViewX = mMoveView.getX();
                    float moveDistance = x - mLastX; //手指滑动的距离

                    float tempX = moveViewX + moveDistance; //预计moveView滑动后的x

                    // moveView的X的区间：-rightViewWidth < x < leftViewWidth
                    if (tempX >= 0) { //向右滑动
                        if (rightSlideEnable) {
                            int leftViewWidth = mLeftView.getWidth();
                            mMoveView.setX(Math.min(tempX, leftViewWidth));
                        } else {
                            mMoveView.setX(0);
                        }
                    } else { //向左滑动
                        if (leftSlideEnable) {
                            int rightViewWidth = mRightView.getWidth();
                            mMoveView.setX(Math.max(-rightViewWidth, tempX));
                        } else {
                            mMoveView.setX(0);
                        }
                    }

                    mLastX = x;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSliding) {
                    float moveViewX = mMoveView.getX();

                    if (leftSlideEnable && (moveViewX < -mRightView.getWidth() / 2)) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(mMoveView, "X", mMoveView.getX(), -mRightView.getWidth());
                        animator.setDuration(200);
                        animator.start();
                    } else if (rightSlideEnable && (moveViewX > mLeftView.getWidth() / 2)) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(mMoveView, "X", mMoveView.getX(), mLeftView.getWidth());
                        animator.setDuration(200);
                        animator.start();
                    } else if (leftSlideEnable || rightSlideEnable) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(mMoveView, "X", mMoveView.getX(), 0f);
                        animator.setDuration(200);
                        animator.start();
                    }

                    isSliding = false;
                    return true;
                }

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSliding ? true : super.onTouchEvent(ev);
    }

    /**
     * 重置view状态
     */
    public void resetViewState() {
        mMoveView.setX(0f);
        isSliding = false;
    }

}
