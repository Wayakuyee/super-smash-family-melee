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

        // cancel discovery when connection found
        try {
            Global.BLUETOOTH_ADAPTER.cancelDiscovery();
            bluetoothServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            if (bluetoothServerSocket != null)
                bluetoothServerSocket.close();
            if (Global.BLUETOOTH_SOCKET != null)
                Global.BLUETOOTH_SOCKET.close();
            Global.BLUETOOTH_SOCKET = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}