package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;

public class AdventureScene implements Scene {
    private DankButton bg;

    public AdventureScene() {
        bg = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "lol"
        );
        bg.setTextSize(Global.SCREEN_HEIGHT/2);
        bg.setTextARGB(255, 255, 255, 255);
        bg.setRectARGB(255, 100, 100, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {

    }

    @Override
    public void receiveBack() {
        terminate(Global.SCENE_NAME.GAME_MENU_SCENE);
    }

    @Override
    public void update() {

    }

    private void terminate(Global.SCENE_NAME sceneName) {
        Global.CURRENT_SCENE = sceneName;
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected())
            Global.BLUETOOTH_DATA.write("scene" + sceneName.name());
    }
}
