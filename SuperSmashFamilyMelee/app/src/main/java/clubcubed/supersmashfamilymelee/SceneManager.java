package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneManager implements Scene {
    private int previousScene;
    private int currentScene;
    private HashMap<String, Integer> dictionary = new HashMap<>();
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneManager() {
        previousScene = 0;
        currentScene = 0;
        // scenes.add();

        dictionary.put("MainMenu", 0);
        dictionary.put("GameMenu", 1);
        dictionary.put("Options", 2);
        // dictionary.put("", );
    }

    public static void changeScene(int newScene) {


    }

    public static void changeScene(String newScene) {


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
    public void terminate() {

    }

    @Override
    public void update() {

    }
}
