package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorNumber extends Actor {
	private TextureRegion[] texs;
	private float fontWidth, fontHeight;
	private int number = 1;
	private String num = "" + number;

	public ActorNumber(TextureRegion tex) {
		texs = tex.split((int) (tex.getRegionWidth() / 10f),
				tex.getRegionHeight())[0];
		fontWidth = texs[0].getRegionWidth();
		fontHeight = texs[0].getRegionHeight();
		setSize(fontWidth, fontHeight);
	}

	public ActorNumber(TextureRegion[] texs) {
		this.texs = texs;
		fontWidth = texs[0].getRegionWidth();
		fontHeight = texs[0].getRegionHeight();
		setSize(fontWidth, fontHeight);
	}

	public void setFontSize(float fontWidht, float fontHeight) {
		this.fontWidth = fontWidht;
		this.fontHeight = fontHeight;
		setSize(fontWidth * num.length(), fontHeight);
	}

	public void setNumber(int number) {
		this.number = number;
		num = "" + number;
		setSize(fontWidth * num.length(), fontHeight);
	}

	public int getNumber() {
		return number;
	}

	/**
	 * 数字累加
	 */
	public void cumulative(int number) {
		this.number += number;
		setNumber(this.number);
	}

	/**
	 * 累乘
	 */
	public void multiplicative(int number) {
		this.number *= number;
		setNumber(this.number);
	}

	public void draw(Batch batch, float alpha) {
		for (int i = 0; i < num.length(); i++) {
			// Color color = batch.getColor();
			batch.setColor(getColor());
			batch.draw(texs[Integer.parseInt(num.substring(i, i + 1))], getX()
					+ i * fontWidth, getY(), fontWidth, fontHeight);
			batch.setColor(Color.WHITE);
			// batch.draw(tex, getX(), getY(), getOriginX(), getOriginY(),
			// getWidth(), getHeight(), getScaleX(), getScaleY(),
			// getRotation());
		}
	}
}
