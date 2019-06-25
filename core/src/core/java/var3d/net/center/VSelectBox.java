package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;

import var3d.net.center.freefont.FreeBitmapFont;

/**
 * 下拉框
 * @param <T>
 */

public class VSelectBox<T> extends SelectBox<T> {
	private static FreeBitmapFont font;

	public VSelectBox(SelectBoxStyle style) {
		super(reStyle(style));
	}

	private static SelectBoxStyle reStyle(SelectBoxStyle style) {
		font = (FreeBitmapFont) style.font;
		return style;
	}

	public void setItems(T... newItems) {
		super.setItems(newItems);
		for (T t : newItems) {
			font.appendText(t.toString());
		}
	}

	public void setItems(Array<T> newItems) {
		super.setItems(newItems);
		for (T t : newItems) {
			font.appendText(t.toString());
		}
	}
}
