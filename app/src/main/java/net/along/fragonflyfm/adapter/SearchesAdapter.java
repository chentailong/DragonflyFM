package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.util.Log;
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
    private static final String TAG = "SearchesAdapter";
    private onSearchesItemClickListener mItemClickListener  = null;

    public SearchesAdapter(Context context, List<Searches> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.fragment_searches_view, null);
            viewHolder.title = convertView.findViewById(R.id.fragment_searches_station_name);
            viewHolder.cover = convertView.findViewById(R.id.fragment_searches_image);
            viewHolder.audience_count = convertView.findViewById(R.id.fragment_searches_number_of_listeners);
            viewHolder.content_id = convertView.findViewById(R.id.searches_content_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Searches searches = getList().get(position);
        viewHolder.title.setText(searches.getTitle());
        viewHolder.audience_count.setText(searches.getAudience_count());
        viewHolder.cover.setImageBitmap(searches.getBitmap());
        viewHolder.content_id.setId(searches.getContent_id());
        //处理点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position,getList().get(position));
                }
                Log.d(TAG, "onClick: 你点击了：" + position);
            }
        });
        return convertView;
    }

    /**
     * 暴露接口，使SearchesFragment能够使用，实现点击事件
     * @param listener
     */
    public void setOnSearchesItemClickListener(onSearchesItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface onSearchesItemClickListener {
        void onItemClick(int position, Searches searches);
    }

    class ViewHolder {
        TextView title;   //电台名称
        ImageView cover;//电台图片
        TextView audience_count;   //观看人数
        TextView province;  //省份
        TextView  content_id;  //电台ID
    }
}
