package var3d.net.center;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
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
    public Rectangle safeAreaInsets;//安全区域边距

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
        if (isStretching) return;
        float bl = getWidth() / getHeight() * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        if (bl < 1) {
            cutWidth = (1 - bl) * getWidth() / 2f;
            cutHeight = 0;
            getRoot().setScale(bl, 1);
            getRoot().setPosition(cutWidth, 0);
            cutWidth = cutWidth / getRoot().getScaleX();

            //safeLeft = safeAreaInsets.getX() * bl;
        } else if (bl > 1) {
            cutWidth = 0;
            cutHeight = (1 - 1 / bl) * getHeight() / 2f;
            getRoot().setScale(1, 1 / bl);
            getRoot().setPosition(0, cutHeight);
            cutHeight = cutHeight / getRoot().getScaleY();

            // safeLeft = safeAreaInsets.getX() * bl;
        }
        safeLeft = safeAreaInsets.getX() * bl;
        cutAndWidth = getWidth() + cutWidth;
        cutAndHeight = getHeight() + cutHeight;
        fullWidth = cutAndWidth + cutWidth;
        fullHeight = cutAndHeight + cutHeight;
        Gdx.app.log("bbbbbb", "left=" + safeLeft);
    }

    public float getSafeLeft() {
        Gdx.app.log("aaaaaa", "left=" + safeLeft);
        return safeLeft;
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
        if (isOff == true)
            return false;
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            game.var3dListener.keyDown(arg0);
            if (arg0 == Input.Keys.DEL) {
                back();
            } else if (arg0 == Input.Keys.F) {
                // 截图
                game.Screenshot();
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
        if (isOff == true)
            return false;
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            game.var3dListener.keyUp(arg0);
        }
        return false;
    }

    public void draw() {
        if (bgList.size() != 0) {
            Batch batch = getBatch();
            batch.begin();
            batch.setColor(1, 1, 1, 1);
            for (Actor bg : bgList) {
                float prefx = bg.getX();
                float prefy = bg.getY();
                bg.setPosition(bg.getX() + getRoot().getX() - cutWidth,
                        bg.getY() + getRoot().getY() - cutHeight);
                bg.draw(batch, getRoot().getColor().a);
                bg.setPosition(prefx, prefy);
            }
            batch.end();
        }
        super.draw();
    }

    /**
     * 设置场景背景
     */
    public ArrayList<Actor> getBgList() {
        return bgList;
    }

    public Image setBackground(String imgName) {
        Image bg = game.getImage(imgName).getActor();
        bgList.add(bg);
        return bg;
    }

    public Image setBackground(String imgName, Color color) {
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT)
                .setColor(color).getActor();
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
