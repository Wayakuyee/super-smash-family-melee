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
    private HashMap<String, Integer> sceneIndex = new HashMap<>();
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneManager() {

        // Global.SCENE_NAME = "MainMenuScene";
        // for testing lol
        Global.SCENE_NAME = "StageScene";
        // currentScene = 0;

        scenes.add(0, new MainMenuScene());
        scenes.add(1, new GameMenuScene());
        scenes.add(2, new OptionScene());
        scenes.add(3, new CharacterSelectScene());
        scenes.add(4, new StageSelectScene());
        scenes.add(5, new StageScene());
        scenes.add(6, new AdventureScene());

        sceneIndex.put("MainMenuScene", 0);
        sceneIndex.put("GameMenuScene", 1);
        sceneIndex.put("OptionScene", 2);
        sceneIndex.put("CharacterSelectScene", 3);
        sceneIndex.put("StageSelectScene", 4);
        sceneIndex.put("StageScene", 5);
        sceneIndex.put("AdventureScene", 6);
    }

    @Override
    public void draw(Canvas canvas) {
        // scenes.get(currentScene).draw(canvas);
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        // scenes.get(currentScene).receiveInput(motionEvent);
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).receiveInput(motionEvent);
    }

    @Override
    public void receiveBack() {
        // scenes.get(currentScene).receiveBack();
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).receiveBack();
    }

    @Override
    public void reset() {
        // scenes.get(currentScene).reset();
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).reset();
    }

    @Override
    public void terminate() {
        // scenes.get(currentScene).terminate();
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).terminate();
    }

    @Override
    public void update() {
        // scenes.get(currentScene).update();
        scenes.get(sceneIndex.get(Global.SCENE_NAME)).update();
    }
}
