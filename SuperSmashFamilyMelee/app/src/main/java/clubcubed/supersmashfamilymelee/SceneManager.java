package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import clubcubed.supersmashfamilymelee.Scenes.AdventureScene;
import clubcubed.supersmashfamilymelee.Scenes.CharacterSelectScene;
import clubcubed.supersmashfamilymelee.Scenes.GameMenuScene;
import clubcubed.supersmashfamilymelee.Scenes.MainMenuScene;
import clubcubed.supersmashfamilymelee.Scenes.OptionScene;
import clubcubed.supersmashfamilymelee.Scenes.Scene;
import clubcubed.supersmashfamilymelee.Scenes.StageScene;
import clubcubed.supersmashfamilymelee.Scenes.StageSelectScene;

public class SceneManager implements Scene {
    // private int currentScene;
    private HashMap<String, Scene> scenes = new HashMap<>();
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneManager() {

        // Global.SCENE_NAME = "MainMenuScene";
        // for testing lol
        Global.SCENE_NAME = "StageScene";

        scenes.put("MainMenuScene", new MainMenuScene());
        scenes.put("GameMenuScene", new GameMenuScene());
        scenes.put("OptionScene", new OptionScene());
        scenes.put("CharacterSelectScene", new CharacterSelectScene());
        scenes.put("StageSelectScene", new StageSelectScene());
        scenes.put("StageScene", new StageScene());
        scenes.put("AdventureScene", new AdventureScene());
    }

    @Override
    public void draw(Canvas canvas) {
        // scenes.get(currentScene).draw(canvas);
        scenes.get(Global.SCENE_NAME).draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        // scenes.get(currentScene).receiveInput(motionEvent);
        scenes.get(Global.SCENE_NAME).receiveInput(motionEvent);
    }

    @Override
    public void receiveBack() {
        // scenes.get(currentScene).receiveBack();
        scenes.get(Global.SCENE_NAME).receiveBack();
    }

    @Override
    public void reset() {
        // scenes.get(currentScene).reset();
        scenes.get(Global.SCENE_NAME).reset();
    }

    @Override
    public void terminate() {
        // scenes.get(currentScene).terminate();
        scenes.get(Global.SCENE_NAME).terminate();
    }

    @Override
    public void update() {
        // scenes.get(currentScene).update();
        scenes.get(Global.SCENE_NAME).update();
    }
}
