package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.activities.PlayerActivity;
import net.along.fragonflyfm.activities.ProgramActivity;
import net.along.fragonflyfm.entity.Broadcasters;
import net.along.fragonflyfm.entity.Player;
import net.along.fragonflyfm.entity.Program;
import net.along.fragonflyfm.service.PlayUtil;
import net.along.fragonflyfm.util.GetTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/16
 **/

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramItem> {
    private static final String TAG = "ProgramAdapter";
    private List<Program> mProgram;
    private Context context;
    private Player mPlayingItem;

    public ProgramAdapter(Context context, List<Program> mProgram) {
        this.mProgram = mProgram;
        this.context = context;
    }


    @NonNull
    @Override
    public ProgramItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list_view, parent, false);
        ProgramItem item = new ProgramItem(view);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramItem holder, int position) {
        final Program entity = mProgram.get(position);
        List<Broadcasters> broadcasters = entity.getBroadcasters();
        String host = "";
        for (Broadcasters hostObj : broadcasters) {
            host = host + "  " + hostObj.getUsername();
        }
        String audience_counts = String.valueOf(entity.getDuration());
        holder.audience_count.setText(audience_counts);
        holder.hostView.setText(host);
        holder.stateImg.setImageResource(R.drawable.ic_trumpet);
        holder.titleView.setText(entity.getTitle());
        holder.durationView.setText("[" + entity.getStart_time() + "-" + entity.getEnd_time() + "]");

        final String finalHost = host;
        holder.mRelativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("channelName", ((ProgramActivity) context).channelName);
            intent.putExtra("cover", ((ProgramActivity) context).cover);
            intent.putExtra("title", entity.getTitle());
            intent.putExtra("broadcaster", finalHost);
            intent.putExtra("start_time", entity.getStart_time());
            intent.putExtra("end_time", entity.getEnd_time());
            intent.putExtra("duration", entity.getDuration());
            List<Player> playingList = new ArrayList<>();
            for (Program itemEntity : mProgram) {
                mPlayingItem = new Player();
                String host1 = "";
                for (Broadcasters hostObj : itemEntity.getBroadcasters()) {
                    host1 = host1 + "  " + hostObj.getUsername();
                }
                mPlayingItem.setBroadcasters(host1);
                mPlayingItem.setChannelId(((ProgramActivity) context).channelId);
                mPlayingItem.setStartTime(itemEntity.getStart_time());
                mPlayingItem.setEndTime(itemEntity.getEnd_time());
                String playUrl = GetTime.changeToPlayUrl(((ProgramActivity) context).channelId,
                        itemEntity.getStart_time(), itemEntity.getEnd_time());
                mPlayingItem.setPlayUrl(playUrl);
                mPlayingItem.setProgramName(itemEntity.getTitle());
                playingList.add(mPlayingItem);
            }
            PlayUtil.setPlayingList(playingList);
            context.startService(new Intent(context, PlayUtil.class));
            context.startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return mProgram.size();
    }

    class ProgramItem extends RecyclerView.ViewHolder {
        ImageView stateImg;
        TextView titleView;
        TextView hostView;
        TextView audience_count;
        TextView durationView;
        RelativeLayout mRelativeLayout;


        public ProgramItem(@NonNull View itemView) {
            super(itemView);
            stateImg = itemView.findViewById(R.id.program_trumpet_imageView);
            titleView = itemView.findViewById(R.id.program_title);
            hostView = itemView.findViewById(R.id.program_username);
            audience_count = itemView.findViewById(R.id.audience_count);
            durationView = itemView.findViewById(R.id.program_duration);
            mRelativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
