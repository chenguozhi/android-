package com.example.chen.adapter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.chen.adapter.R;
import com.example.chen.adapter.adapter.LeftDelteAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.list);
        editText= (EditText) findViewById(R.id.folder_search);
        List<Info> list=new ArrayList<>();
        for (int a=0;a<100;a++){
            Info info=new Info("测试"+a,a);
            list.add(info);
        }
        final MyAdapter adapter=new MyAdapter(this,list, LeftDelteAdapter.SHOWALL);
        listView.setAdapter(adapter);
//        listView.setTagAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(editText.getText().toString());
            }
        });
    }
}
