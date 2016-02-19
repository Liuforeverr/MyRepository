package com.qianfeng.liu.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Liu Shouxiang
 *
 * @Date on 2016/1/21.
 */
public class SevTask extends AsyncTask<BluetoothAdapter,Void ,String> {
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-08002009a66");
    @Override
    protected String doInBackground(BluetoothAdapter... params) {
        BluetoothAdapter adapter = params[0];
        try {
            BluetoothServerSocket serverSocket =
                    adapter.listenUsingRfcommWithServiceRecord("blue_service",
                            MY_UUID_SECURE);
            BluetoothSocket clientSocket = serverSocket.accept();
            Log.i("---", "连接成功");

            byte[]bytes = new byte[1024];
            InputStream inputStream =
                    clientSocket.getInputStream();
            inputStream.read(bytes);
            String msg = new String(bytes,"UTF-8");
            Log.i("---",msg);
            return msg;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
