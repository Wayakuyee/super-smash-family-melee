package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Scenes.AdventureScene;
import clubcubed.supersmashfamilymelee.Scenes.CharacterSelectScene;
import clubcubed.supersmashfamilymelee.Scenes.GameMenuScene;
import clubcubed.supersmashfamilymelee.Scenes.MainMenuScene;
import clubcubed.supersmashfamilymelee.Scenes.OptionScene;
import clubcubed.supersmashfamilymelee.Scenes.Scene;
import clubcubed.supersmashfamilymelee.Scenes.StageScene;
import clubcubed.supersmashfamilymelee.Scenes.StageSelectScene;

public class SceneManager implements Scene {
    private Scene scene;

    public SceneManager() {

    }

    public SceneManager(int i) {
        // changeScene("MainMenuScene");
        // changeScene("GameMenuScene");
        changeScene("StageScene");
    }

    protected void changeScene(String sceneName) {
        switch (sceneName) {
            case "MainMenuScene":
                scene = new MainMenuScene();
                break;
            case "GameMenuScene":
                scene = new GameMenuScene();
                break;
            case "OptionScene":
                scene = new OptionScene();
                break;
            case "CharacterSelectScene":
                scene = new CharacterSelectScene();
                break;
            case "StageSelectScene":
                scene = new StageSelectScene();
                break;
            case "StageScene":
                scene = new StageScene();
                break;
            case "AdventureScene":
                scene = new AdventureScene();
                break;
            default:
                scene = new MainMenuScene();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        scene.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        scene.receiveInput(motionEvent);
    }

    @Override
    public void receiveBack() {
        scene.receiveBack();
    }

    @Override
    public void reset() {
        scene.reset();
    }

    @Override
    public void update() {
        scene.update();
    }
}
