package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.bluetooth.BluetoothDevice;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothClient extends Thread {
    private BluetoothDevice bluetoothDevice;
    private boolean connect;

    public BluetoothClient(BluetoothDevice bluetoothDevice) {
        connect = false;
        this.bluetoothDevice = bluetoothDevice;

        try {
            Global.BLUETOOTH_SOCKET = this.bluetoothDevice.createRfcommSocketToServiceRecord(Global.GAME_UUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Global.BLUETOOTH_SOCKET.connect();
            connect = true;
        } catch (Exception e) {
            e.printStackTrace();
            connect = false;
        }
    }

    public void die() {
        bluetoothDevice
    }

    public boolean isConnect() {
        return connect;
    }
}
