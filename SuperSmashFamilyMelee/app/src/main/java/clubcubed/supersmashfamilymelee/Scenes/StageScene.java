package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.*;
import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.*;
import clubcubed.supersmashfamilymelee.Stages.Stage;
import clubcubed.supersmashfamilymelee.Stages.*;

public class StageScene implements Scene {
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private Stage stage;

    public StageScene() {
        // un hardcode it later
        stage = new LastJourneyEnd();
        characterOne = new FaxMcClad();
        characterTwo = new FaxMcClad();


        controller = new Controller();
    }

    @Override
    public void draw(Canvas canvas) {

        stage.draw(canvas);
        characterTwo.draw(canvas);
        characterOne.draw(canvas);
        controller.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        characterOne.receiveInput(controller.receiveInput(motionEvent));
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
        // check stage collide

        // check character attack collide


        stage.update();
        characterTwo.update();
        characterOne.update();
        controller.update();
    }
}
