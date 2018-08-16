package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;

public interface Character {
    int getAttackDag();
    void hit(float[] direction);
    void receiveInput(float[] inputs);
    void draw(Canvas canvas);
    void update();

    /*

    state:


    movementState:
    stand
    up
    down
    left
    right

     */

    /* copy paste this lol
    private void jump() {

    }

    private void run() {

    }

    private void crouch() {

    }

    private void neutralAttack() {

    }

    private void upAttack() {

    }

    private void downAttack() {

    }

    private void leftAttack() {

    }
    private void rightAttack() {

    }
    */
}