package pl.jgwozdz.brickcommand.android;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainBrickSteer extends Activity {


    public static final String BLUETOOTH_TAG = "bluetooth";
    public static final String EV3 = "EV3";
    public static final String EV3_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothSocket bluetoothSocket;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onDestroy() {
        disconnect(null);
        super.onDestroy();
    }

    public void disconnect(View view) {
        if (bluetoothSocket == null) return;
        try {
            bluetoothSocket.close();
            bluetoothSocket = null;
        } catch (IOException e) {
            Log.e(BLUETOOTH_TAG, "connection exception", e);
            return;
        }
        Log.i(BLUETOOTH_TAG, "disconnected...");
        if (view != null) { // todo: change this
            Button btn = (Button) view.getRootView().findViewById(R.id.connectBtn);
            btn.setText(R.string.Connect);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connect(view);
                }
            });
            TextView info = (TextView) view.getRootView().findViewById(R.id.connectionInfo);
            info.setText(R.string.notConnected);
        }
    }

    public void connect(View view) {
        disconnect(view);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Log.i(BLUETOOTH_TAG, "adapter: " + adapter);
        if (!adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        if (getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED) {
            adapter.cancelDiscovery();
        }
        Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
        Log.i(BLUETOOTH_TAG, "bondedDevices: " + bondedDevices);
        BluetoothDevice ev3 = null;
        for (BluetoothDevice bondedDevice : bondedDevices) {
            Log.i(BLUETOOTH_TAG, bondedDevice + " = " + bondedDevice.getName());
            if (EV3.equals(bondedDevice.getName())) ev3 = bondedDevice;
        }
        assert ev3 != null;
        try {
            bluetoothSocket = ev3.createRfcommSocketToServiceRecord(UUID.fromString(EV3_UUID));
            Log.i(BLUETOOTH_TAG, "connecting...");
            bluetoothSocket.connect();
        } catch (IOException e) {
            Log.e(BLUETOOTH_TAG, "connection exception", e);
            return;
        }
        Log.d("View", view.toString());
        Log.i(BLUETOOTH_TAG, "connected...");
        Button btn = (Button) view.getRootView().findViewById(R.id.connectBtn);
        btn.setText("Disconnect");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect(view);
            }
        });
        TextView info = (TextView) view.getRootView().findViewById(R.id.connectionInfo);
        info.setText(bluetoothSocket.getRemoteDevice().toString());
    }

}
