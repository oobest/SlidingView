package com.albert.study.slidingview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 *  左边View的Behavior
 */
public class LeftViewBehavior extends CoordinatorLayout.Behavior<View>{

    public LeftViewBehavior() {
        super();
    }

    public LeftViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //dragV 依赖于 moveV
        return dependency.getId() == R.id.id_sliding_move_view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float x = dependency.getX();
        child.setX(x-child.getWidth());
        return true;
    }
}
