package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.demo.R;

/**
 * Created by feng on 2018/5/20.
 */

public class StageTouchpad extends VStage {
    private Image img_tank;
    private Touchpad touchpad;//摇杆
    private float speedX, speedY;
    private boolean isMoveing;

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //tank
        img_tank = game.getImage(R.image.tank).touchOff().setOrigin(Align.center).setPosition
                (getWidth() / 2, getHeight() / 2, Align.center).show();
        //创建标题
        VLabel lab_title = game.getLabel("摇杆示例").setPosition(getWidth() / 2, getTop() - 10, Align.top)
                .touchOff().show();
        //返回
        Button btn_close = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_close.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(new StageMain());
            }
        });

        //摇杆初始化
        touchpad = game.getTouchpad(30, R.image.touchpad_bg, R.image.touchpad_center).setPosition
                (getLeft() + 5, getBottom() + 5).show();
        touchpad.addListener(new InputListener() {

            public boolean touchDown(InputEvent event, float px, float py, int pointer, int but) {
                return true;
            }

            public void touchDragged(InputEvent event, float px, float py, int pointer) {
                move(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
            }

            public void touchUp(InputEvent event, float px, float py, int pointer, int but) {
                if (isMoveing == true) {
                    isMoveing = false;
                    //加个缓停效果
                    img_tank.clearActions();
                    img_tank.addAction(Actions.moveBy(speedX * 10, speedY * 10, 0.6f, Interpolation.pow2Out));
                }
            }
        });
    }

    private void move(float kpx, float kpy) {
        isMoveing = true;
        float radian = (float) (Math.atan2(kpx, -kpy)) - MathUtils.PI / 2;
        img_tank.setRotation(radian * MathUtils.radiansToDegrees);
        speedX = MathUtils.cos(radian) * 5;
        speedY = MathUtils.sin(radian) * 5;
    }

    public void act() {
        super.act();
        if (isMoveing) {
            img_tank.moveBy(speedX, speedY);
        }
        inZone();
    }

    //将坦克限定在可视区域内
    private void inZone() {
        if (img_tank.getX() < getLeft()) {
            img_tank.setX(getLeft());
        } else if (img_tank.getRight() > getRight()) {
            img_tank.setX(getRight(), Align.right);
        }
        if (img_tank.getY() < getBottom()) {
            img_tank.setY(getBottom());
        } else if (img_tank.getTop() > getTop()) {
            img_tank.setY(getTop(), Align.top);
        }
    }

    public void start() {
        Gdx.app.log("aaaaaaa", "该方法在重启app前仅会执行一次");
    }

    @Override
    public void reStart() {

    }

    @Override
    public void show() {

    }

    @Override
    public void back() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void changing(float width, float height) {

    }
}
