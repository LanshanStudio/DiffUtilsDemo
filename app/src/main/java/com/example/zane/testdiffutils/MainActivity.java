package com.example.zane.testdiffutils;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DiffAdapter adapter;
    private DiffCallback callback;
    private List<Data> mDatas;
    private List<Data> mNewDatas;
    private RefreshHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        init();
    }

    private void init() {
        mNewDatas = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DiffAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        handler = new RefreshHandler(this);
    }

    private void initDatas(){
        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Data data = new Data("Zane "+i, 20);
            data.setID(i);
            mDatas.add(data);
        }
    }

    public void onRefresh(View view){
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        /**
         * 不要重复点击,不然这里很明显会报并发异常
         */
        for (Data data : mDatas){
            mNewDatas.add(data);
        }

        Data data = new Data("徐志", 20);
        data.setID(mDatas.size());
        mNewDatas.add(5, data);
        mNewDatas.get(1).setName("徐志");
        mNewDatas.get(2).setAge(200);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(mDatas, mNewDatas));
                Message msg = new Message();
                msg.obj = diffResult;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private final static class RefreshHandler extends Handler{

        private WeakReference<MainActivity> reference;

        public RefreshHandler(MainActivity activity){
            reference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference != null){
                MainActivity a = reference.get();
                DiffUtil.DiffResult result = (DiffUtil.DiffResult) msg.obj;
                result.dispatchUpdatesTo(a.adapter);
                //Log.i("MainActivity2", mDatas.get(1).getName());
                a.mDatas = a.mNewDatas;
                a.adapter.setDatas(a.mDatas);
                a.recyclerView.smoothScrollToPosition(0);
            }
        }
    }

}
