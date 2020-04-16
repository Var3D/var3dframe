package var3d.net.center.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import java.util.HashMap;

/**
 * Created by fengyu on 2017/3/27.
 * 加强版Actions
 */

public class Vctions extends Actions {

    static public ParabolaToAction parabolaTo(float x, float y, float duration) {
        return parabolaTo(x, y, 10, duration, null);
    }

    static public ParabolaToAction parabolaTo(float x, float y, float gravity, float duration) {
        return parabolaTo(x, y, gravity, duration, null);
    }

    static public ParabolaToAction parabolaTo(float x, float y, float gravity, float duration, Interpolation interpolation) {
        ParabolaToAction action = action(ParabolaToAction.class);
        action.setPosition(x, y);
        action.setDuration(duration);
        action.setGravity(gravity);
        action.setInterpolation(interpolation);
        return action;
    }

    static public ParabolaToAction parabolaToAligned(float x, float y, int alignment, float duration) {
        return parabolaToAligned(x, y, alignment, 10, duration, null);
    }

    static public ParabolaToAction parabolaToAligned(float x, float y, int alignment, float gravity, float duration) {
        return parabolaToAligned(x, y, alignment, gravity, duration, null);
    }

    static public ParabolaToAction parabolaToAligned(float x, float y, int alignment, float gravity, float duration
            , Interpolation interpolation) {
        ParabolaToAction action = action(ParabolaToAction.class);
        action.setPosition(x, y, alignment);
        action.setDuration(duration);
        action.setGravity(gravity);
        action.setInterpolation(interpolation);
        return action;
    }

    static HashMap<String, Action> actionHashMap = new HashMap<>();

    static public RepeatAction forever(String key, Action repeatedAction) {
        if(actionHashMap.containsKey(key))throw new IllegalArgumentException("This key is already occupied, please change to a different key");
        RepeatAction action = Actions.forever(repeatedAction);
        actionHashMap.put(key, action);
        return action;
    }

    static public void removeActions(String key) {
        Action action = actionHashMap.get(key);
        Actor actor = action.getActor();
        if (actor != null) {
            actor.removeAction(action);
            actionHashMap.remove(action);
        }
    }
}

