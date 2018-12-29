package clubcubed.supersmashfamilymelee;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.media.MediaPlayer;

import java.util.UUID;

import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothData;

public class Global {
    public static float SCREEN_HEIGHT;
    public static float SCREEN_WIDTH;
    public static float GAME_RATIO;
    public static float GAME_HEIGHT = 500f;
    public static float GAME_WIDTH = 750f;
    public static float GAME_DIFFERENCE;

    public static String SCENE_NAME;

    public static String STAGE_NAME;
    public static String CHARACTER_ONE_NAME;
    public static String CHARACTER_TWO_NAME;

    public static BluetoothAdapter BLUETOOTH_ADAPTER;
    public static BluetoothSocket BLUETOOTH_SOCKET;
    public static BluetoothData BLUETOOTH_DATA;
    public static int REQUEST_ENABLE_BT;
    public static final String GAME_NAME = "SSFM";
    public static final UUID GAME_UUID = UUID.fromString("bee4b5e9-7a94-44dd-a186-aaa2bbc151f8");

    public static MediaPlayer SONG_PLAYER;
}
