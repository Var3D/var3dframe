package var3d.net.center;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
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
    private float cutWidth, cutHeight;

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

    public VStage(VGame game, Batch batch) {
        super(new ScalingViewport(Scaling.stretch, game.WIDTH, game.HEIGHT), batch);
        set(game);
    }

    public VStage(VGame game, int width, int height) {
        super(new ScalingViewport(Scaling.stretch, width, height));
        set(game);
    }

    private void set(VGame game) {
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
        float bl = getWidth() / getHeight() * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        if (bl < 1) {
            cutWidth = (1 - bl) * getWidth() / 2f;
            cutHeight = 0;
            getRoot().setScale(bl, 1);
            getRoot().setPosition(cutWidth, 0);
        } else if (bl > 1) {
            cutWidth = 0;
            cutHeight = (1 - 1 / bl) * getHeight() / 2f;
            getRoot().setScale(1, 1 / bl);
            getRoot().setPosition(0, cutHeight);
        }
    }

    public float getCutWidth() {
        return cutWidth / getRoot().getScaleX();
    }

    public float getCutHeight() {
        return cutHeight / getRoot().getScaleY();
    }

    public float getCutAndWidth() {
        return getWidth() + cutWidth / getRoot().getScaleX();
    }

    public float getCutAndHeight() {
        return getHeight() + cutHeight / getRoot().getScaleY();
    }

    public float getFullWidth() {
        return getWidth() + cutWidth / getRoot().getScaleX() * 2;
    }

    public float getFullHeight() {
        return getHeight() + cutHeight / getRoot().getScaleY() * 2;
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

    public void setBackground(String imgName) {
        Image bg = game.getImage(imgName).getActor();
        bgList.add(bg);
    }

    public void setBackground(String imgName, Color color) {
        Image bg = game.getImage(imgName, game.WIDTH, game.HEIGHT)
                .setColor(color).getActor();
        bgList.add(bg);
    }

    public void setBackground(Actor bg) {
        bgList.add(bg);
    }

    public void setBackground(Color color) {
        Image bg = game.getImage(game.WIDTH, game.HEIGHT, color).getActor();
        bgList.add(bg);
    }

    public void setBackground(Color color1, Color color2) {
        Actor bg = new ActorGradient(game.WIDTH, game.HEIGHT, color1, color2);
        bgList.add(bg);
    }

    public Group createSuperRoot() {
        Group SRoot = new Group();
        SRoot.setSize(game.WIDTH + getCutWidth() * 2.5f, game.HEIGHT + getCutHeight() * 2.5f);
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
