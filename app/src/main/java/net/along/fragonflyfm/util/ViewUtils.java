package net.along.fragonflyfm.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.WebServerConnection;
import net.lzzy.listlib.ViewHolder;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/
public class ViewUtils {
    /**
     * 计算设备额头的状态栏高度
     * @return px为单位的高度值
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static void gotoSettings(Context context, GoBackFunction funcGoBack) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_settings, null);
        Pair<String, String> url = WebServerConnection.loadServerParams();
        EditText edtIp = view.findViewById(R.id.dialog_setting_edt_ip);
        EditText edtPort = view.findViewById(R.id.dialog_setting_edt_port);
        edtIp.setText(url.first);
        edtPort.setText(url.second);
        new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("取消", (dialog, which) -> funcGoBack.gotoMain(context))
                .setPositiveButton("保存", (dialog, which) -> {
                    String ip = edtIp.getText().toString();
                    String port = edtPort.getText().toString();
                    if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
                        Toast.makeText(context, "信息不完整", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    WebServerConnection.saveServerParams(context, ip, port);
                    funcGoBack.gotoMain(context);
                })
                .show();
    }

    /**
     * 需要弹出服务器设置对话框的activity实现该接口，然后将接口对象传递给gotoSettings方法
     */
    public interface GoBackFunction{
        void gotoMain(Context context);
    }

    private static AlertDialog dialog;

    public static void showDialog(Context context, String message) {
        if (dialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
            TextView tv = view.findViewById(R.id.dialog_progress_tv);
            tv.setText(message);
            dialog = new AlertDialog.Builder(context).create();
            dialog.setView(view);
        }
        dialog.show();
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public abstract static class GenericGroupAdapter<P, C> extends BaseExpandableListAdapter{
        private final Context context;
        private final int groupLayout;
        private final int childLayout;
        private final List<P> parents;
        private final List<List<C>> groupChildren;
        public GenericGroupAdapter(Context context, int groupLayout, int childLayout,
                                   List<P> parents, List<List<C>> groupChildren){
            this.context = context;
            this.groupLayout = groupLayout;
            this.childLayout = childLayout;
            this.parents = parents;
            this.groupChildren = groupChildren;
        }

        @Override
        public int getGroupCount() {
            return parents.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groupChildren.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parents.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groupChildren.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            P p = parents.get(groupPosition);
            ViewHolder holder = ViewHolder.getInstance(context, groupLayout, convertView, parent);
            populateParent(holder, p, isExpanded, groupPosition);
            return holder.getConvertView();
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            C c = groupChildren.get(groupPosition).get(childPosition);
            ViewHolder holder = ViewHolder.getInstance(context, childLayout, convertView, parent);
            populateChild(holder, c, isLastChild);
            return holder.getConvertView();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        /**
         * 填充分组布局视图
         * @param holder 分组布局视图持有者
         * @param p 分组数据对象
         * @param isExpand 该分组是否展开
         */
        public abstract void populateParent(ViewHolder holder, P p, boolean isExpand, int groupPosition);

        /**
         * 填充子布局视图
         * @param holder 子布局视图组件持有者
         * @param c 子布局的数据对象
         * @param isLastChild 是否最后一个数据
         */
        protected abstract void populateChild(ViewHolder holder, C c, boolean isLastChild);
    }
}
