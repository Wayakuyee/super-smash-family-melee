package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class StageSelectScene implements Scene {
    private DankButton bg;
    private DankButton wait;
    private boolean waiting;
    private ArrayList<DankButton> stages = new ArrayList<>();
    private ArrayList<Global.STAGE_NAME> stageNames = new ArrayList<>();

    public StageSelectScene() {
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected()) {
            // go back if opponent is too far behind
            if (Global.BLUETOOTH_DATA.scene != Global.SCENE_NAME.CHARACTER_SELECT_SCENE
                    || Global.BLUETOOTH_DATA.scene != Global.CURRENT_SCENE) {
                receiveBack();
                return;
            }
            waiting = true;
        } else {
            waiting = false;
        }

        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        bg.setRectARGB(255, 0, 0, 0);

        wait = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "waiting");
        wait.setTextSize(Global.SCREEN_HEIGHT/2);
        wait.setTextARGB(255, 255, 255, 255);
        wait.setRectARGB(100, 255, 0, 0);

        // TODO: add stages
        stages.add(new DankButton("Last Journey End"));
        stageNames.add(Global.STAGE_NAME.LAST_JOURNEY_END);

        int columns = 5;
        int rows = 2;
        float x = Global.SCREEN_WIDTH/columns;
        float y = Global.SCREEN_HEIGHT/rows;

        for (int i=0; i<stages.size(); i++) {
            stages.get(i).setRectF(new RectF((i%columns)*x, (i%rows)*y, ((i%columns)+1)*x, ((i%rows)+1)*y));
            stages.get(i).setTextSize(Math.min(x/10, y/10));
            stages.get(i).setTextARGB(255, 255, 0, 0);
            stages.get(i).setPulse(100);
            stages.get(i).setRectARGB(150, 60, 60, 60);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);

        for (DankButton s : stages)
            s.draw(canvas);

        if (waiting)
            wait.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !waiting) {
            for (int i=0; i<stages.size(); i++) {
                if (stages.get(i).collide(motionEvent.getX(), motionEvent.getY())) {
                    Global.CURRENT_STAGE = stageNames.get(i);
                    if (Global.BLUETOOTH_DATA != null)
                        Global.BLUETOOTH_DATA.write("stage" + Global.CURRENT_STAGE.name());
                    terminate(Global.SCENE_NAME.STAGE_SCENE);
                }
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
        terminate(Global.SCENE_NAME.CHARACTER_SELECT_SCENE);
    }

    @Override
    public void update() {
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected()) {
            if (Global.BLUETOOTH_DATA.scene == Global.SCENE_NAME.GAME_MENU_SCENE) {
                receiveBack();
                return;
            }
            waiting = (Global.BLUETOOTH_DATA.scene != Global.SCENE_NAME.STAGE_SELECT_SCENE);

            if (Global.BLUETOOTH_DATA.stage != Global.STAGE_NAME.NULL) {
                Global.CURRENT_STAGE = Global.BLUETOOTH_DATA.stage;
                Global.BLUETOOTH_DATA.write("stage" + Global.CURRENT_STAGE.name());

            }

            if (waiting)
                wait.dankRectUpdate();
        }

        for (DankButton s : stages)
            s.dankTextUpdate();
    }

    private void terminate(Global.SCENE_NAME sceneName) {
        Global.CURRENT_SCENE = sceneName;
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected())
            Global.BLUETOOTH_DATA.write("scene" + sceneName.name());
    }
}
