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

    public StageSelectScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);

        for (DankButton s : stages) {
            s.draw(canvas);
        }

        if (waiting) {
            wait.draw(canvas);
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            for (DankButton s : stages) {
                if (s.collide(motionEvent.getX(), motionEvent.getY())) {
                    Global.STAGE_NAME = s.getText();
                    Global.BLUETOOTH_DATA.write(s.getText().getBytes());
                    terminate("StageScene");
                }
            }
        }
    }

    @Override
    public void receiveBack() {
        if (Global.BLUETOOTH_DATA == null) {
            terminate("CharacterSelectScene");
        }
    }

    @Override
    public void reset() {
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        bg.setRectARGB(255, 0, 0, 0);

        wait = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "waiting");
        wait.setTextSize(Global.SCREEN_HEIGHT/2);
        wait.setTextARGB(255, 255, 255, 255);
        wait.setRectARGB(100, 255, 0, 0);

        stages.add(new DankButton("LastJourneyEnd"));

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

        if (Global.BLUETOOTH_DATA != null) {
            Global.BLUETOOTH_DATA.write("StageSelectScene".getBytes());
        }
    }

    @Override
    public void update() {
        if (Global.BLUETOOTH_DATA != null) {
            waiting = !new String(Global.BLUETOOTH_DATA.buffer).equals("CharacterSelectScene");
        }
        if (waiting) {
            wait.dankRectUpdate();
        }
        for (DankButton s : stages) {
            s.dankTextUpdate();
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}
