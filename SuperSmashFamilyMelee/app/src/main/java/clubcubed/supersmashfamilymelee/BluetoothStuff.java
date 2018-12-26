package clubcubed.supersmashfamilymelee;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class BluetoothStuff extends Thread {
    String TAG = "bluetoothstuff";
    private boolean status;
    private BluetoothServerSocket bluetoothServerSocket;
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothStuff() {
        this.ActivateBluetooth(true);

        if (isStatus()) {
            GetMAC();
            CreateSocket();
        }
        }

    private void ActivateBluetooth(boolean b) {
        if (Global.BLUETOOTH_ADAPTER == null) {
            // Device doesn't support Bluetooth
            status = false;
        } else {
            status = b;
        }
    }

    public boolean isStatus() {
        return status;
    }

    private void GetMAC() {
        Set<BluetoothDevice> pairedDevices = Global.BLUETOOTH_ADAPTER.getBondedDevices();
        HashMap<String, String> macs = new HashMap<>();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                // MAC address
                String deviceHardwareAddress = device.getAddress();
                macs.put(deviceHardwareAddress, deviceName);
            }
        }

        for (String i : macs.keySet()) {
            Log.d(TAG, String.format("%s : %s", i, macs.get(i)));
        }
    }

    private void CreateSocket() {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = Global.BLUETOOTH_ADAPTER.listenUsingRfcommWithServiceRecord("SSFM", UUID.fromString("bee4b5e9-7a94-44dd-a186-aaa2bbc151f8"));
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        bluetoothServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket bluetoothSocket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (bluetoothSocket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                //// manageMyConnectedSocket(bluetoothSocket);
                //// bluetoothServerSocket.close();
                break;
            }
        }
    }

    /*
    private void idkloldontopen () {
        public void constructor () {
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
        public void run () {
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
        public void write ( byte[] bytes){
            try {
                outputStream.write(bytes);

                // Share the sent message with the UI activity.
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
            }
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel () {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }
    */
}
