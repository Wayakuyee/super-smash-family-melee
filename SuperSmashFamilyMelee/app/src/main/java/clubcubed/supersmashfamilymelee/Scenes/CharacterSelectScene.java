package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class CharacterSelectScene implements Scene {
    private DankButton bg;
    private ArrayList<DankButton> characters = new ArrayList<>();

    public CharacterSelectScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);

        for (DankButton c : characters) {
            c.draw(canvas);
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            for (DankButton c : characters) {
                if (c.collide(motionEvent.getX(), motionEvent.getY())) {
                    Global.CHARACTER_ONE_NAME = c.getText();
                    terminate("StageSelectScene");
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

        characters.add(new DankButton("FaxMcClad"));

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
    }

    @Override
    public void update() {
        for (DankButton c : characters) {
            c.dankTextUpdate();
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}
