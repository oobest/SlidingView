package com.albert.study.slidingviewlib;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 右边View的Behavior
 */
public class RightViewBehavior extends CoordinatorLayout.Behavior<View>{

    public RightViewBehavior() {
        super();
    }

    public RightViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.albert_sliding_view_id_sliding_move_view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float x = dependency.getX();
        int width = dependency.getWidth();
        child.setX(width+x);
        return true;
    }
}
