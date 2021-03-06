package com.albert.study.slidingview;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albert.study.slidingviewlib.SlidingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add("#" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new TextAdapter(datas));

    }


    private class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;


        public TextViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            this.mTextView = itemView.findViewById(R.id.textView);
        }

        public void bind(String text) {
            this.mTextView.setText(text);
            SlidingView slidingView = (SlidingView) itemView;
            slidingView.resetViewState();
        }
    }

    private class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {
        private List<String> mDatas;

        public TextAdapter(List<String> datas) {
            mDatas = datas;
        }

        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            return new TextViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
            holder.bind(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

    }
}