package com.example.zane.testdiffutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

/**
 * Created by Zane on 16/10/8.
 * Email: zanebot96@gmail.com
 */

public class DiffCallback extends DiffUtil.Callback{

    private List<Data> oldListDatas;
    private List<Data> newListDatas;

    public DiffCallback(List<Data> oldDatas, List<Data> newDatas){
        oldListDatas = oldDatas;
        newListDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return oldListDatas.size();
    }

    @Override
    public int getNewListSize() {
        return newListDatas.size();
    }

    /**
     * 最先调用,先判断是不是同一个item,通过item的唯一标志来判断,比如ID
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (newListDatas.get(newItemPosition).getID() == oldListDatas.get(oldItemPosition).getID()){
            return true;
        }
        return false;
    }


    /**
     * areItemsTheSame()返回True才会第二个调用
     * 用来进一步判断item的数据是否发生改变,改变(返回false)才会调用notifyItemxxxxx()方法
     * 如果返回false那么会继续调用下面的函数,当然你可以不重写下面的函数,如果不重写那么就直接
     * 默认返回null了,那么Adapter的onBindeViewHolder的第三个参数就是空,
     * 当然,重写了刷新效率更高
     * 这里可以直接调用Bean类的equals了其实
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (!newListDatas.get(newItemPosition).getName().equals(oldListDatas.get(oldItemPosition).getName())){
            return false;
        } else if (newListDatas.get(newItemPosition).getAge() != oldListDatas.get(oldItemPosition).getID()){
            return false;
        }
        Log.i("callback", "position" + newItemPosition + " new name " + newListDatas.get(newItemPosition).getName() + " old name " + oldListDatas.get(oldItemPosition).getName());
        return true;
    }


    /**
     * 默认返回null
     * 在这里我们可以根据判断来将改变了的字段写入Bundle
     * 回传到onBindViewHolder()的第三个参数,直接取出来更新,效率更高
     * 这里回传的参数会在Adapter里面自动通过notifyItemxxxxx()方法将所有合并的plyload
     * 合并起来,然后回传给onBindViewHolder()方法
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //return super.getChangePayload(oldItemPosition, newItemPosition);
        Data newData = newListDatas.get(newItemPosition);
        Data oldData = oldListDatas.get(oldItemPosition);
        Bundle payload = new Bundle();
        Log.i("callback", "dddifff " + newItemPosition + " " + oldItemPosition);
        if (!newData.getName().equals(oldData.getName())){
            Log.i("callback", newData.getName());
            payload.putString("KEY_NAME", newData.getName());
        }
        if (newData.getAge() != oldData.getAge()){
            payload.putInt("KEY_AGE", newData.getAge());
        }

        if (payload.size() == 0){
            return null;
        }

        return payload;
    }
}
