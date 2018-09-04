package com.albert.study.slidingviewlib;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>
 * 文件名称：DrawerContentViewBehavior
 * </p>
 * <p>
 * 文件描述：//
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
 * @version 创建时间：2018/9/4 14:16
 */
public class DrawerContentViewBehavior extends CoordinatorLayout.Behavior<View>{

    public DrawerContentViewBehavior() {
        super();
    }

    public DrawerContentViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.albert_sliding_view_id_drawer_handler_view;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float x = dependency.getX();
        int width = dependency.getWidth();
        child.setX(width+x);
        return true;
    }
}
