package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.entity.Searches;

import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/13
 **/

public class SearchesAdapter extends BaseAdapter<Searches> {

    public SearchesAdapter(Context context, List<Searches> list){
        super(context,list);
    }
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
         if (viewHolder == null) {
             viewHolder = new ViewHolder();
             convertView = getInflater().inflate(R.layout.fragment_searches_view,null);
             viewHolder.title = convertView.findViewById(R.id.fragment_searches_station_name);
             viewHolder.cover = convertView.findViewById(R.id.fragment_searches_image);
             viewHolder.audience_count = convertView.findViewById(R.id.fragment_searches_number_of_listeners);
             convertView.setTag(viewHolder);
         }else{
             viewHolder = (ViewHolder) convertView.getTag();
         }
         Searches searches = getList().get(position);
         viewHolder.title.setText(searches.getTitle());
         viewHolder.audience_count.setText(searches.getAudience_count());
         viewHolder.cover.setImageBitmap(searches.getBitmap());
         return convertView;
     }

    class ViewHolder {
         TextView title;   //电台名称
         ImageView cover;//电台图片
         TextView audience_count;   //观看人数
    }
 }
