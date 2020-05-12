package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class Shraek extends Character {
    private RectF character;
    private Paint characterPaint;
    private RectF characterTop;
    private Paint characterTopPaint;
    private RectF characterBot;
    private Paint characterBotPaint;

    private Float[] inputs;

    // attack dag means attack damage and lag
    private int attackDag;
    private int attackDagMax;

    //-1 : no attack
    // 0 : neutral
    // 1 : up
    // 2 : down
    // 3 : left
    // 4 : right
    private int attackState;

    private MovementState state;
    private int jumpCount;
    private int jumpFrame;

    private double startTime;

    private int percent;
    private int stock;
    private float weight;
    private float speed;

    private double c;

    public Shraek(int player) {
        super(player);
        inputs = new Float[]{0f, 0f, 0f, 0f};

        character = new RectF(0, 0, 50*Global.GAME_RATIO, 100*Global.GAME_RATIO);
        characterPaint = new Paint();
        characterPaint.setColor(Color.argb(0, 0,0,0));

        characterTop = new RectF();
        characterTopPaint = new Paint();
        characterTopPaint.setColor(Color.argb(200, 0,150,0));

        characterBot = new RectF();
        characterBotPaint = new Paint();
        characterBotPaint.setColor(Color.argb(200, 230, 255, 230));

        stock = 4;
        c = 60.0/1000;
        weight = Global.GAME_RATIO * 2f;
        speed = Global.GAME_RATIO * 2.5f;

        spawn();
    }

    private void spawn() {
        float height = character.height();
        float width = character.width();

        character.top = 0;
        character.bottom = height;
        move(0, 0);

        if (getPlayer() == 1) {
            // top left, 1/4 screen
            character.left = (Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) - (width/2);
            character.right = (Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) + (width/2);
        } else {
            // top right, 3/4 screen
            character.left = (3*Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) - (width/2);
            character.right = (3*Global.GAME_WIDTH/4*Global.GAME_RATIO +Global.GAME_DIFFERENCE) + (width/2);
        }

        state = MovementState.Fall;

        jumpCount = 1;
        startTime = 0f;
        attackState = -1;
        attackDag = 0;
        attackDagMax = 0;
        percent = 0;
    }

    private void die() {
        stock -= 1;
        spawn();
    }

    private void jump() {
        if (jumpCount > 0 && jumpFrame < 2) {
            jumpCount -= 1;
            state = MovementState.Jump;
            jumpFrame = 10;
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
        characterPaint.setColor(Color.argb(200, 255,255,255));
        characterTopPaint.setColor(Color.argb(0, 0,0,0));
        characterBotPaint.setColor(Color.argb(0, 0, 0, 0));
        attackState = 0;
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void upAttack() {
        move(0, -20*Global.GAME_RATIO);
        characterTopPaint.setColor(Color.argb(200, 255,0,0));
        attackState = 1;
        attackDag = 60;
        attackDagMax = 60;
        startTime = System.currentTimeMillis();
    }

    private void downAttack() {
        move(0, Global.GAME_RATIO);
        characterBotPaint.setColor(Color.argb(200, 255,0,0));
        attackState = 2;
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void leftAttack() {
        move(-2*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,255,0));
        characterTopPaint.setColor(Color.argb(0, 0,0,0));
        characterBotPaint.setColor(Color.argb(0, 0, 0, 0));
        attackState = 3;
        attackDag = 10;
        attackDagMax = 10;
        startTime = System.currentTimeMillis();
    }

    private void rightAttack() {
        move(2*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,255, 0));
        characterTopPaint.setColor(Color.argb(0, 0,0,0));
        characterBotPaint.setColor(Color.argb(0, 0, 0, 0));
        attackState = 4;
        attackDag = 10;
        attackDagMax = 10;
        startTime = System.currentTimeMillis();
    }

    private void move(float x, float y) {
        x *= 2.0f;
        character.top += y;
        character.bottom += y;
        character.left += x;
        character.right += x;

        // update character top and bottom colours
        float h = character.height() / 3;
        characterTop.set(character.left, character.top,
                character.right, character.top + h);
        characterBot.set(character.left, character.top + h,
                character.right, character.bottom);
    }

    private void action() {
        // 0 : neutral
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
        return attackState != -1;
    }

    @Override
    public int getAttackDag() {
        return attackDag;
    }

    @Override
    public RectF getAttackHitbox() {
        if (attackState == 1)
            return new RectF(characterTop);
        else if (attackState == 2)
            return new RectF(characterBot);
        else
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
        if (RectF.intersects(rectF, character)) {
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
        attackState = -1;
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
        canvas.drawRect(characterTop, characterTopPaint);
        canvas.drawRect(characterBot, characterBotPaint);

        if (attackDag > 0)
            canvas.drawRect(character, characterPaint);
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
            move(0, -1/2f * jumpFrame * weight);
            jumpFrame -= 1;
        } else {
            // done jumping
            state = MovementState.Fall;
        }

        // attack lag passing by
        if (attackDag > 0)
            attackDag = (int)((attackDagMax - ((System.currentTimeMillis() - startTime) * c)) +0.5);

        // TODO: not very efficient
        // reset and repaint if not attacking
        if (attackDag <= 0) {
            attackState = -1;
            characterPaint.setColor(Color.argb(0, 0,0,0));
            characterTopPaint.setColor(Color.argb(200, 0,150,0));
            characterBotPaint.setColor(Color.argb(200 , 230, 255, 230));
        }
    }
}