package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.SceneManager;

public class StageSelectScene extends SceneManager implements Scene {
    public StageSelectScene() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {

    }

    @Override
    public void receiveBack() {
        terminate("CharacterSelectScene");
    }

    @Override
    public void reset() {

    }

    @Override
    public void update() {
        terminate("StageScene");
    }

    private void terminate(String sceneName) {
        super.changeScene(sceneName);
    }
}
