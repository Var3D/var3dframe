package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.lang.reflect.Method;
import java.util.HashMap;

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
        game.var3dListener.getLineNumber(t);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String className = new Exception().getStackTrace()[1].getClassName();
        try {
            Class<?> clazz = loader.loadClass(className);
            if (VStage.class.isAssignableFrom(clazz)) {//判断clazz是否为VStage的实例或子类实例
                show(game.getStage());
            } else {
                HashMap<Class<?>, VDialog> pools = game.getDialogs();
                VDialog dialog = pools.get(clazz);
                show(dialog);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }

    public T show(Stage stage) {
        game.var3dListener.getLineNumber(t);
        stage.addActor(t);
        return t;
    }

    public T show(int aglin) {
        game.var3dListener.getLineNumber(t);
        show(game.getStage(), .5f, .5f, aglin);
        return t;
    }

    public T show(Stage stage, int aglin) {
        game.var3dListener.getLineNumber(t);
        show(stage, .5f, .5f, aglin);
        return t;
    }

    //设置actor在stage中的比例坐标
    public T show(float sx, float sy) {
        game.var3dListener.getLineNumber(t);
        show(game.getStage(), sx, sy);
        return t;
    }

    //设置actor在stage中的比例坐标
    public T show(float sx, float sy, int aglin) {
        game.var3dListener.getLineNumber(t);
        show(game.getStage(), sx, sy, aglin);
        return t;
    }

    //设置actor在stage中的比例坐标
    public T show(Stage stage, float sx, float sy) {
        game.var3dListener.getLineNumber(t);
        show(stage, sx, sy, Align.bottomLeft);
        return t;
    }

    //设置actor在stage中的比例坐标
    public T show(Stage stage, float sx, float sy, int aglin) {
        game.var3dListener.getLineNumber(t);
        stage.addActor(t);
        if (stage instanceof VStage) {
            VStage vStage = (VStage) stage;
            t.setPosition(vStage.getFullWidth() * sx - vStage.getCutWidth(), vStage.getFullHeight() * sy
                    - vStage.getCutHeight(), aglin);
        } else {
            t.setPosition(stage.getWidth() * sx, stage.getHeight() * sy, aglin);
        }
        return t;
    }

    public T show(Group group) {
        group.addActor(t);
        return t;
    }

    public T show(Group group, int align) {
        show(group, .5f, .5f, align);
        return t;
    }

    public T show(Group group, float sx, float sy) {
        show(group, sx, sy, Align.bottomLeft);
        return t;
    }

    public T show(Group group, float sx, float sy, int aglin) {
        group.addActor(t);
        t.setPosition(group.getWidth() * sx, group.getHeight() * sy, aglin);
        return t;
    }

    public UI<T> addClicAction() {
        addClicAction(Color.DARK_GRAY);
        return this;
    }

    public UI<T> addClicAction(final Color color) {
        t.addListener(new InputListener() {
            private Color pref;

            public boolean touchDown(InputEvent event, float px, float py,
                                     int pointer, int but) {
                if (pref == null) pref = t.getColor().cpy();
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

    public UI<T> setX(Actor actor) {
        t.setX(actor.getX());
        return this;
    }

    public UI<T> setY(float y) {
        t.setY(y);
        return this;
    }

    public UI<T> setY(Actor actor) {
        t.setY(actor.getY());
        return this;
    }

    public UI<T> setPosition(float x, float y) {
        t.setPosition(x, y);
        return this;
    }

    public UI<T> setPosition(Actor actor) {
        t.setPosition(actor.getX(), actor.getY());
        return this;
    }

    public UI<T> setPosition(float x, float y, int align) {
        t.setPosition(x, y, align);
        return this;
    }

    public UI<T> setPosition(Actor actor, int align) {
        t.setPosition(actor.getX(), actor.getY(), align);
        return this;
    }

    public UI<T> setWidth(float width) {
        t.setWidth(width);
        return this;
    }

    public UI<T> setWidth(Actor actor) {
        t.setWidth(actor.getWidth());
        return this;
    }

    public UI<T> setHeight(float height) {
        t.setHeight(height);
        return this;
    }

    public UI<T> setHeight(Actor actor) {
        t.setHeight(actor.getHeight());
        return this;
    }

    public UI<T> setSize(float width, float height) {
        t.setSize(width, height);
        return this;
    }

    public UI<T> setSize(Actor actor) {
        t.setSize(actor.getWidth(), actor.getHeight());
        return this;
    }

    public UI<T> setRate(float rateX,float rateY){
        t.setSize(t.getWidth()*rateX,t.getHeight()*rateY);
        return this;
    }

    public UI<T> setRate(float rate){
        t.setSize(t.getWidth()*rate,t.getHeight()*rate);
        return this;
    }

    public UI<T> setBounds(float x, float y, float width, float height) {
        t.setBounds(x, y, width, height);
        return this;
    }

    public UI<T> setBounds(Actor actor) {
        t.setBounds(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
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

    public UI<T> setRotation(float degrees){
        t.setRotation(degrees);
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

    //设置圆角遮罩
    public UI<T> setFillet(float radius) {
        //当然还没成功
        return this;
    }

    private HashMap<String, Method> hashMethod;

    //执行自定义控件的自定义方法(如果报错,请改用一个特殊的没有使用过的方法名)
    public UI<T> method(String methodName, Object... parameters) {
        Class clazz = t.getClass();
        try {
            if (hashMethod == null) {
                hashMethod = new HashMap<>();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (hashMethod.get(method.getName() + "#" + method.getParameterTypes().length) != null)
                        throw new UnsupportedOperationException("method方法不支持调用<方法名相同且方法参数个数相等>的方法");
                    hashMethod.put(method.getName() + "#" + method.getParameterTypes().length, method);
                }
            }
            Method method = hashMethod.get(methodName + "#" + parameters.length);
            method.invoke(t, parameters);
        } catch (Exception e) {
            throw new UnsupportedOperationException(clazz + "未找到" + methodName + "方法,或给定的参数错误");
        }
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

    public UI<T> setStroke(Color strokeColor) {
        setStroke(strokeColor,1);
        return this;
    }

    public UI<T> setStroke(Color strokeColor, float strokeWidth) {
        if (t instanceof VLabel) {
            ((VLabel) t).setStroke(strokeColor, strokeWidth);
        } else if (t instanceof SLabel) {
            ((SLabel) t).setStroke(strokeColor, strokeWidth);
        } else
            Gdx.app.error("Var3D框架消息", "setStroke(Color strokeColor, float strokeWidth)方法仅在类型VLabel/SLabel上有效");
        return this;
    }

    public UI<T> setShadow(Color shadowColor){
        setShadow(shadowColor,1,-1);
        return this;
    }

    public UI<T> setShadow(Color shadowColor,float offx,float offy){
        if (t instanceof VLabel) {
            VLabel l= (VLabel) t;
            l.setShadowOption(VLabel.ShadowOption.Projection);
            l.setShadowColor(shadowColor);
            l.setShadowOffsetX(offx);
            l.setShadowOffsetY(offy);
        } else if (t instanceof SLabel) {
            SLabel l= (SLabel) t;
            l.setShadowOption(VLabel.ShadowOption.Projection);
            l.setShadowColor(shadowColor);
            l.setShadowOffsetX(offx);
            l.setShadowOffsetY(offy);
        } else
            Gdx.app.error("Var3D框架消息", "setShadow(Color shadowColor,float offx,float offy)方法仅在类型VLabel/SLabel上有效");
        return this;
    }

    public UI<T> setFontScale(float scale) {
        setFontScale(scale, scale);
        return this;
    }

    public UI<T> setFontScale(float scaleX, float scaleY) {
        if (t instanceof VLabel) {
            ((VLabel) t).setFontScale(scaleX, scaleY);
        } else if (t instanceof SLabel) {
            ((SLabel) t).setFontScale(scaleX, scaleY);
        } else if (t instanceof Label) {
            ((Label) t).setFontScale(scaleX, scaleY);
        } else Gdx.app.error("Var3D框架消息", "setFontScale(float scale)方法仅在类型VLabel/SLabel/Label上有效");
        return this;
    }

    public UI<T> setAlignment(int alignment) {
        if (t instanceof VLabel) {
            ((VLabel) t).setAlignment(alignment);
        } else if (t instanceof SLabel) {
            ((SLabel) t).setAlignment(alignment);
        } else if (t instanceof Label) {
            ((Label) t).setAlignment(alignment);
        }else if (t instanceof TextField) {
            ((TextField) t).setAlignment(alignment);
        } else Gdx.app.error("Var3D框架消息", "setAlignment(int alignment)方法仅在类型VLabel/SLabel/Label上有效");
        return this;
    }
}
