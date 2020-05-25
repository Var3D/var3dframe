package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import var3d.net.center.freefont.FreeBitmapFont;

/**
 * 下拉框
 * @param <T>
 */

public class VSelectBox<T> extends SelectBox<T> {
	private static FreeBitmapFont font;
	private ScrollPane selectBoxList;

	public VSelectBox(SelectBoxStyle style) {
		super(reStyle(style));
		selectBoxList=getScrollPane();
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


	public void showList() {
		toFront();
		super.showList();
		selectBoxList.setPosition(getX(), getY(), Align.topLeft);
		getParent().addActor(selectBoxList);
	}
}
