package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;

public class FaxMcClad implements Character {
    // attack dag means attack damage and lag
    private int attackDag;
    private int attackDagMax;

    private String state;

    private float startTime;

    private int jumpCount;
    private float percent;
    private int stock;

    private double c = 100/3;

    public FaxMcClad() {
        startTime = 0f;
        attackDag = 0;
        state = "stand";
        percent = 0f;
        stock = 4;
    }

    private void jump() {

    }

    private void run() {

    }

    private void crouch() {

    }

    private void neutralAttack() {
        attackDag = 10;
        attackDagMax = 10;
        startTime = System.currentTimeMillis();
    }

    private void upAttack() {
        attackDag = 50;
        attackDagMax = 50;
        startTime = System.currentTimeMillis();
    }

    private void downAttack() {
        attackDag = 50;
        attackDagMax = 50;
        startTime = System.currentTimeMillis();
    }

    private void leftAttack() {
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void rightAttack() {
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }


    @Override
    public int getAttackDag() {
        return attackDag;
    }

    @Override
    public void hit(float[] direction) {

    }

    @Override
    public void receiveInput(float[] inputs) {
        // if attack
        if (inputs[3] > 0) {

            if (inputs[0] == 0f && inputs[1] == 0f) {
                neutralAttack();

            } else if (Math.abs(inputs[0]) < Math.abs(inputs[1])) {
                if (inputs[1] > 0f) {
                    upAttack();
                } else {
                    downAttack();
                }

            } else {
                if (inputs[0] < 0f) {
                    leftAttack();
                } else {
                    rightAttack();
                }
            }

            return;
        }

        // if jump

        // if move

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {
        if (attackDag != 0) {
            attackDag = (int)(attackDagMax - Math.round( (System.currentTimeMillis() - startTime) / c));
        }
    }
}
