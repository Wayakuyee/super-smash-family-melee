package clubcubed.supersmashfamilymelee.Scenes;

import android.bluetooth.BluetoothDevice;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Set;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class GameMenuScene implements Scene {
    private DankButton bg;
    private DankButton adventure;
    private DankButton melee;
    private DankButton bluetooth;

    public GameMenuScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        adventure.draw(canvas);
        melee.draw(canvas);
        bluetooth.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                if (adventure.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("AdventureScene");
                } else if (melee.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("CharacterSelectScene");
                } else if (bluetooth.collide(motionEvent.getX(), motionEvent.getY())) {
                    if (Global.REQUEST_ENABLE_BT < 0 && Global.BLUETOOTH_ADAPTER != null && Global.BLUETOOTH_ADAPTER.isEnabled()) {
                        Set<BluetoothDevice> bluetoothDevices = Global.BLUETOOTH_ADAPTER.getBondedDevices();
                        HashMap<String, String> macs = new HashMap<>();

                        if (bluetoothDevices.size() > 0) {
                            // There are paired devices. Get the name and address of each paired device.
                            for (BluetoothDevice device : bluetoothDevices) {
                                String deviceName = device.getName();
                                // MAC address
                                String deviceHardwareAddress = device.getAddress();
                                macs.put(deviceHardwareAddress, deviceName);
                            }
                        }

                        for (String i : macs.keySet()) {
                            Log.d("GameMenuScene", String.format("%s : %s", i, macs.get(i)));
                        }
                    } else {
                        bluetooth.setRectARGB(255, 255, 0, 0);
                        bluetooth.setText("Restart Game");
                    }
                }
        }
    }

    @Override
    public void receiveBack() {
        terminate("GameMenuScene");
    }

    @Override
    public void reset() {
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        bg.setRectARGB(150, 0, 255, 0);

        float w = Global.SCREEN_WIDTH/4;
        float h = Global.SCREEN_HEIGHT/10;

        adventure = new DankButton(new RectF(w, h, 3*w, 3*h),"Adventure Mode");
        adventure.setRectARGB(255, 200, 150, 0);
        adventure.setTextARGB(255, 255, 255, 255);
        adventure.setTextSize(h);
        adventure.setPulse(10);

        melee = new DankButton(new RectF(w, 4*h, 3*w, 6*h),"Melee");
        melee.setRectARGB(255, 255, 100, 0);
        melee.setTextARGB(255, 255, 255, 255);
        melee.setTextSize(h);
        melee.setPulse(10);

        bluetooth = new DankButton(new RectF(w, 7*h, 3*w, 9*h),"Enable Bluetooth");
        bluetooth.setRectARGB(255, 0, 100, 255);
        bluetooth.setTextARGB(255, 255, 255, 255);
        bluetooth.setTextSize(h);
        bluetooth.setPulse(10);
    }

    @Override
    public void update() {
        bg.dankRectUpdate();
        adventure.pulseUpdate();
        melee.pulseUpdate();
        bluetooth.pulseUpdate();
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}