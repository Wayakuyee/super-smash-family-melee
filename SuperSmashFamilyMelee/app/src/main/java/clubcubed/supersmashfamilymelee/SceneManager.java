package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import clubcubed.supersmashfamilymelee.Scenes.Scene;
import clubcubed.supersmashfamilymelee.Scenes.StageScene;

public class SceneManager implements Scene {
    private int previousScene;
    private int currentScene;
    private HashMap<String, Integer> dictionary = new HashMap<>();
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneManager() {
        previousScene = 0;
        currentScene = 0;
        // scenes.add();
        scenes.add(0, new StageScene("d", "a", "b"));
        // hardcoded rn
        /*
        dictionary.put("MainMenu", 0);
        dictionary.put("GameMenu", 1);
        dictionary.put("Options", 2);
        */
        // dictionary.put("", );
    }

    @Override
    public void draw(Canvas canvas) {
        scenes.get(currentScene).draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        scenes.get(currentScene).receiveInput(motionEvent);
    }

    @Override
    public void receiveBack() {
        scenes.get(currentScene).receiveBack();
    }

    @Override
    public void reset() {
        scenes.get(currentScene).reset();
    }

    @Override
    public void terminate() {
        scenes.get(currentScene).terminate();
    }

    @Override
    public void update() {
        scenes.get(currentScene).update();
    }
}
