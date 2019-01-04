package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothClient extends Thread {
    public BluetoothClient(BluetoothDevice bluetoothDevice) {
        try {
            Global.BLUETOOTH_SOCKET = bluetoothDevice.createRfcommSocketToServiceRecord(Global.GAME_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Global.BLUETOOTH_SOCKET.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
