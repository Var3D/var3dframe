package var3d.net.center;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class VStage extends Stage {
    public VGame game;
    private String name = "";
    public ArrayList<Actor> bgList;
    private float cutWidth, cutHeight, cutAndWidth, cutAndHeight, fullWidth, fullHeight;
    private float safeLeft, safeRight, safeTop, safeBottom;
    private float startX, startY;
    private boolean isStretching = false;//是否拉伸比例适配
    public Rectangle safeAreaInsets = new Rectangle();//安全区域边距

    //获取上一个刚添加的actor
    public Actor pref() {
        return getRoot().getChildren().peek();
    }

    //当前正在用UI链控制的Actor
    public Actor self() {
        return game.self;
    }

    public HashMap<String, Object> getIntent() {
        return intent;
    }

    public void setIntent(HashMap<String, Object> intent) {
        this.intent = intent;
    }

    protected HashMap<String, Object> intent;

    public VStage() {
        super(new ScalingViewport(Scaling.stretch, VGame.game.WIDTH, VGame.game.HEIGHT));
        set(VGame.game);
    }

    public VStage(boolean isStretching) {
        super(new ScalingViewport(Scaling.stretch, VGame.game.WIDTH, VGame.game.HEIGHT));
        this.isStretching = isStretching;
        set(VGame.game);
    }

    public VStage(Batch batch) {
        super(new ScalingViewport(Scaling.stretch, VGame.game.WIDTH, VGame.game.HEIGHT), batch);
        set(VGame.game);
    }

    public VStage(int width, int height) {
        super(new ScalingViewport(Scaling.stretch, width, height));
        set(VGame.game);
    }

    private void set(VGame game) {
        safeAreaInsets = game.var3dListener.getSafeAreaInsets();
        bgList = new ArrayList<Actor>();
        this.game = game;
        name = getClass().getName();
        resize(0, 0);
    }

    /**
     * 进入新创建的stage时执行
     */
    public abstract void init();

    /**
     * 该方法只会执行一次，即便再次创建这个stage
     */
    public abstract void start();

    /**
     * 进入创建过的stage时执行
     */
    public abstract void reStart();

    /**
     * 进入stage时执行，无论是否创建过
     */
    public abstract void show();

    public abstract void back();

    public abstract void pause();

    public abstract void resume();

    public abstract void changing(float width, float height);

    public void resize(float width, float height) {
        changing(width, height);
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        if (isStretching) {//拉伸适配的时候,计算一下iphoneX的安全边距
            calculationCuts();
            calculationAafeArea(1, 1);
            return;
        }
        float bl = getWidth() / getHeight() * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        if (bl < 1) {
            cutWidth = (1 - bl) * getWidth() / 2f;
            cutHeight = 0;
            getRoot().setScale(bl, 1);
            getRoot().setPosition(cutWidth, 0);
            startX = cutWidth;
            startY = 0;
            cutWidth = cutWidth / getRoot().getScaleX();
            calculationAafeArea(bl, 1);
        } else if (bl >= 1) {
            cutWidth = 0;
            cutHeight = (1 - 1 / bl) * getHeight() / 2f;
            getRoot().setScale(1, 1 / bl);
            getRoot().setPosition(0, cutHeight);
            startX = 0;
            startY = cutHeight;
            cutHeight = cutHeight / getRoot().getScaleY();
            calculationAafeArea(1, bl);
        }
        calculationCuts();
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void resetStage() {
        getRoot().setPosition(startX, startY);
    }

    private void calculationAafeArea(float blx, float bly) {
        Vector2 vector2 = game.var3dListener.getAppScreenSize();//获取iphoneX的真机分辨率
        if (vector2.x != 0) {
            safeLeft = safeAreaInsets.x / vector2.x * getWidth() / blx;
            safeRight = safeAreaInsets.width / vector2.x * getWidth() / blx;
        }
        if (vector2.y != 0) {
            safeBottom = safeAreaInsets.y / vector2.y * getHeight() * bly;
            safeTop = safeAreaInsets.height / vector2.y * getHeight() * bly;
        }
    }

    //计算黑边宽度和全屏尺寸
    private void calculationCuts() {
        cutAndWidth = getWidth() + cutWidth;
        cutAndHeight = getHeight() + cutHeight;
        fullWidth = cutAndWidth + cutWidth;
        fullHeight = cutAndHeight + cutHeight;
    }

    //以下这4个方法直接合并处理等比例情况下的黑边以及iphoneX的适配问题,获取的4个点一定在最合理的边缘
    // (在非iphoneX的屏幕上就是绝对边缘,以及在拉伸适配方案中也是绝对的边缘,在iphoneX上会在安全区域的绝对边缘)
    public float getLeft() {
        return safeLeft - cutWidth;
    }

    public float getRight() {
        return cutAndWidth - safeRight;
    }

    public float getTop() {
        return cutAndHeight - safeTop;
    }

    public float getBottom() {
        return safeBottom - cutHeight;
    }

    //返回水平百分比坐标(自适应刘海屏)
    public float getRateX(float rate) {
        return getLeft() + (fullWidth - safeLeft - safeRight) * rate;
    }

    //返回垂直百分比坐标(自适应刘海屏)
    public float getRateY(float rate) {
        return getBottom() + (fullHeight - safeTop - safeBottom) * rate;
    }

    public float getSafeLeft() {
        return safeLeft;
    }

    public float getSafeRight() {
        return safeRight;
    }

    public float getSafeTop() {
        return safeTop;
    }

    public float getSafeBottom() {
        return safeBottom;
    }

    public float getCutWidth() {
        return cutWidth;
    }

    public float getCutHeight() {
        return cutHeight;
    }

    public float getCutAndWidth() {
        return cutAndWidth;
    }

    public float getCutAndHeight() {
        return cutAndHeight;
    }

    public float getFullWidth() {
        return fullWidth;
    }

    public float getFullHeight() {
        return fullHeight;
    }

    public String getName() {
        return name;
    }

    /**
     * 设置焦点(参数为null的话全屏禁止响应)
     */
    public void setFocus(Actor focusActor) {
        for (Actor actor : getActors()) {
            actor.setTouchable(Touchable.disabled);
        }
        if (focusActor != null) {
            focusActor.setTouchable(Touchable.enabled);
            pop(focusActor);
        }
    }

    /**
     * 所有Actor恢复响应
     */
    public void removeFocus() {
        for (Actor actor : getActors()) {
            actor.setTouchable(Touchable.enabled);
        }
    }

    // Actor置顶
    public void pop(Actor actor) {
        actor.remove();
        addActor(actor);
    }

    private boolean isOff = false;

    /**
     * 关闭截图按键
     */
    public void setOff() {
        isOff = true;
    }

    private int prefKey = -1;

    public boolean keyDown(int keyCode) {
        if (isOff == true) return super.keyDown(keyCode);
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            if (game.isCloseShortcut()) return super.keyDown(keyCode);
            game.var3dListener.keyDown(keyCode);
            boolean ctrl = prefKey == Input.Keys.CONTROL_LEFT || prefKey == Input.Keys.CONTROL_RIGHT || prefKey == Input.Keys.COMMA;
            prefKey = keyCode;
            if (ctrl) {//control或者command
                switch (keyCode) {
                    case Input.Keys.BACKSPACE://后退
                        if (getRoot().getTouchable() == Touchable.enabled) {
                            back();
                        }
                        break;
                    case Input.Keys.F: // 截图
                        game.Screenshot(game.getLanguage(), null, null);
                        break;
                    case Input.Keys.G:// 多语言截图
                        game.ScreenshotMultiLanguage();
                        break;
                    case Input.Keys.MINUS://语言切换
                        game.switchLanguage(false);
                        break;
                    case Input.Keys.EQUALS://语言切换
                        game.switchLanguage(true);
                        break;
                    case Input.Keys.E://编辑UI
                        game.var3dListener.edit(this);
                        break;
                    case Input.Keys.P://保存UI
                        game.var3dListener.saveUI(this);
                        break;
                }
            }
        } else if (keyCode == Input.Keys.BACK) {
            if (game.isHaveDialog()) {
                VDialog dialog = game.getDialogArray().peek();
                dialog.back();
            } else if (getRoot().getTouchable() == Touchable.enabled) {
                back();
            }
        }
        return super.keyDown(keyCode);
    }

    public boolean keyUp(int keyCode) {
        if (isOff == true) return super.keyUp(keyCode);
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            if (game.isCloseShortcut()) return super.keyDown(keyCode);
            game.var3dListener.keyUp(keyCode);
        }
        return super.keyUp(keyCode);
    }

    //单独绘制背景
    public void drawBackground() {
        if (bgList.size() != 0) {
            Batch batch = getBatch();
            batch.begin();
            batch.setColor(1, 1, 1, 1);
            for (Actor bg : bgList) {
                float prefx = bg.getX();
                float prefy = bg.getY();
                bg.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
                bg.draw(batch, getRoot().getColor().a);
                bg.setPosition(prefx, prefy);
            }
            batch.end();
        }
    }

    //单独绘制前景
    public void drawForeground() {
        super.draw();
    }

    public void draw() {
        drawBackground();
        drawForeground();
    }

    /**
     * 设置场景背景
     */
    public ArrayList<Actor> getBgList() {
        return bgList;
    }

    public Image setBackground(String imgName) {
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT).getActor();
        bgList.add(bg);
        return bg;
    }

    public Image setBackground(String imgName, Color color) {
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT).setColor(color).getActor();
        bgList.add(bg);
        return bg;
    }

    public void setBackground(Actor bg) {
        bgList.add(bg);
    }

    public Image setBackground(Color color) {
        Image bg = game.getImage(game.WIDTH, game.HEIGHT, color).getActor();
        bgList.add(bg);
        return bg;
    }

    public ActorGradient setBackground(Color color1, Color color2) {
        ActorGradient bg = new ActorGradient(game.WIDTH, game.HEIGHT, color1, color2);
        bgList.add(bg);
        return bg;
    }

    public Group createSuperRoot() {
        Group SRoot = new Group();
        SRoot.setSize(getFullWidth(), getFullHeight());
        SRoot.setPosition(game.getCenterX(), game.getCenterY(), Align.center);
        return SRoot;
    }

    private final Affine2 worldTransform = new Affine2();
    private final Matrix4 computedTransform = new Matrix4();
    private final Matrix4 oldTransform = new Matrix4();

    public Batch batchBegin() {
        Batch batch = super.getBatch();
        batch.begin();
        batch.setColor(Color.WHITE);
        oldTransform.set(batch.getTransformMatrix());
        batch.setTransformMatrix(computeTransform());
        return batch;
    }

    public void batchEnd() {
        getBatch().setTransformMatrix(oldTransform);
        getBatch().end();
    }

    protected Matrix4 computeTransform() {
        Affine2 worldTransform = this.worldTransform;
        Group root = getRoot();
        worldTransform.setToTrnRotScl(root.getX(), root.getY(), 0, root.getScaleX(), root.getScaleY());
        computedTransform.set(worldTransform);
        return computedTransform;
    }
}
