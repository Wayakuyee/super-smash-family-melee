package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.bluetooth.BluetoothServerSocket;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothServer extends Thread {
    private boolean connect;
    private BluetoothServerSocket bluetoothServerSocket;

    public BluetoothServer() {
        connect = false;
        try {
            bluetoothServerSocket = Global.BLUETOOTH_ADAPTER.listenUsingRfcommWithServiceRecord(Global.GAME_NAME, Global.GAME_UUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Global.BLUETOOTH_SOCKET = null;

        while (Global.BLUETOOTH_SOCKET == null) {
            try {
                Global.BLUETOOTH_SOCKET = bluetoothServerSocket.accept();
                connect = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnect() {
        return connect;
    }
}