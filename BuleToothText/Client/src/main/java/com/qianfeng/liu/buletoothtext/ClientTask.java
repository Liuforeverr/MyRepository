package com.qianfeng.liu.buletoothtext;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Liu Shouxiang
 *
 * @Date on 2016/1/21.
 */
public class ClientTask extends AsyncTask<BluetoothDevice,Void,Void> {
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-08002009a66");
    @Override
    protected Void doInBackground(BluetoothDevice... params) {
        BluetoothDevice device = params[0];
        try {
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
            socket.connect();
            Log.i("---","连接成功");
            String msg = "111111111";
            socket.getOutputStream().write(msg.getBytes(),0,msg.getBytes().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
