package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import var3d.net.center.freefont.FreeBitmapFont;

public class VTextButton extends TextButton {

	public VTextButton(String text, TextButtonStyle style) {
		super(append(text, style), style);
	}

	private static String append(String text, TextButtonStyle style) {
		return ((FreeBitmapFont) style.font).appendTextPro(text);
	}

	public void setText(String text) {
		super.setText(append(text, getStyle()));
	}
}
