package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by feng on 2018/6/1.
 * 可缩放文字控件类
 */

public class SLabel extends Group {
    public VLabel label;

    public SLabel(CharSequence text, Label.LabelStyle style) {
        label = new VLabel(text, style);
        addActor(label);
        setSize(label.getPrefWidth(), label.getPrefHeight());
        setColor(label.getColor());
    }

    public void setText(CharSequence newText) {
        label.setText(newText);
    }

    public StringBuilder getText () {
        return label.getText();
    }

    public void setColor(Color color) {
        super.setColor(color);
        label.setColor(color);
    }

    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        label.setColor(r, g, b, a);
    }

    public float getShadowOffsetX() {
        return label.getShadowOffsetX();
    }

    public void setShadowOffsetX(float shadowOffsetX) {
        label.setShadowOffsetX(shadowOffsetX);
    }

    public float getShadowOffsetY() {
        return label.getShadowOffsetY();
    }

    public void setShadowOffsetY(float shadowOffsetY) {
        label.setShadowOffsetY(shadowOffsetY);
    }

    public VLabel.ShadowOption getShadowOption() {
        return label.getShadowOption();
    }

    public void setShadowOption(VLabel.ShadowOption shadowOption) {
        label.setShadowOption(shadowOption);
    }

    public Color getShadowColor() {
        return label.getShadowColor();
    }

    public void setShadowColor(Color shadowColor) {
        label.setShadowColor(shadowColor);
    }

    /**
     * 设置描边
     */
    public void setStroke(Color strokeColor) {
        label.setStroke(strokeColor);
    }

    /**
     * 设置描边
     */
    public void setStroke(Color strokeColor, float strokeWidth) {
        label.setStroke(strokeColor, strokeWidth);
    }



    public void setFontScale(float fontScale) {
        this.setFontScale(fontScale, fontScale);
    }

    public void setFontScale(float fontScaleX, float fontScaleY) {
        label.setFontScale(fontScaleX, fontScaleY);
        setSize(label.getPrefWidth(), label.getPrefHeight());
    }

    public void setAlignment(int algin) {
        label.setAlignment(algin);
    }

    public void act(float delat) {
        super.act(delat);
        if (getActions().size > 0) {
            label.setColor(getColor());

        }
    }
}
