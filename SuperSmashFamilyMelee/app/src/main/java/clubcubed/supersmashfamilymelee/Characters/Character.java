package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;

public interface Character {
    void hit(float[] direction);
    void receiveInput(float[] inputs);
    void draw(Canvas canvas);
    void update();

    /* copy paste this lol
    private void jump() {

    }

    private void run() {

    }

    private void crouch() {

    }

    private void wavedash() {

    }

    private void neutralAttack() {

    }

    private void upAttack() {

    }

    private void downAttack() {

    }

    private void sideAttack() {

    }
     */
}