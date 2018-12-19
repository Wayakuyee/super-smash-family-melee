package clubcubed.supersmashfamilymelee;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothStuff extends Thread {
    String TAG = "bluetoothstuff";
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothStuff(BluetoothSocket bluetoothSocket) throws IOException {
        this.bluetoothSocket = bluetoothSocket;

        try {
            this.inputStream = this.bluetoothSocket.getInputStream();
        } catch (Exception e) {
            Log.d("bluetoothstuff", "input stream die");
            this.inputStream = null;
        }

        try {
            this.outputStream = this.bluetoothSocket.getOutputStream();
        } catch (Exception e) {
            Log.d("bluetoothstuff", "output stream die");
            this.outputStream = null;
        }
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                bytes = inputStream.read(buffer);

                // Send the obtained bytes to the UI activity.
            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }
    }

    // Call this from the main activity to send data to the remote device.
    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);

            // Share the sent message with the UI activity.
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);

            // Send a failure message back to the activity.
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}
