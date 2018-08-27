package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.RectF;

public interface Character {
    int getAttackDag();
    RectF getCharacter();
    int getStock();
    void collide(RectF rectF, String type);
    void hit(RectF rectF, int attackDag);
    void receiveInput(float[] inputs);
    void draw(Canvas canvas);
    void update();

    /*

    state:
    stand
    jump
    fall
    run
    hit

     */

    /* copy paste this lol
    private void spawn() {

    }

    private void die() {

    }

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