package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class UI<T extends Actor> {
    private T t;
    private VGame game;

    public UI(VGame game) {
        this.game = game;
    }

    public UI<T> setActor(T t) {
        this.t = t;
        return this;
    }

    public T getActor() {
        game.var3dListener.getLineNumber(t);
        return t;
    }

    public T show() {
        game.getStage().addActor(t);
        game.var3dListener.getLineNumber(t);
        return t;
    }

    public T show(Group group) {
        group.addActor(t);
        game.var3dListener.getLineNumber(t);
        return t;
    }

    public T show(Stage stage) {
        stage.addActor(t);
        game.var3dListener.getLineNumber(t);
        return t;
    }

    public UI<T> addClicAction() {
        addClicAction(Color.DARK_GRAY);
        return this;
    }

    /**
     * 添加点击变色效果
     */
    public UI<T> addClicAction(final Color color) {
        final Color pref = t.getColor().cpy();
        t.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float px, float py,
                                     int pointer, int but) {
                t.setColor(color);
                return true;
            }

            public void touchUp(InputEvent event, float px, float py,
                                int pointer, int but) {
                t.setColor(pref);
            }
        });
        return this;
    }

    public UI<T> setX(float x) {
        t.setX(x);
        return this;
    }

    public UI<T> setY(float y) {
        t.setY(y);
        return this;
    }

    public UI<T> setPosition(float x, float y) {
        t.setPosition(x, y);
        return this;
    }

    public UI<T> setPosition(float x, float y, int align) {
        t.setPosition(x, y, align);
        return this;
    }

    public UI<T> setWidth(float width) {
        t.setX(width);
        return this;
    }

    public UI<T> setHeight(float height) {
        t.setHeight(height);
        return this;
    }

    public UI<T> setSize(float width, float height) {
        t.setSize(width, height);
        return this;
    }

    public UI<T> setBounds(float x, float y, float width, float height) {
        t.setBounds(x, y, width, height);
        return this;
    }

    public UI<T> setOriginX(float originX) {
        t.setOriginX(originX);
        return this;
    }

    public UI<T> setOriginY(float originY) {
        t.setOriginY(originY);
        return this;
    }

    public UI<T> setOrigin(float originX, float originY) {
        t.setOrigin(originX, originY);
        return this;
    }

    public UI<T> setOrigin(int align) {
        t.setOrigin(align);
        return this;
    }

    public UI<T> setScaleX(float scaleX) {
        t.setScaleX(scaleX);
        return this;
    }

    public UI<T> setScaleY(float scaleY) {
        t.setScaleY(scaleY);
        return this;
    }

    public UI<T> setScale(float scale) {
        t.setScale(scale);
        return this;
    }

    public UI<T> setScale(float scaleX, float scaleY) {
        t.setScale(scaleX, scaleY);
        return this;
    }

    public UI<T> setColor(Color color) {
        t.setColor(color);
        return this;
    }

    public UI<T> setColor(float r, float g, float b, float a) {
        t.setColor(r, g, b, a);
        return this;
    }

    public UI<T> setName(String name) {
        t.setName(name);
        return this;
    }

    public UI<T> setUserObject(Object obj) {
        t.setUserObject(obj);
        return this;
    }

    public UI<T> setVisible(boolean isVisible) {
        t.setVisible(isVisible);
        return this;
    }

    public UI<T> setTouchable(Touchable touchable) {
        t.setTouchable(touchable);
        return this;
    }

    public UI<T> touchOff() {
        t.setTouchable(Touchable.disabled);
        return this;
    }

    public UI<T> setAlpha(float alpha) {
        Color color = t.getColor();
        t.setColor(color.r, color.g, color.b, alpha);
        return this;
    }

    public UI<T> addListener(EventListener listener) {
        t.addListener(listener);
        return this;
    }

    public UI<T> addCaptureListener(EventListener listener) {
        t.addCaptureListener(listener);
        return this;
    }

    public UI<T> addAction(Action action) {
        t.addAction(action);
        return this;
    }

    public UI<T> copyBounds(Actor actor) {
        t.setBounds(actor.getX(), actor.getY(), actor.getWidth(),
                actor.getHeight());
        return this;
    }
}
