package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothData extends Thread {
    public int player;
    public int bytes;
    public byte[] buffer;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothData() {
        try {
            inputStream = Global.BLUETOOTH_SOCKET.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }

        try {
            outputStream = Global.BLUETOOTH_SOCKET.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            outputStream = null;
        }

        read();
    }

    private void read() {
        bytes = -1;
        buffer = new byte[1024];

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                bytes = inputStream.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("BluetoothData", "broke");
                cancel();
                break;
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            Global.BLUETOOTH_SOCKET.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}