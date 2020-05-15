package clubcubed.supersmashfamilymelee.BluetoothStuff;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Stack;

import clubcubed.supersmashfamilymelee.Global;

public class BluetoothData extends Thread {
    // opponent data
    public Global.CHARACTER_NAME character = Global.CHARACTER_NAME.NULL;
    public Global.STAGE_NAME stage = Global.STAGE_NAME.NULL;
    public Global.SCENE_NAME scene = Global.SCENE_NAME.NULL;
    public boolean isHost;
    // -3 : force quit
    // -2 : waiting loading
    // -1 : opponent pause
    //  0 : ongoing
    //  1 : player 1 win
    //  2 : player 2 win
    // 69 : tie lmao
    public short gameState;
    public boolean desync = false;

    private LinkedList<String> inputsBuffer;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothData() {
        super();
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
        inputsBuffer = new LinkedList<>();
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                int len = inputStream.read(buffer);
                String message = (len >= 0) ? new String(buffer).substring(0, len) : ("");
                for (String s : message.split(",")) {
                    if (s.startsWith("input")) {
                        inputsBuffer.add(s.substring(5));
                    } else if (s.startsWith("state")) {
                        this.gameState = Short.parseShort(s.substring(5));
                    } else if (s.startsWith("chara")) {
                        this.character = Global.CHARACTER_NAME.valueOf(s.substring(5));
                    } else if (s.startsWith("stage")) {
                        this.stage = Global.STAGE_NAME.valueOf(s.substring(5));
                    } else if (s.startsWith("scene")) {
                        this.scene = Global.SCENE_NAME.valueOf(s.substring(5));
                    } else if (s.equals("cancel")) {
                        cancel();
                    } else {
                        Log.d("BluetoothData", "unknown message" + s);
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
                Log.d("BluetoothData", "cannot read");
                cancel();
                break;
            }
        }
    }

    public boolean isConnected() {
        return Global.BLUETOOTH_SOCKET != null && Global.BLUETOOTH_SOCKET.isConnected();
    }

    public void write(String s) {
        try {
            // comma separated messages
            outputStream.write((s + ",").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("BluetoothData", "cannot write");
        }
    }

    public void cancel() {
        try {
            if (inputStream != null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
            if (Global.BLUETOOTH_DATA != null)
                Global.BLUETOOTH_SOCKET.close();
                Global.BLUETOOTH_SOCKET = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("BluetoothData", "cannot close");
        }
    }

    public String getInputs() {
        if (inputsBuffer.isEmpty()) {
            desync = true;
            return "0;0;0;0";
        }
        return inputsBuffer.remove();
    }
}