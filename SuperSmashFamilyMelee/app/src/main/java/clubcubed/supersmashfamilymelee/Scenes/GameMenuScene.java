package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.SceneManager;

public class GameMenuScene extends SceneManager implements Scene {
    private DankButton bg;
    private DankButton adventure;
    private DankButton melee;
    private DankButton bluetooth;


    public GameMenuScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        adventure.draw(canvas);
        melee.draw(canvas);
        bluetooth.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP) {
            if (adventure.collide(motionEvent.getX(), motionEvent.getY())) {
                terminate("AdventureScene");
            } else if (melee.collide(motionEvent.getX(), motionEvent.getY())){
                terminate("CharacterSelectScene");
            } else if (bluetooth.collide(motionEvent.getX(), motionEvent.getY())) {

            }
        }
    }

    @Override
    public void receiveBack() {
        terminate("MainMenuScene");
    }

    @Override
    public void reset() {
        bg = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        bg.setRectARGB(150, 0, 255, 0);

        float w = Global.SCREEN_WIDTH/4;
        float h = Global.SCREEN_HEIGHT/10;

        adventure = new DankButton(new RectF(w, h, 3*w, 3*h),"Adventure Mode");
        adventure.setRectARGB(255, 200, 150, 0);
        adventure.setTextARGB(255, 255, 255, 255);
        adventure.setTextSize(h);
        adventure.setPulse(10);

        melee = new DankButton(new RectF(w, 4*h, 3*w, 6*h),"Melee");
        melee.setRectARGB(255, 255, 100, 0);
        melee.setTextARGB(255, 255, 255, 255);
        melee.setTextSize(h);
        melee.setPulse(10);

        bluetooth = new DankButton(new RectF(w, 7*h, 3*w, 9*h),"Enable Bluetooth");
        bluetooth.setRectARGB(255, 0, 100, 255);
        bluetooth.setTextARGB(255, 255, 255, 255);
        bluetooth.setTextSize(h);
        bluetooth.setPulse(10);
    }

    @Override
    public void update() {
        bg.dankRectUpdate();
        adventure.pulseUpdate();
        melee.pulseUpdate();
        bluetooth.pulseUpdate();
    }

    private void terminate(String sceneName) {
        super.changeScene(sceneName);
    }
}