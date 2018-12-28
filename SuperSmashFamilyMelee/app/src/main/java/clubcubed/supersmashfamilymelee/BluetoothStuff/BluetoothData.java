package clubcubed.supersmashfamilymelee.BluetoothStuff;

import java.io.InputStream;
import java.io.OutputStream;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothData extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothData() {
        try {
            inputStream = Global.BLUETOOTH_SOCKET.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            inputStream = null;
        }

        try {
            outputStream = Global.BLUETOOTH_SOCKET.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            outputStream = null;
        }
    }

    public int read() {
        try {
            return inputStream.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
