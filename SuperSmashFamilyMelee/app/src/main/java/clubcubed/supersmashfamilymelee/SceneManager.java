package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.util.Log;
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
    private Global.SCENE_NAME sceneName;
    private Scene scene;

    public SceneManager() {
        changeScene();
    }

    private void changeScene() {
        sceneName = Global.CURRENT_SCENE;
        switch (sceneName) {
            case MAIN_MENU_SCENE:
                scene = new MainMenuScene();
                break;
            case GAME_MENU_SCENE:
                scene = new GameMenuScene();
                break;
            case OPTION_SCENE:
                scene = new OptionScene();
                break;
            case CHARACTER_SELECT_SCENE:
                scene = new CharacterSelectScene();
                break;
            case STAGE_SELECT_SCENE:
                scene = new StageSelectScene();
                break;
            case STAGE_SCENE:
                scene = new StageScene();
                break;
            case ADVENTURE_SCENE:
                scene = new AdventureScene();
                break;
            default:
                Log.d("SceneManager", "undefined scene name" + sceneName.name());
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
    public void update() {
        if (Global.CURRENT_SCENE != sceneName) {
            changeScene();
        }
        scene.update();
    }
}
