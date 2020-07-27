package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/13
 **/

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private List<T> list;

    public BaseAdapter(Context context, List<T> list) {
        this.context = context;
        setList(list);
        inflater = LayoutInflater.from(context);

    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public List<T> getList() {
        return list;
    }


    public void setList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
