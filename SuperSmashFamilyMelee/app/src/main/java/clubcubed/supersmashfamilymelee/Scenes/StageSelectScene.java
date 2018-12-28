package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class StageSelectScene implements Scene {
    private DankButton bg;
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
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            for (DankButton s : stages) {
                if (s.collide(motionEvent.getX(), motionEvent.getY())) {
                    Global.STAGE_NAME = s.getText();
                    terminate("StageScene");
                }
            }
        }
    }

    @Override
    public void receiveBack() {
        terminate("CharacterSelectScene");
    }

    @Override
    public void reset() {
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));

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
    }

    @Override
    public void update() {
        for (DankButton s : stages) {
            s.dankTextUpdate();
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}
