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
    private boolean isStretching = false;//是否拉伸比例适配

    //获取上一个刚添加的actor
    public Actor pref() {
        return getChildren().peek();
    }

    //当前正在用UI链控制的Actor
    public Actor self() {
        return game.self;
    }

    public enum ActionType {
        NOEFFECTE, MOVELEFT, MOVERIGHT, MOVEODOWN, MOVEUP, FADEIN, POPUP
    }

    public VDialog(VGame game) {
        this(game, false);
    }

    public VDialog(VGame game, boolean isStretching) {
        this.isStretching = isStretching;
        this.game = game;
        stageTop = game.getTopStage();
        //在有内置虚拟按钮的安卓上会出现屏幕分辨率被改变的状况造成遮盖层无法完全覆盖底层,故强制放大1.5倍解决这个问题
        bg0 = game.getImage(stageTop.getFullWidth()*1.5f, stageTop.getFullHeight()*1.5f, Color.BLACK)
                .setPosition(game.getCenterX(), game.getCenterY(), Align.center).getActor();
        if (isStretching) {
            setScale(1f / stageTop.getRoot().getScaleX(), 1f / stageTop.getRoot().getScaleY());
        }
        addBackgroundAcition();
    }

    private float getCutWidth() {
        return isStretching ? 0 : stageTop.getCutWidth();
    }

    private float getCutHeight() {
        return isStretching ? 0 : stageTop.getCutHeight();
    }

    private float getCutAndWidth() {
        return game.WIDTH + getCutWidth();
    }

    private float getCutAndHeight() {
        return game.HEIGHT + getCutHeight();
    }

    public float getFullWidth() {
        return getCutAndWidth() + getCutWidth();
    }

    public float getFullHeight() {
        return getCutAndHeight() + getCutHeight();
    }

    public float getSafeLeft() {
        return stageTop.getSafeLeft() / getScaleX();
    }

    public float getSafeRight() {
        return stageTop.getSafeRight() / getScaleX();
    }

    public float getSafeTop() {
        return stageTop.getSafeTop() / getScaleY();
    }

    public float getSafeBottom() {
        return stageTop.getSafeBottom() / getScaleY();
    }

    //返回水平百分比坐标(自适应刘海屏)
    public float getRateX(float rate) {
        return getLeft() + (getFullWidth() - getSafeLeft() - getSafeRight()) * rate;
    }

    //返回垂直百分比坐标(自适应刘海屏)
    public float getRateY(float rate) {
        return getBottom() + (getFullHeight() - getSafeTop() - getSafeBottom()) * rate;
    }

    public float getLeft() {
        return getSafeLeft();
    }

    public float getRight() {
        return getFullWidth() - getSafeRight();
    }

    public float getTop() {
        return getFullHeight() - getSafeTop();
    }

    public float getBottom() {
        return getSafeBottom();
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
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT).setColor(color).getActor();
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

    //设置一层看不见的背景
    public void setBackground(float width, float height) {
        setBackground(game.getUI(Actor.class).setSize(width, height).getActor());
    }

    //设置全屏背景
    public void setBackground(Color color) {
        setBackground(game.getImage(getFullWidth(), getFullHeight(), color).getActor());
    }

    //设置背景
    public void setBackground(float width, float height, Color color) {
        setBackground(game.getImage(width, height, color).getActor());
    }

    //设置全屏背景
    public ActorGradient setBackground(Color color1, Color color2) {
        return setBackground(getFullWidth(), getFullHeight(), color1, color2);
    }

    //设置背景
    public ActorGradient setBackground(float width, float height, Color color1, Color color2) {
        ActorGradient bg = new ActorGradient(width, height, color1, color2);
        setBackground(bg);
        return bg;
    }

    /*
     * 设置9妹图做背景(宽，高，圆角半径)
     */
    public void setBackground(String imgName, float width, float height, int left, int right, int top, int bottom) {
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
                if (isStretching) {
                    addAction(Actions.sequence(Actions.scaleTo(0, 0), Actions.scaleTo(
                            1f / stageTop.getRoot().getScaleX(), 1f / stageTop.getRoot().getScaleY()
                            , 0.2f, Interpolation.bounce)));
                } else {
                    addAction(Actions.sequence(Actions.scaleTo(0, 0),
                            Actions.scaleTo(1, 1, 0.2f, Interpolation.bounce)));
                }
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
