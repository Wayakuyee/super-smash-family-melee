package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothClient extends Thread {
    private BluetoothDevice bluetoothDevice;
    public BluetoothClient(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;

        try {
            Global.BLUETOOTH_SOCKET = this.bluetoothDevice.createRfcommSocketToServiceRecord(Global.GAME_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Global.BLUETOOTH_SOCKET.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
