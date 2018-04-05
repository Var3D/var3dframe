package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.shaderActor.DefaultShaders;

public class VLabel extends Label {
    private boolean isStroke = false;// 是否描边
    private Color strokeColor;
    private float strokeWidth;
    private float shadowOffsetX = 0f;//设置阴影位移x
    private float shadowOffsetY = 0f;//设置阴影位移x
    private ShadowOption shadowOption = ShadowOption.Disable;//设置阴影选项
    private Color shadowColor = new Color(Color.GRAY);//阴影颜色

    private ShaderProgram shaderProgramOutLine;

    public enum ShadowOption {
        Disable, Projection, Smear
    }

    public VLabel(CharSequence text, LabelStyle style) {
        super(append(text, style), style);
        setSize(getPrefWidth(), getPrefHeight());
        setColor(style.fontColor);
    }

    private static CharSequence append(CharSequence text, LabelStyle style) {
        return ((FreeBitmapFont) style.font).appendTextPro(text.toString());
    }

    public void setText(CharSequence newText) {
        super.setText(append(newText, getStyle()));
    }

    public void setColor(Color color) {
        super.setColor(color);
        if (isStroke) strokeColor = color.cpy();
    }

    /**
     * 设置加粗(参数0-1为宜)
     */
    public void setBold(float width) {
        setStroke(getColor().cpy(), width);
    }

    /**
     * 设置描边
     */
    public void setStroke(Color strokeColor) {
        setStroke(strokeColor, 1.5f);
    }

    /**
     * 设置描边
     */
    public void setStroke(Color strokeColor, float strokeWidth) {
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        isStroke = true;
        if (shaderProgramOutLine == null) {
            shaderProgramOutLine = new ShaderProgram(DefaultShaders.defaultVert, DefaultShaders.outlineFrag);
            if (shaderProgramOutLine.isCompiled() == false)
                throw new IllegalArgumentException("Error compiling shader: " + shaderProgramOutLine.getLog());

        }
    }

    /**
     * 设置字体缩放
     */

    public void setFontScale(float fontScale) {
        super.setFontScale(fontScale);
        setSize(getPrefWidth(), getPrefHeight());
    }


    public float getShadowOffsetX() {
        return shadowOffsetX;
    }

    public void setShadowOffsetX(float shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
    }

    public float getShadowOffsetY() {
        return shadowOffsetY;
    }

    public void setShadowOffsetY(float shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
    }

    public ShadowOption getShadowOption() {
        return shadowOption;
    }

    public void setShadowOption(ShadowOption shadowOption) {
        this.shadowOption = shadowOption;
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }


    private float dxs[] = {1, 1, -1, -1, 1, -1, 0, 0};
    private float dys[] = {1, -1, 1, -1, 0, 0, 1, -1};
    private float oldShadowAlpha, oldStrokeAlpha;

    public void draw(Batch batch, float parentAlpha) {
        switch (shadowOption) {
            case Disable: //关闭阴影特效
                drawLabel(batch, parentAlpha);
                break;
            case Projection: //投影效果
                oldShadowAlpha = shadowColor.a;
                shadowColor.a = getColor().a;
                getBitmapFontCache().tint(shadowColor);
                getBitmapFontCache().setPosition(getX() + shadowOffsetX, getY() + shadowOffsetY);
                getBitmapFontCache().draw(batch);
                shadowColor.a = oldShadowAlpha;
                drawLabel(batch, parentAlpha);
                break;
            case Smear: //拖影效果
                float k = shadowOffsetY / shadowOffsetX;
                float offsetX = shadowOffsetX, offsetY = shadowOffsetY;
                float off = 0;
                if (offsetX > 0) off = -Math.abs(k);
                else off = Math.abs(k);

                oldShadowAlpha = shadowColor.a;
                shadowColor.a = getColor().a;
                while (Math.abs(offsetX) > 0 && Math.abs(offsetY) > 0) {
                    offsetX += off;
                    offsetY = k * offsetX;
                    if (isStroke) {
                        validate();
                        for (int i = 0; i < dxs.length; i++) {
                            getBitmapFontCache().tint(shadowColor);
                            getBitmapFontCache().setPosition(getX() + offsetX + dxs[i] * strokeWidth,
                                    getY() + offsetY + dys[i] * strokeWidth + strokeWidth);
                            getBitmapFontCache().draw(batch);
                        }
                    } else {
                        getBitmapFontCache().tint(shadowColor);
                        getBitmapFontCache().setPosition(getX() + offsetX, getY() + offsetY);
                        getBitmapFontCache().draw(batch);
                    }
                }
                shadowColor.a = oldShadowAlpha;
                drawLabel(batch, parentAlpha);
                break;

        }
    }

    public void drawLabel(Batch batch, float parentAlpha) {
        if (isStroke) {
            validate();

            BitmapFontCache cache = getBitmapFontCache();
            FreeBitmapFont font = (FreeBitmapFont) cache.getFont();

            ShaderProgram shader = batch.getShader();
            batch.setShader(shaderProgramOutLine);

            shaderProgramOutLine.setUniformf("outlineColor", strokeColor.r, strokeColor.g, strokeColor.b);
            shaderProgramOutLine.setUniformf("outlineSize", strokeWidth);
            shaderProgramOutLine.setUniformf("limit", 0.2f);
            shaderProgramOutLine.setUniformf("textureSize", font.pageWidth, font.pageWidth);

            cache.setPosition(getX(), getY());
            cache.draw(batch);

            batch.setShader(shader);
            cache.tint(getColor());
            cache.draw(batch);
        } else {
            super.draw(batch, parentAlpha);
        }
    }
}
