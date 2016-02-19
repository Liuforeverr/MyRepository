package com.qianfeng.liu.buletoothtext;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> listDev;
    private MyReceiver receiver;

    @ViewInject(R.id.lv)
    private ListView lvDev;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        listDev = new ArrayList<>();

         adapter = new MyAdapter<>(this,listDev);
            lvDev.setAdapter(adapter);
        openBt();
        searchBt();
        registerBroadCastRecieve();

    }

    /**
     * 搜索蓝牙设备
     */
    protected void searchBt() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (!bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.startDiscovery();
            }
        }
    }

    /**
     * 打开蓝牙设备
     */
    protected void openBt() {
        bluetoothAdapter =
                BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }

    }

    /**
     * 注册广播接收者
     */
    protected void registerBroadCastRecieve(){
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        IntentFilter filterFinish = new IntentFilter();
        filterFinish.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filterFinish);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }



    /**
     * 连接设备
     */
//    protected void connectDevice(){
//        lvDev.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
//    }
//
    @OnItemClick(R.id.lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = listDev.get(position);
        ClientTask task = new ClientTask();
        task.execute(device);
    }
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String strAction = intent.getAction();
            if (strAction.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                listDev.add(device);
                adapter.notifyDataSetChanged();
            } else if (strAction.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "搜索完成", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
