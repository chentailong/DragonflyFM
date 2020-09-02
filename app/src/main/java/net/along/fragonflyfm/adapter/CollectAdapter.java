package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.activities.ProgramActivity;
import net.along.fragonflyfm.entity.SearchesData;
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
    private List<SearchesData> data;
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
        holder.number.setText(radio.getAudience_count()+ "");
        holder.collect.setOnClickListener((view) -> {
            holder.collect.setImageResource(R.drawable.ic_no_collect);
            Toast.makeText(context, "你取消了这个收藏", Toast.LENGTH_SHORT).show();
            int channelId = radio.getChannel_id();
            String title = radio.getTitle();
            SugarRecord.executeQuery("delete from COLLECT_RADIO where channelId=? " +
                    "and title=?", channelId + "", title);
        });
        Glide.with(context).load(radio.getImgUrl()).error(R.drawable.sh).into(holder.img);

        holder.card_view.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProgramActivity.class);
            intent.putExtra("channel_id", radio.getChannel_id());
            intent.putExtra("channelName", radio.getTitle());
            intent.putExtra("cover", radio.getImgUrl());
            intent.putExtra("channel", radio.getTitle());
            intent.putExtra("previous", "我的收藏");
            intent.putExtra("audience_count", radio.getAudience_count());
            if (radio.getStart_time() != null && radio.getDuration() != 0 && radio.getIdes() != 0) {
                intent.putExtra("startTime", radio.getStart_time());
                intent.putExtra("programId", radio.getIdes());
                intent.putExtra("duration", radio.getDuration());
            }else{
                Toast.makeText(context, "暂无节目播放，请选择其他电台节目", Toast.LENGTH_SHORT).show();
                return;
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    class CollectItem extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        ImageView collect;
        TextView number;
        CardView card_view;

        public CollectItem(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fragment_radio_image);
            name = itemView.findViewById(R.id.radio_station_name);
            collect = itemView.findViewById(R.id.radio_collection);
            card_view = itemView.findViewById(R.id.card_radio_view);
            number = itemView.findViewById(R.id.radio_listeners);
        }
    }
}
