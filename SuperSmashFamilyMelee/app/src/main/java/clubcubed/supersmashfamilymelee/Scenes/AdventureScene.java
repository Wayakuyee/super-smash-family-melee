package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.SceneManager;

public class AdventureScene extends SceneManager implements Scene {
    public AdventureScene() {

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
        super.changeScene(sceneName);
    }
}
