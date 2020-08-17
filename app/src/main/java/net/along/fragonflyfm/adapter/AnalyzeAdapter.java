package net.along.fragonflyfm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.along.fragonflyfm.record.AppVisitCount;

import java.util.List;

import fm.qingting.qtsdk.entity.Content;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/8/17
 **/

public class AnalyzeAdapter extends BaseAdapter {
    private Content mContent;
    private List<AppVisitCount> mList;

    public AnalyzeAdapter(Content content, List<AppVisitCount> list) {
        this.mContent = content;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public AppVisitCount getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       
        return null;
    }
}
