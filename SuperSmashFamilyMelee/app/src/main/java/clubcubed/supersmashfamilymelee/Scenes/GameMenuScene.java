package clubcubed.supersmashfamilymelee.Scenes;

import android.bluetooth.BluetoothDevice;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Set;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothClient;
import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothServer;
import clubcubed.supersmashfamilymelee.Global;

public class GameMenuScene implements Scene {
    private DankButton bg;
    private DankButton adventure;
    private DankButton melee;
    private DankButton bluetooth;
    private DankButton host;
    private DankButton connect;
    private HashMap<DankButton[], BluetoothDevice> devices;
    private boolean showDevices;
    private int checkBluetooth;
    private BluetoothServer bluetoothServer;
    private BluetoothClient bluetoothClient;
    private float yPosition;
    private float dankState;

    public GameMenuScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);

        if (showDevices) {
            for (DankButton[] dankButtons : devices.keySet()) {
                for (DankButton dankButton : dankButtons) {
                    dankButton.draw(canvas);
                }
            }
        } else {
            adventure.draw(canvas);
            melee.draw(canvas);
            if (checkBluetooth >= 0) {
                bluetooth.draw(canvas);
            } else {
                host.draw(canvas);
                connect.draw(canvas);
            }
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (showDevices) {
                BluetoothDevice device = null;
                for (DankButton[] dankButtons : devices.keySet()) {
                    for (DankButton dankButton : dankButtons) {
                        if (dankButton.collide(motionEvent.getX(), motionEvent.getY())) {
                            device = devices.get(dankButtons);
                        }
                    }
                }
                if (device != null) {
                    bluetoothClient = new BluetoothClient(device);
                    bluetoothClient.run();
                    showDevices = false;
                }
            } else {
                if (adventure.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("AdventureScene");
                } else if (melee.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("CharacterSelectScene");
                } else if (checkBluetooth >= 0 && bluetooth.collide(motionEvent.getX(), motionEvent.getY())) {
                    if (Global.BLUETOOTH_ADAPTER != null && Global.BLUETOOTH_ADAPTER.isEnabled()) {
                        checkBluetooth = -1;
                    } else {
                        bluetooth.setRectARGB(255, 255, 0, 0);
                        bluetooth.setText("Restart Game");
                    }
                } else if (host.collide(motionEvent.getX(), motionEvent.getY())) {
                    dankState = 1;
                    bluetoothClient = null;
                    bluetoothServer = new BluetoothServer();
                    bluetoothServer.run();
                } else if (connect.collide(motionEvent.getX(), motionEvent.getY())) {
                    dankState = 2;
                    bluetoothClient = null;
                    showDevices = true;
                    devices = new HashMap<>();

                    Set<BluetoothDevice> bluetoothDevices = Global.BLUETOOTH_ADAPTER.getBondedDevices();

                    if (bluetoothDevices.size() > 0) {
                        float h = Global.SCREEN_HEIGHT/5;
                        float w = Global.SCREEN_WIDTH/3;
                        int counter = 0;

                        // There are paired devices. Get the name and address of each paired device.
                        for (BluetoothDevice device : bluetoothDevices) {
                            DankButton[] dankButtons = new DankButton[]{
                                    new DankButton(new RectF(0, counter*h, w, (counter+1)*h), device.getName()),
                                    new DankButton(new RectF(w, counter*h, 3*w, (counter+1)*h), device.getAddress())
                            };

                            dankButtons[0].setRectARGB(150, 0, 150, 255);
                            dankButtons[0].setTextARGB(255, 255, 255, 255);
                            dankButtons[0].setTextSize(h/4);

                            dankButtons[1].setRectARGB(150, 0, 150, 255);
                            dankButtons[1].setTextARGB(255, 255, 255, 255);
                            dankButtons[1].setTextSize(h/4);

                            devices.put(dankButtons, device);
                            counter++;
                        }
                    }
                }
            }
        } else if (showDevices && motionEvent.getAction() == MotionEvent.ACTION_SCROLL) {
            float yDistance = motionEvent.getY() - yPosition;
            yPosition = motionEvent.getY();

            for (DankButton[] dankButtons : devices.keySet()) {
                for (DankButton dankButton : dankButtons) {
                    dankButton.addYPosition(yDistance);
                }
            }
        }
    }

    @Override
    public void receiveBack() {
        if (showDevices) {
            showDevices = false;
        } else {
            terminate("GameMenuScene");
        }
    }

    @Override
    public void reset() {
        dankState = 0;

        showDevices = false;
        checkBluetooth = 0;

        bluetoothServer = null;
        bluetoothClient = null;

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

        host = new DankButton(new RectF(w, 7*h, 2*w, 9*h),"Host");
        host.setRectARGB(255, 0, 200, 50);
        host.setTextARGB(255, 255, 255, 255);
        host.setTextSize(h);
        host.setPulse(10);

        connect = new DankButton(new RectF(2*w, 7*h, 3*w, 9*h),"Connect");
        connect.setRectARGB(255, 0, 200, 50);
        connect.setTextARGB(255, 255, 255, 255);
        connect.setTextSize(h);
        connect.setPulse(10);
    }

    @Override
    public void update() {
        bg.dankRectUpdate();

        if (showDevices) {
            for (DankButton[] dankButtons : devices.keySet()) {
                for (DankButton dankButton : dankButtons) {
                    dankButton.dankRectUpdate();
                }
            }
        } else {
            adventure.pulseUpdate();
            melee.pulseUpdate();
            bluetooth.pulseUpdate();
            host.pulseUpdate();
            connect.pulseUpdate();

            if (dankState == 1) {
                host.dankRectUpdate();
            } else if (dankState == 2) {
                connect.dankRectUpdate();
            }
        }

        if (Global.BLUETOOTH_SOCKET != null && Global.BLUETOOTH_SOCKET.isConnected()) {
            try {
                if (Global.BLUETOOTH_SOCKET.getInputStream().read() == -1) {
                    Global.BLUETOOTH_SOCKET.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Global.BLUETOOTH_SOCKET != null && Global.BLUETOOTH_SOCKET.isConnected()) {
            bluetooth.setText("Connected");
            bluetoothClient = null;
            bluetoothServer = null;
            checkBluetooth = 1;
            dankState = 0;
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}