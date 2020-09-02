package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.record.CollectRadio;

import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/9/2
 **/

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.CollectItem> {
    private Context context;
    private List<CollectRadio> tables;//数据
    private String TAG = "CollectAdapter";

    public CollectAdapter(Context context, List<CollectRadio> tables) {
        this.tables = tables;
        this.context = context;
    }

    //创建列表
    @NonNull
    @Override
    public CollectItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_radio_view, null);
        return new CollectItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectItem holder, int position) {
        CollectRadio radio = tables.get(position);
        holder.name.setText(radio.getTitle());
        holder.collect.setOnClickListener((view) -> {
            holder.collect.setImageResource(R.drawable.ic_no_collect);
            Toast.makeText(context, "你取消了这个收藏", Toast.LENGTH_SHORT).show();
            int channelId = radio.getChannel_id();
            String title = radio.getTitle();
            SugarRecord.executeQuery("delete from COLLECT_RADIO where channelId=? " +
                    "and title=?", channelId + "", title);
            Log.e(TAG, "删除了一条数据——" + title);
        });
        Glide.with(context).load(radio.getImgUrl()).error(R.drawable.sh).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    class CollectItem extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        ImageView collect;

        public CollectItem(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fragment_radio_image);
            name = itemView.findViewById(R.id.radio_station_name);
            collect = itemView.findViewById(R.id.radio_collection);
        }
    }
}
