package clubcubed.supersmashfamilymelee;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.BluetoothStuff.BluetoothData;
import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.FaxMcClad;
import clubcubed.supersmashfamilymelee.Characters.Shraek;
import clubcubed.supersmashfamilymelee.Stages.LastJourneyEnd;
import clubcubed.supersmashfamilymelee.Stages.Stage;

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
    public static class STAGE_MANAGER {
        public static Stage GET_STAGE(STAGE_NAME stage) {
            // TODO: add more stages
            switch (stage) {
                case LAST_JOURNEY_END:
                    return new LastJourneyEnd();
                default:
                    Log.d("StageScene", "undefined STAGE_NAME" + Global.CURRENT_STAGE.name());
                    return new LastJourneyEnd();
            }
        }
        public static void GET_STAGES(ArrayList<DankButton> stages) {
            // TODO: add more stages
            stages.add(new DankButton("Last Journey End"));
        }
        public static void GET_STAGE_NAMES(ArrayList<STAGE_NAME> stageNames) {
            // TODO: add more stages
            stageNames.add(STAGE_NAME.LAST_JOURNEY_END);
        }
    }

    public static CHARACTER_NAME CHARACTER_ONE_NAME = CHARACTER_NAME.NULL;
    public static CHARACTER_NAME CHARACTER_TWO_NAME = CHARACTER_NAME.NULL;
    public enum CHARACTER_NAME {
        // TODO: add more characters
        NULL,
        FAX_MC_CLAD,
        SHRAEK
    }
    public static class CHARACTER_MANAGER {
        public static CHARACTER_NAME GET_RANDOM_NAME(Random random) {
            // TODO: add more characters
            switch(random.nextInt(2)) {
                case 0:
                    return CHARACTER_NAME.FAX_MC_CLAD;
                case 1:
                    return CHARACTER_NAME.SHRAEK;
                default:
                    Log.d("Global", "undefined random integer");
                    return CHARACTER_NAME.FAX_MC_CLAD;
            }
        }
        public static Character GET_CHARACTER(CHARACTER_NAME character, int player) {
            // TODO: add more characters
            switch(character) {
                case FAX_MC_CLAD:
                    return new FaxMcClad(player);
                case SHRAEK:
                    return new Shraek(player);
                default:
                    Log.d("Global", "undefined character " + character.name());
                    return new FaxMcClad(player);
            }
        }
        public static void GET_CHARACTERS(ArrayList<DankButton> characters) {
            // TODO: add more characters
            characters.add(new DankButton("Fax McClad"));
            characters.add(new DankButton("Shraek"));
        }
        public static void GET_CHARACTER_NAMES(ArrayList<CHARACTER_NAME> characterNames) {
            // TODO: add more characters
            characterNames.add(CHARACTER_NAME.FAX_MC_CLAD);
            characterNames.add(CHARACTER_NAME.SHRAEK);
        }
    }

    public static BluetoothAdapter BLUETOOTH_ADAPTER;
    public static BluetoothSocket BLUETOOTH_SOCKET;
    public static BluetoothData BLUETOOTH_DATA;
    public static int REQUEST_ENABLE_BT;
    public static final String GAME_NAME = "SSFM";
    public static final UUID GAME_UUID = UUID.fromString("bee4b5e9-7a94-44dd-a186-aaa2bbc151f8");

    public static MediaPlayer SONG_PLAYER;
}
