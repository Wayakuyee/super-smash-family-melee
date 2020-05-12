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

    public static SCENE_NAME CURRENT_SCENE;
    public enum SCENE_NAME {
        NULL,
        MAIN_MENU_SCENE,
        GAME_MENU_SCENE,
        ADVENTURE_SCENE,
        OPTION_SCENE,
        CHARACTER_SELECT_SCENE,
        STAGE_SELECT_SCENE,
        STAGE_SCENE
    }

    public static STAGE_NAME CURRENT_STAGE = STAGE_NAME.NULL;
    public enum STAGE_NAME {
        // TODO: add more stages
        NULL,
        LAST_JOURNEY_END
    }

    public static CHARACTER_NAME CHARACTER_ONE_NAME = CHARACTER_NAME.NULL;
    public static CHARACTER_NAME CHARACTER_TWO_NAME = CHARACTER_NAME.NULL;
    public enum CHARACTER_NAME {
        // TODO: add more characters
        NULL,
        FAX_MC_CLAD
    }

    public static BluetoothAdapter BLUETOOTH_ADAPTER;
    public static BluetoothSocket BLUETOOTH_SOCKET;
    public static BluetoothData BLUETOOTH_DATA;
    public static int REQUEST_ENABLE_BT;
    public static final String GAME_NAME = "SSFM";
    public static final UUID GAME_UUID = UUID.fromString("bee4b5e9-7a94-44dd-a186-aaa2bbc151f8");

    public static MediaPlayer SONG_PLAYER;
}
