package var3d.net.center.actions;

/**
 * Created by fengyu on 2017/3/25.
 */

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;

/**
 * 抛物线Action
 *
 * @author fengyu
 */
public class ParabolaToAction extends TemporalAction {
    private float speedY, y, g = 1f;
    private float time, endX, endY;
    private float startX, startY, startSpeedY;
    private int alignment = Align.bottomLeft;

    protected void update(float percent) {
        valueAt(percent);
    }

    private void valueAt(float percent) {
        target.setPosition(startX + (endX - startX) * percent, y, alignment);
        speedY = startSpeedY - g * time * percent;
        y += speedY;
    }

    protected void begin() {
        y = target.getY(alignment);
        startX = target.getX(alignment);
        startY = target.getY(alignment);
        float t1 = time / 2 + (endY - startY) / (time * g);
        startSpeedY = g * t1;
    }

    protected void end() {
        target.setPosition(endX, endY, alignment);
    }

    public void setPosition(float x, float y) {
        endX = x;
        endY = y;
    }

    public void setPosition(float x, float y, int alignment) {
        endX = x;
        endY = y;
        this.alignment = alignment;
    }

    public void setDuration(float duration) {
        super.setDuration(duration);
        time = duration * 60;//假设为60帧
    }

    public float getX() {
        return endX;
    }

    public void setX(float x) {
        endX = x;
    }

    public float getY() {
        return endY;
    }

    public void setY(float y) {
        endY = y;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void reset() {
        super.reset();
        alignment = Align.bottomLeft;
    }

    public void setGravity(float g) {
        this.g = g / 10f;
    }
}

