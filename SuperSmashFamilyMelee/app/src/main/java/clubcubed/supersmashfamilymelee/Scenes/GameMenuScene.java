package clubcubed.supersmashfamilymelee.Scenes;

import android.bluetooth.BluetoothDevice;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Set;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothClient;
import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothData;
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

    // -1 : hosting or connecting
    //  0 : unchecked, might be null
    //  1 : checked, success
    private int checkBluetooth;

    private BluetoothServer bluetoothServer;
    private BluetoothClient bluetoothClient;

    // for scrolling
    private float yPosition;

    // 0 : no rainbow
    // 1 : host button rainbow
    // 2 : connect button rainbow
    private int dankState;

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
            yPosition = motionEvent.getY();
            // showing paired devices
            if (showDevices) {
                BluetoothDevice device = null;
                // check for pressing down
                for (DankButton[] dankButtons : devices.keySet()) {
                    for (DankButton dankButton : dankButtons) {
                        if (dankButton.collide(motionEvent.getX(), motionEvent.getY())) {
                            device = devices.get(dankButtons);
                            break;
                        }
                    }
                    if (device != null) {
                        bluetoothClient = new BluetoothClient(device);
                        bluetoothClient.start();
                        showDevices = false;
                        break;
                    }
                }
            } else {
                // other buttons
                if (adventure.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("AdventureScene");

                } else if (melee.collide(motionEvent.getX(), motionEvent.getY())) {
                    terminate("CharacterSelectScene");

                } else if (checkBluetooth == 0 && bluetooth.collide(motionEvent.getX(), motionEvent.getY())) {
                    // check if bluetooth is enabled
                    if (Global.BLUETOOTH_ADAPTER != null && Global.BLUETOOTH_ADAPTER.isEnabled()) {
                        checkBluetooth = -1;
                    } else {
                        bluetooth.setRectARGB(255, 255, 0, 0);
                        bluetooth.setText("Bluetooth broke");
                    }

                } else if (checkBluetooth == -1 && host.collide(motionEvent.getX(), motionEvent.getY())) {
                    // hosting
                    dankState = 1;
                    if (bluetoothServer != null) {
                        bluetoothServer.cancel();
                    }
                    bluetoothClient = null;
                    bluetoothServer = new BluetoothServer();
                    bluetoothServer.start();

                } else if (checkBluetooth == -1 && connect.collide(motionEvent.getX(), motionEvent.getY())) {
                    // show paired devices menu
                    dankState = 2;
                    if (bluetoothServer != null) {
                        bluetoothServer.cancel();
                    }
                    bluetoothServer = null;
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
                                    new DankButton(new RectF(w, counter*h, (3*w)-(w/6), (counter+1)*h), device.getAddress())
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
        } else if (showDevices && motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            // scroll pojers
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
            if (Global.BLUETOOTH_DATA != null) {
                Global.BLUETOOTH_DATA.cancel();
            }
            Global.BLUETOOTH_SOCKET = null;
            Global.BLUETOOTH_DATA = null;
            terminate("MainMenuScene");
        }
    }

    @Override
    public void reset() {
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

        host = new DankButton(new RectF(w, 7*h, 47*w/24, 9*h),"Host");
        host.setRectARGB(255, 0, 200, 50);
        host.setTextARGB(255, 255, 255, 255);
        host.setTextSize(h);
        host.setPulse(10);

        connect = new DankButton(new RectF(49*w/24, 7*h, 3*w, 9*h),"Connect");
        connect.setRectARGB(255, 0, 200, 50);
        connect.setTextARGB(255, 255, 255, 255);
        connect.setTextSize(h);
        connect.setPulse(10);

        if (Global.BLUETOOTH_DATA != null) {
            dankState = Global.BLUETOOTH_DATA.player;
            Global.BLUETOOTH_DATA.write("GameMenuScene".getBytes());
        }
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

        if (checkBluetooth != 1 && Global.BLUETOOTH_SOCKET != null && Global.BLUETOOTH_SOCKET.isConnected()) {
            Log.d("asdf", String.format("%d, %b, %b", checkBluetooth, Global.BLUETOOTH_SOCKET!=null, Global.BLUETOOTH_SOCKET.isConnected()));

            if (Global.BLUETOOTH_DATA == null) {
                Global.BLUETOOTH_DATA = new BluetoothData();
                Global.BLUETOOTH_DATA.start();
            }

            Global.BLUETOOTH_DATA.write("GameMenuScene".getBytes());

            if (dankState == 1) {
                bluetooth.setText("Connected (P1)");
            } else {
                bluetooth.setText("Connected (P2)");
            }

            Global.BLUETOOTH_DATA.player = dankState;
            bluetoothServer = null;
            bluetoothClient = null;
            checkBluetooth = 1;
            dankState = 0;
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}