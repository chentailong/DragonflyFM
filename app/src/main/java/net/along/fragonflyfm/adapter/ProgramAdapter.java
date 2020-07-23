package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.entity.Program;
import net.along.fragonflyfm.entity.Searches;

import java.util.List;


/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/16
 **/

public class ProgramAdapter extends BaseAdapter<Program> {

    private static final String TAG = "ProgramAdapter";
    private onProgramItemClickListener mItemClickListener  = null;

    public ProgramAdapter(Context context, List<Program> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            view = getInflater().inflate(R.layout.program_list_view, null);
            viewHolder.title = view.findViewById(R.id.program_title);
            viewHolder.username = view.findViewById(R.id.program_username);
            viewHolder.audience_count = view.findViewById(R.id.program_people);
            viewHolder.start_time = view.findViewById(R.id.program_start_time);
            viewHolder.content_id = view.findViewById(R.id.program_content_id);
            viewHolder.usernames = view.findViewById(R.id.program_usernames);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Program program =getList().get(i);
        viewHolder.title.setText(program.getTitle());
        viewHolder.username.setText(program.getUsername());
        viewHolder.audience_count.setText(program.getAudience_count());
        viewHolder.start_time.setText("start :"+program.getStart_time());
        viewHolder.content_id.setId(program.getContent_id());
        viewHolder.usernames.setText("    "+program.getUsernames());
        return view;
    }

    /**
     * 暴露接口，使SearchesFragment能够使用，实现点击事件
     * @param listener
     */
    public void setOnProgramItemClickListener(onProgramItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface onProgramItemClickListener {
        void onItemClick(int position, Searches searches);
    }

    class ViewHolder {
        TextView title;  //节目名称
        TextView username;  //主播
        TextView usernames;
        TextView audience_count;  //观看人数
        TextView start_time; //开始时间
        TextView content_id; //电台id
    }

}
