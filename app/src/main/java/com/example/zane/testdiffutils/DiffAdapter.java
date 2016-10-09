package com.example.zane.testdiffutils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zane on 16/10/8.
 * Email: zanebot96@gmail.com
 */

public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.MyViewHolder>{

    private List<Data> mDatas;
    private LayoutInflater inflater;
    private Context mContext;

    public DiffAdapter(Context mContext, List<Data> mDatas){
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data = mDatas.get(position);
        holder.mNameText.setText(data.getName());
        holder.mAgeText.setText(String.valueOf(data.getAge()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position);
        } else {
            for (int i = 0; i < payloads.size(); i++){
                Bundle payload = (Bundle) payloads.get(i);
                for (String key : payload.keySet()){
                    switch (key){
                        case "KEY_NAME":
                            Log.i("adapter", payload.getString(key));
                            holder.mNameText.setText(payload.getString(key));
                            break;
                        case "KEY_AGE":
                            holder.mAgeText.setText(payload.getInt(key));
                            break;
                    }
                }
            }
        }
    }

    /**
     * 这里不用再去调用notifyItemxxxx(),全靠DiffUtils去帮我们智能调用了
     * @param mDatas
     */
    public void setDatas(List<Data> mDatas){
        this.mDatas = mDatas;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameText;
        public TextView mAgeText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.name_text_item);
            mAgeText = (TextView) itemView.findViewById(R.id.age_text_item);
        }
    }
}
