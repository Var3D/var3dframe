package var3d.net.center;

import var3d.net.center.freefont.FreeBitmapFont;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class VLabel extends Label {
	private boolean isStroke = false;// 是否描边
	private Color strokeColor;
	private float strokeWidth;

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
		if (isStroke)
			strokeColor = color.cpy();
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
		this.strokeColor = strokeColor;
		this.strokeWidth = 1;
		isStroke = true;
	}

	/**
	 * 设置描边(参数0-2为宜)
	 */
	public void setStroke(Color strokeColor, float strokeWidth) {
		this.strokeColor = strokeColor;
		this.strokeWidth = strokeWidth;
		isStroke = true;
	}

	/**
	 * 设置字体缩放
	 */
	public void setFontScale(float fontScale) {
		super.setFontScale(fontScale);
	}

	private float dxs[] = { 1, 1, -1, -1, 1, -1, 0, 0 };
	private float dys[] = { 1, -1, 1, -1, 0, 0, 1, -1 };

	public void draw(Batch batch, float parentAlpha) {
		if (isStroke) {
			validate();
			for (int i = 0; i < dxs.length; i++) {
				getBitmapFontCache().tint(strokeColor);
				getBitmapFontCache().setPosition(getX() + dxs[i] * strokeWidth,
						getY() + dys[i] * strokeWidth + strokeWidth);
				getBitmapFontCache().draw(batch, getColor().a);
			}
			getBitmapFontCache().tint(getColor());
			getBitmapFontCache().setPosition(getX(), getY() + strokeWidth);
			getBitmapFontCache().draw(batch);
		} else {
			super.draw(batch, parentAlpha);
		}
	}
}
