package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class MainMenuScene implements Scene {
    private DankButton bg;

    public MainMenuScene() {
        // TODO: add content
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "pog");
        bg.setRectARGB(150, 255, 255, 0);
        bg.setTextARGB(255, 255, 255, 255);
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        terminate(Global.SCENE_NAME.GAME_MENU_SCENE);
    }

    @Override
    public void receiveBack() {

    }

    @Override
    public void update() {
        bg.dankRectUpdate();
    }

    private void terminate(Global.SCENE_NAME sceneName) {
        Global.CURRENT_SCENE = sceneName;
        if (Global.BLUETOOTH_DATA != null) {
            Global.BLUETOOTH_DATA.cancel();
            Global.BLUETOOTH_DATA = null;
        }
    }
}
