package com.qiangfeng.administrator.cpclient;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String Uristring = "content://com.qiangfeng.administrator.contentproviderclient.mycontentprovider/person";
    private ContentResolver resolver;
    private EditText et1, et2;
    private ListView lv;
    private List<Map<String, Object>> list;
    private SimpleAdapter adapter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        resolver = getContentResolver();
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        adapter = new SimpleAdapter(this, list, R.layout.listview_layout,
                new String[]{"_id", "name", "salary"}, new int[]{R.id.tv1, R.id.tv2, R.id.tv3});
        lv.setAdapter(adapter);

    }

    public void commit(View view) {
        String name = et1.getText().toString();
        String salary = et2.getText().toString();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("salary", salary);

        Uri uri = resolver.insert(Uri.parse(Uristring), values);

        query(uri);


    }

    public void query() {
        Cursor c = resolver.query(Uri.parse(Uristring), new String[]{"_id", "name", "salary"}, null, null, null);

        while (c.moveToNext()) {

            int id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            String salary = c.getString(c.getColumnIndex("salary"));
//
            adapter.notifyDataSetChanged();

        }
    }

    public void query(Uri uri) {
        Cursor c = resolver.query(uri, new String[]{"_id", "name", "salary"}, null, null, null);

        while (c.moveToNext()) {

            int id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            String salary = c.getString(c.getColumnIndex("salary"));
            Log.i("MainActivity", id + "  " + name + "  " + salary);

            Map<String, Object> map = new HashMap<>();
            map.put("_id", id);
            map.put("name", name);
            map.put("salary", salary);

            list.add(map);

            adapter.notifyDataSetChanged();

        }

    }
}
