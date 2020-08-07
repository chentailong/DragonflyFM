package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.activities.PlayerActivity;
import net.along.fragonflyfm.activities.ProgramActivity;
import net.along.fragonflyfm.dataBase.ProgramDataBase;
import net.along.fragonflyfm.entity.Broadcasters;
import net.along.fragonflyfm.entity.Player;
import net.along.fragonflyfm.entity.Program;
import net.along.fragonflyfm.service.PlayService;
import net.along.fragonflyfm.util.DataBaseUtil;
import net.along.fragonflyfm.util.GetTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
    private int StartProgram;

    public ProgramAdapter(Context context, List<Program> mProgram) {
        this.mProgram = mProgram;
        this.context = context;
    }


    @NonNull
    @Override
    public ProgramItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list_view, null);
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
        holder.hostView.setText(host);
        holder.stateImg.setImageResource(R.drawable.ic_trumpet);
        holder.titleView.setText(entity.getTitle());
        holder.durationView.setText("[" + entity.getStart_time() + "-" + entity.getEnd_time() + "]");

        int temp=((ProgramActivity)context).programId;
        String startTime=((ProgramActivity)context).startTime;
        if (entity.getProgram_id() == temp && entity.getStart_time().equals(startTime)) {
            Log.e("ProgramAdapter","context.programId="+temp+"; entity="+entity.getProgram_id());
            holder.audience_count.setText(((ProgramActivity) context).count + "");
            holder.countImg.setVisibility(View.VISIBLE);
            holder.audience_count.setVisibility(View.VISIBLE);
            holder.mRelativeLayout.setBackground(context.getDrawable(R.drawable.program_be_show_style));
            StartProgram = position;
        }
        final String finalHost = host;
        holder.mRelativeLayout.setOnClickListener(view -> {
            if (position > StartProgram) {
                Toast.makeText(context, "节目尚未开始", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("channelName", ((ProgramActivity) context).channelName);
            intent.putExtra("cover", ((ProgramActivity) context).cover);
            intent.putExtra("title", entity.getTitle());
            intent.putExtra("broadcaster", finalHost);
            intent.putExtra("start_time", entity.getStart_time());
            intent.putExtra("end_time", entity.getEnd_time());
            intent.putExtra("duration", entity.getDuration());

            //记录数据
//            updateProgramTable(entity.getProgram_id(), entity.getTitle());

            List<Player> playingList = new ArrayList<>();
            for (Program itemEntity : mProgram) {
                mPlayingItem = new Player();
                String host1 = "";
                for (Broadcasters hostObj : itemEntity.getBroadcasters()) {
                    host1 = host1 + "  " + hostObj.getUsername();
                }
                mPlayingItem.setBroadcasters(host1);
                mPlayingItem.setChannelId(((ProgramActivity) context).channelId);
                mPlayingItem.setPlayUrl(itemEntity.getStart_time());
                mPlayingItem.setEndTime(itemEntity.getEnd_time());
                String playUrl = GetTime.changeToPlayUrl(((ProgramActivity) context).channelId,
                        itemEntity.getStart_time(), itemEntity.getEnd_time());
                mPlayingItem.setPlayUrl(playUrl);
                mPlayingItem.setProgramName(itemEntity.getTitle());
                playingList.add(mPlayingItem);
            }
//            PlayService.currentIndex = position;
            PlayService.setPlayingList(playingList);
            Intent serIntent = new Intent(context, PlayService.class);
            serIntent.putExtra("startIndex", position);
            context.startService(serIntent);
            context.startActivity(intent);
        });
    }

    /**
     * 更新数据，当点击列表项后，调用这个方法
     */
    private void updateProgramTable(int programId, String programName) {
        long nowTimeStamp = System.currentTimeMillis();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date(nowTimeStamp));
        Iterator records = ProgramDataBase.findAll(ProgramDataBase.class);
        while (records.hasNext()) {
            ProgramDataBase thisRecord = (ProgramDataBase) records.next();
            long thisTimeStamp = thisRecord.getTimeStamp();
            Calendar thisCalendar = Calendar.getInstance();
            thisCalendar.setTime(new Date(thisTimeStamp));
            if (DataBaseUtil.isToday(nowCalendar, thisCalendar)) {
                int thisProgramId = thisRecord.getProgramId();
                String thisProgramName = thisRecord.getProgramName();
                if (programId == thisProgramId && programName.equals(thisProgramName)) {
                    long thisId = thisRecord.getId();
                    int updateCount = thisRecord.getCount();
                    ProgramDataBase.executeQuery("update Prefer_Program_Table set count=? where id=?",
                            updateCount + 1 + "", thisId + "");
                    Log.e("updateProgramTable", "更新了今天这个节目的点击次数");
                    return;
                } else {
                    continue;
                }
            }
        }
        ProgramDataBase mDataBase = new ProgramDataBase();
        mDataBase.setProgramId(programId);
        mDataBase.setCount(1);
        mDataBase.setProgramName(programName);
        mDataBase.setTimeStamp(nowTimeStamp);
        mDataBase.save();
    }


    @Override
    public int getItemCount() {
        return mProgram.size();
    }

    class ProgramItem extends RecyclerView.ViewHolder {
        ImageView stateImg;
        ImageView countImg;
        TextView titleView;
        TextView hostView;
        TextView audience_count;
        TextView durationView;
        RelativeLayout mRelativeLayout;


        public ProgramItem(@NonNull View itemView) {
            super(itemView);
            stateImg = itemView.findViewById(R.id.program_trumpet_imageView);
            countImg = itemView.findViewById(R.id.program_people_imageView);
            titleView = itemView.findViewById(R.id.program_title);
            hostView = itemView.findViewById(R.id.program_username);
            audience_count = itemView.findViewById(R.id.audience_count);
            durationView = itemView.findViewById(R.id.program_duration);
            mRelativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
