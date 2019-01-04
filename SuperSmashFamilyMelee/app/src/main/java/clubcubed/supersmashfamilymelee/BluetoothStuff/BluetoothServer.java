package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.bluetooth.BluetoothServerSocket;

import java.io.IOException;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothServer extends Thread {
    private BluetoothServerSocket bluetoothServerSocket;

    public BluetoothServer() {
        try {
            bluetoothServerSocket = Global.BLUETOOTH_ADAPTER.listenUsingRfcommWithServiceRecord(Global.GAME_NAME, Global.GAME_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Global.BLUETOOTH_SOCKET = null;

        while (Global.BLUETOOTH_SOCKET == null) {
            try {
                Global.BLUETOOTH_SOCKET = bluetoothServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Global.BLUETOOTH_ADAPTER.cancelDiscovery();
            bluetoothServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            bluetoothServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}