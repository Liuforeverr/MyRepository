package com.qianfeng.liu.buletoothtext;

/**
 * Created by Liu Shouxiang
 *
 * @Date on 2016/1/21.
 */
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context,List<T> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        objects=list;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvdev = (TextView) convertView.findViewById(R.id.tvdev);

            convertView.setTag(viewHolder);
        }
        initializeViews((T)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        //TODO implement
        holder.tvdev.setText(((BluetoothDevice) object).getName());

    }

    protected class ViewHolder {
        private TextView tvdev;
    }
}

