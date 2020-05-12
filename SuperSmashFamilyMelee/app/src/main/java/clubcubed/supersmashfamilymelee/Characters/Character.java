package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Stages.Stage;

public abstract class Character {
    private int player;
    Character(int player) {
        this.player = player;
    }
    int getPlayer() {
        return player;
    }

    public abstract boolean isAttacking();
    public abstract int getAttackDag();
    public abstract RectF getAttackHitbox();
    public abstract int getStock();
    public abstract int getPercent();
    public abstract void collide(RectF rectF, Stage.PlatformType type);
    public abstract boolean hit(RectF rectF, int attackDag);
    public abstract void hitSuccess();
    public abstract void receiveInput(Float[] inputs);
    public abstract void receiveBluetooth(String[] inputs);
    public abstract void draw(Canvas canvas);
    public abstract void update();

    public enum MovementState {
        Crouch, Fall, Jump
    }

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