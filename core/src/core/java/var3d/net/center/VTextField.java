package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.reflect.Method;

import var3d.net.center.freefont.FreeBitmapFont;

public class VTextField extends TextField {
	static private final char BACKSPACE = 8;
	static private final char TAB = '\t';
	VTextField field;
	boolean passwordMode;

	public VTextField(String text, TextFieldStyle style) {
		super(append(text, style), style);
		setOnlyFontChars(false);
		field = this;
		ClickListener appendListener = new ClickListener() {
			public boolean keyTyped(InputEvent event, char character) {
				if (isDisabled()) return false;
				switch (character) {
				case BACKSPACE:
				case TAB:
				case ENTER_ANDROID:
				case ENTER_DESKTOP:
					break;
				default:
					if (character < 32) return false;
				}

				Stage stage = getStage();
				if (stage == null || stage.getKeyboardFocus() != VTextField.this)
					return false;
				if (character == TAB || character == ENTER_ANDROID) {
				} else {
					boolean enter = character == ENTER_DESKTOP || character == ENTER_ANDROID;
					boolean add = enter ? writeEnters : true;
					if (add) {
						append(getText(), getStyle());
						updateDisplay();
					}
				}
				return true;
			}
		};
		addListener(appendListener);
	}

	public void updateDisplay(){
		setPasswordMode(field.isPasswordMode());
	}

	public void setPasswordCharacter(String passwordCharacter) {
		append(passwordCharacter, getStyle());
		super.setPasswordCharacter(passwordCharacter.charAt(0));
		super.setPasswordMode(true);
	}

	public void setMessageText(String messageText) {
		super.setMessageText(append(messageText, getStyle()));
	}

	private static String append(String text, TextFieldStyle style) {
		if (text.equals("")) return "";
		String newText=((FreeBitmapFont) style.font).appendTextPro(text);
		return newText;
	}

	public void appendText(String str) {
		super.appendText(append(str, getStyle()));
	}

	public void setText(String str) {
		super.setText(append(str, getStyle()));
	}

}
