package com.example.chen.adapter.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chen.adapter.R;
import com.example.chen.adapter.utils.MomentsUtils;
import com.example.chen.adapter.weidgt.MyLinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023.
 * @author fulin
 */
public abstract class LeftDelteAdapter<T> extends BaseArrayAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<T> list;
    private int res;
    //标记，滑动的item下标，无需还原
    public int pos = -1;
    protected int screenWidth;

    public static final String showType="showType";
    /***
     * 只显示左滑的布局中的删除按钮
     * */
    public static final int DELETETYPE=1;
    /**
     * 都不显示（禁用左滑动效果）
     * */
    public static final int DISABLEtYPE=2;
    /**
     * 左滑的布局都显示
     * */
    public static final int SHOWALL=3;

    public int type=3;
    public LeftDelteAdapter(Activity context, int res, List<T> list) {
        super(context, res, list);
        this.context = context;
        this.res = res;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 此方法必须重写
     * @param
     */
    public abstract void getView2(int position, View convertView, ViewGroup parent, ViewGroup container);
    /**
     * 点击删除
     */
    public abstract void clickDelete(int position);
    /**
     * 点击移出
     * */
    public abstract void clickRemove(int position);

    /**
     * item点击事件
     *
     * @param view
     * @param position*/
    public abstract void clickItem(View view, int position);

    /**
     * item长按事件
     * **/
    public abstract void longClickItem(View view, int position);
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        BlackListHolder holder = null;
        if (convertView != null) {
            holder = (BlackListHolder) convertView.getTag(R.id.orign);
        }
        if (holder == null) {
            View view=layoutInflater.inflate(res,null);
            convertView = layoutInflater.inflate(R.layout.left_delete, null);
            ((LinearLayout) convertView.findViewById(R.id.container)).addView(view);
            holder = new BlackListHolder();
            convertView.setTag(R.id.orign,holder);
            holder.sl = (HorizontalScrollView) convertView.findViewById(R.id.sl);
            holder.tv_del = (TextView) convertView.findViewById(R.id.tv_del);
            holder.tv_move_out = (TextView) convertView.findViewById(R.id.tv_move_out);
            holder.contiear = (MyLinearLayout) convertView.findViewById(R.id.container);
        }
        holder.contiear.setTagAdapter(this);
        getView2(position,convertView,parent,holder.contiear);
        holder.contiear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos!=-1){
                    closeAllItems();
                    return;
                }
                clickItem(view,position);
            }
        });
        holder.contiear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(pos!=-1){
                    closeAllItems();
                    return true;
                }
                longClickItem(view, position);
                return true;
            }
        });
        //设置宽度
        ViewGroup.LayoutParams params = holder.contiear.getLayoutParams();
        params.width = screenWidth;
        holder.contiear.setLayoutParams(params);


        holder.sl.setOnTouchListener(new View.OnTouchListener() {
                                         private int lastX = 0;
                                         private int biaoji = -9983761;

                                         Handler handler = new Handler() {
                                             @Override
                                             public void handleMessage(Message msg) {
                                                 super.handleMessage(msg);
                                                 View scroller = (View) msg.obj;
                                                 if (msg.what == biaoji) {
                                                     if (lastX == scroller.getScrollX()) {
                                                         //停止滑动
                                                         handleStop(scroller);
                                                     } else {
                                                         //每5毫秒检查是否停止滑动
                                                         handler.sendMessageDelayed(handler.obtainMessage(biaoji, scroller), 5);
                                                         lastX = scroller.getScrollX();
                                                     }
                                                 }
                                             }
                                         };

                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
//                                                     if (pos==position){//如果当前postion处于左滑动状态吗，立即返回
//                                                         return false;
//                                                     }
                                             if (pos != -1) {//如果有一个处于左滑动状态，则恢复列表，并返回
                                                 final HorizontalScrollView scroller = (HorizontalScrollView) v;
                                                 scroller.post(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         scroller.fullScroll(ScrollView.FOCUS_LEFT);
                                                     }
                                                 });
                                                 pos = -1;
                                                 notifyDataSetChanged();
                                                 return false;
                                             }
                                             if (event.getAction() == MotionEvent.ACTION_UP) {
                                                 handler.sendMessageDelayed(handler.obtainMessage(biaoji, v), 5);
                                             }
                                             return false;
                                         }

                                         //这里写滑动结束对应的事件
                                         private void handleStop(Object view) {
                                             final HorizontalScrollView scroller = (HorizontalScrollView) view;
                                             System.out.println(scroller.getScrollX());
                                             System.out.println(scroller.getHeight());
                                             System.out.print("float" + MomentsUtils.px2dip(context, scroller.getScaleX()));
                                             //do something
                                             int wid = scroller.getScrollX();
                                             //px转换dip（滑动的距离）
                                             int xx = MomentsUtils.px2dip(context, wid);

                                             System.out.print("float" + MomentsUtils.px2dip(context, wid));
                                             pos = position;
//                                                     notifyDataSetChanged();
                                             if (xx > 45) {
                                                 System.out.println("右边");
                                                 scroller.fullScroll(ScrollView.FOCUS_RIGHT);
                                                 notifyDataSetChanged();
                                             } else {
                                                 System.out.println("左边");
                                                 scroller.fullScroll(ScrollView.FOCUS_LEFT);
                                                 pos = -1;
                                             }

                                         }
                                     }
        );
        //左滑动
        if (position != pos) {
            holder.sl.fullScroll(ScrollView.FOCUS_LEFT);
        }
        /**
         * 侧拉删点击删除
         * */
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickDelete(position);

                //完成操作之后，侧滑关闭
                closeAllItems();
            }
        });
        holder.tv_del.setText(settvDelstr());
        /**
         * 侧拉点击恢复黑名单
         * */
        holder.tv_move_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRemove(position);
                //完成操作之后，侧滑关闭
                closeAllItems();
            }
        });
        holder.tv_move_out.setText(settvMoveOut());
//        type=((Bean)getItem(position)).getInt(showType);
        ShowType(holder.tv_del,holder.tv_move_out);

        return convertView;
    }
    /**
     * 设置显示模式
     * */
    private void ShowType(View delete, View move){
        if (type==DISABLEtYPE){
            delete.setVisibility(View.GONE);
            move.setVisibility(View.GONE);
        }else if(type==SHOWALL){
            delete.setVisibility(View.VISIBLE);
            move.setVisibility(View.VISIBLE);
        }else if (type==DELETETYPE){
            delete.setVisibility(View.VISIBLE);
            move.setVisibility(View.GONE);
        }
    }

    /**
     * 设置删除按钮文字
     */
    protected String settvDelstr() {
        return context.getString(R.string.delete);
    }

    /**
     * 设置移出按钮文字
     */
    protected String settvMoveOut() {
        return context.getString(R.string.remove);
    }


    public void closeAllItems() {
        // 遍历集合, 并关闭条目
        pos = -1;
        notifyDataSetChanged();
    }

    public class BlackListHolder {
        HorizontalScrollView sl;
        MyLinearLayout contiear;

        TextView tv_del;
        TextView tv_move_out;
    }

}
