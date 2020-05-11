package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class CharacterSelectScene implements Scene {
    private DankButton bg;
    private DankButton wait;
    private boolean waiting;
    private ArrayList<DankButton> characters = new ArrayList<>();
    private ArrayList<Global.CHARACTER_NAME> characterNames = new ArrayList<>();

    public CharacterSelectScene() {
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        bg.setRectARGB(255, 0, 0, 0);

        wait = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "waiting");
        wait.setTextSize(Global.SCREEN_HEIGHT/2);
        wait.setTextARGB(255, 255, 255, 255);
        wait.setRectARGB(100, 255, 0, 0);

        // TODO: add characters
        characters.add(new DankButton("FaxMcClad"));
        characterNames.add(Global.CHARACTER_NAME.FAX_MC_CLAD);

        int columns = 5;
        int rows = 2;
        float x = Global.SCREEN_WIDTH/columns;
        float y = Global.SCREEN_HEIGHT/rows;

        for (int i=0; i<characters.size(); i++) {
            characters.get(i).setRectF(new RectF((i%columns)*x, (i%rows)*y, ((i%columns)+1)*x, ((i%rows)+1)*y));
            characters.get(i).setTextSize(Math.min(x/10, y/10));
            characters.get(i).setTextARGB(255, 255, 0, 0);
            characters.get(i).setPulse(100);
            characters.get(i).setRectARGB(150, 60, 60, 60);
        }

        // no waiting if no multiplayer
        waiting = (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected());
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);

        for (DankButton c : characters)
            c.draw(canvas);

        if (waiting)
            wait.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !waiting) {
            for (int i=0; i<characters.size(); i++) {
                // check if user taps a stage
                if (!characters.get(i).collide(motionEvent.getX(), motionEvent.getY()))
                    continue;

                // sends stage select to
                if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_SOCKET.isConnected()) {
                    // if the opponent is host, then select character 2
                    // if the opponent is client, then select character 1
                    if (Global.BLUETOOTH_DATA.isHost)
                        Global.CHARACTER_TWO_NAME = characterNames.get(i);
                    else
                        Global.CHARACTER_ONE_NAME = characterNames.get(i);

                    Global.BLUETOOTH_DATA.write("chara" + characterNames.get(i).name());
                } else {
                    Global.CHARACTER_ONE_NAME = characterNames.get(i);
                }
                terminate(Global.SCENE_NAME.STAGE_SELECT_SCENE);
            }
        }
    }

    @Override
    public void receiveBack() {
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected()) {
            Global.BLUETOOTH_DATA.write("chara" + Global.CHARACTER_NAME.NULL.name());
            Global.BLUETOOTH_DATA.write("stage" + Global.STAGE_NAME.NULL.name());
        }
        Global.CURRENT_STAGE = Global.STAGE_NAME.NULL;
        Global.CHARACTER_ONE_NAME = Global.CHARACTER_NAME.NULL;
        Global.CHARACTER_TWO_NAME = Global.CHARACTER_NAME.NULL;
        terminate(Global.SCENE_NAME.GAME_MENU_SCENE);
    }

    @Override
    public void update() {
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected())
            waiting = (Global.BLUETOOTH_DATA.scene == Global.SCENE_NAME.CHARACTER_SELECT_SCENE);

        if (waiting)
            wait.dankRectUpdate();

        for (DankButton c : characters)
            c.dankTextUpdate();
    }

    private void terminate(Global.SCENE_NAME sceneName) {
        Global.CURRENT_SCENE = sceneName;
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected())
            Global.BLUETOOTH_DATA.write("scene" + sceneName.name());
    }
}