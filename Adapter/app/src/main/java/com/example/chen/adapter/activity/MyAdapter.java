package com.example.chen.adapter.activity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.adapter.R;
import com.example.chen.adapter.adapter.LeftDelteAdapter;

import java.util.List;

/**
 * Created by fulin on 2016/4/5 0005.
 */
public class MyAdapter  extends LeftDelteAdapter {
    int sign; //用来标记是哪种情况下的adapter

    private FolderHolder holder;

    public MyAdapter(Activity activity, List noteFolderData, int sign) {
        super(activity, R.layout.item_notefolder, noteFolderData);
        this.activity = activity;
        this.sign = sign;
        setService(new Myservice());
    }
    class  Myservice implements IService{
        @Override
        public boolean add(Object item, String query) {
            Info info= (Info)item;
            return info.getText().indexOf(query)!=-1;
        }
    }
    @Override
    public void getView2(int position, View convertView, ViewGroup parent, ViewGroup container) {

        holder = (FolderHolder) convertView.getTag();
        Object obj = convertView.getTag();
        if (obj != null) {
            holder = (FolderHolder) convertView.getTag();
        }
        if (holder == null) {
            holder = new FolderHolder();
            View view = container;
            holder.folderTitle = (TextView) view.findViewById(R.id.folder_title);
            holder.noteNum = (TextView) view.findViewById(R.id.folder_num);
            convertView.setTag(holder);
        }
        Info info= (Info)getItem(position);
        holder.folderTitle.setText(info.getText());
        holder.noteNum.setText(info.getNum()+"");

    }

    @Override
    public void clickDelete(int position) {
        Toast.makeText(getContext(),"clickDelete"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickRemove(int position) {
        Toast.makeText(getContext(),"clickRemove"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickItem(View view, int position) {
        Toast.makeText(getContext(),"clickItem"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void longClickItem(View view, int position) {
        Toast.makeText(getContext(),"longClickItem"+position,Toast.LENGTH_LONG).show();
    }
    public FolderHolder getHolder() {
        return holder;
    }

    public class FolderHolder {
        public TextView folderTitle;
        public TextView noteNum;

    }


}
