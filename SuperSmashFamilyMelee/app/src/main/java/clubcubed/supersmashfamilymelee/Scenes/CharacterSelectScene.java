package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.SceneManager;

public class CharacterSelectScene extends SceneManager implements Scene {
    public CharacterSelectScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {

    }

    @Override
    public void receiveBack() {
        terminate("GameMenuScene");
    }

    @Override
    public void reset() {

    }

    @Override
    public void update() {
        terminate("StageSelectScene");
    }

    private void terminate(String sceneName) {
        super.changeScene(sceneName);
    }
}
