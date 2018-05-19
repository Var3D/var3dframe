package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public abstract class VDialog extends Group {
    public VGame game;
    private Image bg0 = null;
    private float endAlpha = 0.8f;
    private VStage stageTop;

    public enum ActionType {
        NOEFFECTE, MOVELEFT, MOVERIGHT, MOVEODOWN, MOVEUP, FADEIN, POPUP
    }

    public VDialog(VGame game) {
        this.game = game;
        stageTop = game.getTopStage();
        bg0 = game.getImage(game.getTopStage().getFullWidth(), game.getTopStage().getFullHeight(), Color.BLACK)
                .setPosition(game.getCenterX(), game.getCenterY(), Align.center).getActor();
        init();
        addBackgroundAcition();
    }

    public float getCutWidth() {
        return stageTop.getCutWidth();
    }

    public float getCutHeight() {
        return stageTop.getCutHeight();
    }

    public float getCutAndWidth() {
        return stageTop.getCutAndWidth();
    }

    public float getCutAndHeight() {
        return stageTop.getCutAndHeight();
    }

    public float getFullWidth() {
        return stageTop.getFullWidth();
    }

    public float getFullHeight() {
        return stageTop.getFullHeight();
    }

    public float getSafeLeft() {
        return stageTop.getSafeLeft();
    }

    public float getSafeRight() {
        return stageTop.getSafeRight();
    }

    public float getSafeTop() {
        return stageTop.getSafeTop();
    }

    public float getSafeBottom() {
        return stageTop.getSafeBottom();
    }

    //返回水平百分比坐标(自适应刘海屏)
    public float getRateX(float rate) {
        return stageTop.getRateX(rate);
    }

    //返回垂直百分比坐标(自适应刘海屏)
    public float getRateY(float rate) {
        return stageTop.getRateY(rate);
    }

    public float getLeft() {
        return stageTop.getLeft();
    }

    public float getRight() {
        return stageTop.getRight();
    }

    public float getTop() {
        return stageTop.getTop();
    }

    public float getBottom() {
        return stageTop.getBottom();
    }

    public abstract void init();

    /**
     * 除第一次外每次打开都会执行
     */
    public abstract void reStart();

    /**
     * 每次都执行
     */
    public abstract void show();

    /**
     * 弹出dialog仅执行第一次
     */
    public abstract void start();

    /**
     * 当该dialog被其他dialog覆盖时调用
     */
    public abstract void pause();

    /**
     * 当该dialog重新恢复到顶层时调用
     */
    public abstract void resume();

    private void setThis(Actor actor) {
        setSize(actor.getWidth(), actor.getHeight());
        setOrigin(Align.center);
        setPosition(game.getCenterX(), game.getCenterY(), Align.center);
        addActor(actor);
    }

    /**
     * 普通图片背景
     */
    public void setBackground(String imgName) {
        Image bg = game.getImage(imgName).getActor();
        setThis(bg);
    }

    /*
     * 普通图片背景带颜色
     */
    public void setBackground(String imgName, Color color) {
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT)
                .setColor(color).getActor();
        setThis(bg);
    }

    /*
     * 设置9妹图做背景(宽，高，圆角半径)
     */
    public void setBackground(String imgName, float width, float height, int r) {
        NinePatch path = game.getNinePatch(imgName, r);
        Image bg = new Image(path);
        bg.setSize(width, height);
        setThis(bg);
    }

    /*
     * 设置9妹图做背景(宽，高，圆角半径)
     */
    public void setBackground(String imgName, float width, float height,
                              int left, int right, int top, int bottom) {
        NinePatch path = game.getNinePatch(imgName, left, right, top, bottom);
        Image bg = new Image(path);
        bg.setSize(width, height);
        setThis(bg);
    }

    /**
     * 用户提供背景对象
     */
    public void setBackground(Actor bg) {
        setThis(bg);
    }

    /**
     * 设置背景透明度
     *
     * @param endAlpha
     */
    public void setEndAlpha(float endAlpha) {
        this.endAlpha = endAlpha;
    }

    public void addBackgroundAcition() {
        bg0.clearActions();
        bg0.addAction(Actions.sequence(Actions.alpha(0),
                Actions.alpha(endAlpha, 0.5f)));
    }

    public void draw(Batch batch, float parentAlpha) {
        bg0.act(Gdx.graphics.getDeltaTime());
        bg0.draw(batch, 1);
        super.draw(batch, parentAlpha);
    }

    public void setStartActions(ActionType type) {
        switch (type) {
            case NOEFFECTE:// 无效果
                break;
            case POPUP:// 弹出
                addAction(Actions.sequence(Actions.scaleTo(0, 0),
                        Actions.scaleTo(1, 1, 0.2f, Interpolation.bounce)));
                break;
            case MOVERIGHT:// 从左到右
                addAction(Actions.sequence(Actions.moveTo(-game.getStage()
                        .getCutWidth() - getWidth(), getY()), Actions.moveTo(
                        game.getCenterX() - getWidth() / 2, getY(), 0.2f,
                        Interpolation.bounce)));
                break;
            case MOVELEFT:// 从右到左
                addAction(Actions.sequence(Actions.moveTo(game.WIDTH
                        + game.getStage().getCutWidth(), getY()), Actions.moveTo(
                        game.getCenterX() - getWidth() / 2, getY(), 0.2f,
                        Interpolation.bounce)));
                break;
            case MOVEODOWN:// 从上到下
                addAction(Actions.sequence(Actions.moveTo(getX(), game.HEIGHT
                        + game.getStage().getCutHeight()), Actions.moveTo(getX(),
                        game.getCenterY() - getHeight() / 2, 0.2f,
                        Interpolation.bounce)));
                break;
            case MOVEUP:// 从下到上
                addAction(Actions.sequence(Actions.moveTo(getX(), -game.getStage()
                        .getCutHeight() - getHeight()), Actions.moveTo(getX(),
                        game.getCenterY() - getHeight() / 2, 0.2f,
                        Interpolation.bounce)));
                break;
        }
    }
}
