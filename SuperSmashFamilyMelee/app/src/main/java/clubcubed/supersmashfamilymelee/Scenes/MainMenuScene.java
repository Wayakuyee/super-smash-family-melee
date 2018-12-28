package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Global;

public class MainMenuScene implements Scene {

    public MainMenuScene() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {

    }

    @Override
    public void receiveBack() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void update() {

    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}
