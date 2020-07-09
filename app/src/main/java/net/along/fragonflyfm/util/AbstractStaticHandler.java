package net.along.fragonflyfm.util;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/
public abstract class AbstractStaticHandler<T> extends Handler {
    private final WeakReference<T> context;
    public AbstractStaticHandler(T context){
        this.context = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = this.context.get();
        handleMessage(msg,t);
    }

    /**
     * 处理消息的业务逻辑
     * @param msg Message对象
     * @param context 当前Activity对象
     */
    public abstract void handleMessage(Message msg, T context);
}
