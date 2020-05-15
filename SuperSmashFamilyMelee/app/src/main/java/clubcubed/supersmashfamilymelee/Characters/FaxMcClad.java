package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class FaxMcClad extends Character {
    private DankButton indicator;
    private RectF character;
    private Paint characterPaint;

    private Float[] inputs;

    // attack dag means attack damage and lag
    private int attackDag;
    private int attackDagMax;
    private boolean attacking;

    private MovementState state;
    private int jumpCount;
    private int jumpFrame;

    private double startTime;

    private int percent;
    private int stock;
    private float weight;
    private float speed;

    private double c;

    public FaxMcClad(int player) {
        super(player);
        inputs = new Float[]{0f, 0f, 0f, 0f};

        character = new RectF(0, 0, 50*Global.GAME_RATIO, 100*Global.GAME_RATIO);
        indicator = new DankButton(String.valueOf(getPlayer()));
        indicator.setRectARGB(0, 0, 0, 0);
        indicator.setTextSize(character.height()/3);
        indicator.setTextARGB(255, 255, 255, 255);

        characterPaint = new Paint();
        characterPaint.setColor(Color.argb(200 , 255,140,0));

        stock = 4;
        c = 60.0/1000;
        weight = Global.GAME_RATIO * 3f;
        speed = Global.GAME_RATIO * 4f;

        spawn();
    }

    private void spawn() {
        float height = character.height();
        float width = character.width();

        character.top = 0;
        character.bottom = height;
        if (getPlayer() == 1) {
            // top left, 1/4 screen
            character.left = (Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) - (width/2);
            character.right = (Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) + (width/2);
        } else {
            // top right, 3/4 screen
            character.left = (3*Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) - (width/2);
            character.right = (3*Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) + (width/2);
        }
        move(0, 0);

        state = MovementState.Fall;

        jumpCount = 1;
        startTime = 0f;
        attackDag = 0;
        attackDagMax = 0;
        attacking = false;
        percent = 0;
    }

    private void die() {
        stock -= 1;
        if (stock > 0)
            spawn();
    }

    private void jump() {
        if (jumpCount > 0 && jumpFrame < 2) {
            jumpCount -= 1;
            state = MovementState.Jump;
            jumpFrame = 5;
        }
    }

    private void run(float x) {
        move(x*speed, 0);
    }

    private void crouch(float x, float y) {
        state = MovementState.Crouch;
        move(x*speed, -y*speed);
    }

    private void neutralAttack() {
        characterPaint.setColor(Color.argb(200, 255,255,0));
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void upAttack() {
        move(0, -150*Global.GAME_RATIO);
        characterPaint.setColor(Color.argb(200, 255,0,0));
        attackDag = 120;
        attackDagMax = 120;
        startTime = System.currentTimeMillis();
    }

    private void downAttack() {
        characterPaint.setColor(Color.argb(200, 255,255,255));
        attackDag = 2;
        attackDagMax = 2;
        startTime = System.currentTimeMillis();
    }

    private void leftAttack() {
        move(-50*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,0,255));
        attackDag = 40;
        attackDagMax = 40;
        startTime = System.currentTimeMillis();
    }

    private void rightAttack() {
        move(50*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,0,255));
        attackDag = 40;
        attackDagMax = 40;
        startTime = System.currentTimeMillis();
    }

    private void move(float x, float y) {
        x *= 2.0f;
        character.top += y;
        character.bottom += y;
        character.left += x;
        character.right += x;

        indicator.setPosition(character.left, character.top, character.right, character.bottom);
    }

    private void action() {
        // 0 : stand
        // 1 : up
        // 2 : down
        // 3 : left
        // 4 : right
        int direction = 0;

        // get control stick direction
        if (!(inputs[0] == 0 && inputs[1] == 0)) {
            if (Math.abs(inputs[0]) > Math.abs(inputs[1]))
                direction = (inputs[0] < 0f) ? 3 : 4;
            else
                direction = (inputs[1] > 0f) ? 1 : 2;
        }

        // check attack, cannot attack while in attack lag
        if (inputs[3] >= 0.5 && attackDag <= 0) {
            attacking = true;
            if (direction == 1)
                upAttack();
            else if (direction == 2)
                downAttack();
            else if (direction == 3)
                leftAttack();
            else if (direction == 4)
                rightAttack();
            else
                neutralAttack();
        }

        // check jump, cannot jump while in attack lag
        if (inputs[2] >= 0.5f && attackDag <= 0)
            jump();

        // check run
        if (direction >= 3)
            run(inputs[0]);
        // check crouch, cannot be jumping
        else if (direction == 2 && state != MovementState.Jump)
            crouch(inputs[0], inputs[1]);

        // check falling
        if (direction != 2 && state == MovementState.Crouch)
            state = MovementState.Fall;
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public int getAttackDag() {
        return attackDag;
    }

    @Override
    public RectF getAttackHitbox() {
        return new RectF(character);
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public int getPercent() {
        return percent;
    }

    @Override
    public void collide(RectF rectF, Stage.PlatformType type) {
        if (type == Stage.PlatformType.BlastntZone && !RectF.intersects(rectF, character)) {
            // out of bounds
            die();
        } else if (type == Stage.PlatformType.HardPlatform && RectF.intersects(rectF, character)) {
            if (state != MovementState.Jump) {
                // teleport on top of stage
                move(0, -(character.bottom - rectF.top));
                jumpCount = 2;
            }
        } else if (type == Stage.PlatformType.SoftPlatform && RectF.intersects(rectF, character)) {
            if (state != MovementState.Jump && state != MovementState.Crouch) {
                // teleport above stage
                move(0, -(character.bottom - rectF.top));
                jumpCount = 2;
            }
        }
    }

    @Override
    public boolean hit(RectF rectF, int attackDag) {
        if (attackDag > 0 && RectF.intersects(rectF, character)) {
            percent += attackDag;
            /*
            float dX = character.centerX() - rectF.centerX();
            float dY = character.centerY() - rectF.centerY();
            float dMax = Math.max(dX, dY);
            dX /= dMax;
            dY /= dMax;
            */
            move(
                    ((character.centerX()-rectF.centerX())/Global.GAME_RATIO) * percent/100,
                    ((character.centerY()-rectF.centerY())/Global.GAME_RATIO) * percent/100
            );
            return true;
        }

        return false;
    }

    @Override
    public void hitSuccess() {
        // only hit once
        attacking = false;
    }

    @Override
    public void receiveInput(Float[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public void receiveBluetooth(String[] inputs) {
        this.inputs = new Float[]{
                Float.parseFloat(inputs[0]),
                Float.parseFloat(inputs[1]),
                Float.parseFloat(inputs[2]),
                Float.parseFloat(inputs[3])};
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(character, characterPaint);
        indicator.draw(canvas);
    }

    @Override
    public void update() {
        // compute input actions
        action();

        // apply gravity
        if (state != MovementState.Jump) {
            // falling
            move(0, weight);
        } else if (jumpFrame > 0) {
            // jumping
            move(0, -1 * jumpFrame * weight);
            jumpFrame -= 1;
        } else {
            // done jumping
            state = MovementState.Fall;
        }

        // attack lag passing by
        if (attackDag > 0)
            attackDag = (int)((attackDagMax - ((System.currentTimeMillis() - startTime) * c)) +0.5);

        // repaint character if no attack
        if (attackDag <= 0) {
            attacking = false;
            characterPaint.setColor(Color.argb(200 , 255,140,0));
        }
    }
}