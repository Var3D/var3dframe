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
    private boolean isStretching = false;//是否拉伸比例适配
    public Rectangle safeAreaInsets = new Rectangle();//安全区域边距

    public HashMap<String, Object> getIntent() {
        return intent;
    }

    public void setIntent(HashMap<String, Object> intent) {
        this.intent = intent;
    }

    protected HashMap<String, Object> intent;

    public VStage(VGame game) {
        super(new ScalingViewport(Scaling.stretch, game.WIDTH, game.HEIGHT));
        set(game);
    }

    public VStage(VGame game, boolean isStretching) {
        super(new ScalingViewport(Scaling.stretch, game.WIDTH, game.HEIGHT));
        this.isStretching = isStretching;
        set(game);
    }

    public VStage(VGame game, Batch batch) {
        super(new ScalingViewport(Scaling.stretch, game.WIDTH, game.HEIGHT), batch);
        set(game);
    }

    public VStage(VGame game, int width, int height) {
        super(new ScalingViewport(Scaling.stretch, width, height));
        set(game);
    }

    private void set(VGame game) {
        safeAreaInsets = game.var3dListener.getSafeAreaInsets();
        bgList = new ArrayList<Actor>();
        this.game = game;
        name = getClass().getName();
        resize(0, 0);
    }

    public abstract void init();

    public abstract void reStart();

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
            cutWidth = cutWidth / getRoot().getScaleX();
            calculationAafeArea(bl, 1);
        } else if (bl > 1) {
            cutWidth = 0;
            cutHeight = (1 - 1 / bl) * getHeight() / 2f;
            getRoot().setScale(1, 1 / bl);
            getRoot().setPosition(0, cutHeight);
            cutHeight = cutHeight / getRoot().getScaleY();
            calculationAafeArea(1, bl);
        } else {
            calculationAafeArea(1, 1);
        }
        calculationCuts();
    }

    private void calculationAafeArea(float blx, float bly) {
        Vector2 vector2 = game.var3dListener.getAppScreenSize();//获取iphoneX的真机分辨率
        safeLeft = safeAreaInsets.x / vector2.x * getWidth() / blx;
        safeRight = safeAreaInsets.width / vector2.x * getWidth() / blx;
        safeBottom = safeAreaInsets.y / vector2.y * getHeight() * bly;
        safeTop = safeAreaInsets.height / vector2.y * getHeight() * bly;
    }

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
        return getLeft() + (fullWidth-safeLeft-safeRight) * rate;
    }

    //返回垂直百分比坐标(自适应刘海屏)
    public float getRateY(float rate) {
        return getBottom() + (fullHeight-safeTop-safeBottom) * rate;
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

    public boolean keyDown(int arg0) {
        if (isOff == true) return false;
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            game.var3dListener.keyDown(arg0);
            if (arg0 == Input.Keys.DEL) {
                back();
            } else if (arg0 == Input.Keys.F) {
                // 截图
                game.Screenshot(game.getLanguage(), null, null);
            } else if (arg0 == Input.Keys.G) {
                // 多语言截图
                game.ScreenshotMultiLanguage();
            } else if (arg0 == Input.Keys.valueOf("-")) {
                // 多语言截图
                game.switchLanguage(false);
            } else if (arg0 == Input.Keys.valueOf("=")) {
                // 多语言截图
                game.switchLanguage(true);
            } else if (arg0 == Input.Keys.E) {
                // 编辑UI
                game.var3dListener.edit(this);
            } else if (arg0 == Input.Keys.P) {
                // 保存UI
                game.var3dListener.saveUI(this);
            }
        } else if (arg0 == Input.Keys.BACK) {
            if (getRoot().getTouchable() == Touchable.enabled) {
                back();
            }
        }
        return false;
    }

    public boolean keyUp(int arg0) {
        if (isOff == true) return false;
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            game.var3dListener.keyUp(arg0);
        }
        return false;
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
