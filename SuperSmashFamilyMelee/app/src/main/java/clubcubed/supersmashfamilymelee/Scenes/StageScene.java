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

    public StageScene(String stage, String characterOne, String characterTwo) {
        // character and stage select stuff
        // this.one = Global.;

        // hardcoded for testing
        this.stage = new LastJourneyEnd();

        this.characterOne = new FaxMcClad();
        this.characterTwo = new FaxMcClad();

        this.controller = new Controller();
    }

    @Override
    public void draw(Canvas canvas) {

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

    }
}
