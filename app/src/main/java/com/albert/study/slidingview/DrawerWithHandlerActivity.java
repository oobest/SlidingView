package com.albert.study.slidingview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.albert.study.slidingviewlib.DrawerWithHandlerLayout;

/**
 * <p>
 * 文件名称：DrawerWithHandlerActivity
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
 * @version 创建时间：2018/9/4 14:22
 */
public class DrawerWithHandlerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_with_handler);

        DrawerWithHandlerLayout drawerLayer = findViewById(R.id.drawerLayer);

    }
}
