package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.reflect.Method;

import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.demo.Game;

public class VTextField extends TextField {
	static private final char BACKSPACE = 8;
	static private final char TAB = '\t';
	VTextField field;
	private VTextFieldListener listener;

	public interface VTextFieldListener{
		// 当输入框获得焦点时，执行该方法 （光标出现时）。
		void didBeginEditing(VTextField vTextField);

		//当结束编辑时执行该方法(收起键盘键)
		void didEndEditing(VTextField vTextField);

		// 当点击键盘的返回键（右下角）时，执行该方法。返回值为 true 时关闭键盘
		boolean shouldReturn(VTextField vTextField);

		//当输入框的文本发生变化时调用，可用此方法实现字符长度限制，返回值将被重新设置给输入框
		String onEditingChanged(VTextField vTextField);

		//当键盘出现或改变时调用(此刻会返回键盘高度,可用于动态调整输入框或整个界面的位置，以免键盘挡住了输入框)
		void keyboardWillShow(VTextField vTextField,float keyboardHeight);
	}

	public void setVTextFieldListener (VTextFieldListener listener) {
		this.listener = listener;
	}

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
						if(listener!=null){
							String newText=listener.onEditingChanged(VTextField.this);
							if(newText!=null){
								setText(newText);
								setCursorPosition(newText.length());
							}
						}
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

	//成为焦点
	public void becomeFirstResponder(){
		if(getStage()!=null){
			getStage().setKeyboardFocus(this);
			Game.game.var3dListener.setListenerOnKeyboardChange(new VListenerOnKeyboardChange() {
				public void onKeyboardChange(boolean visible, float keyboardHeight) {
					if(listener!=null){
						listener.keyboardWillShow(VTextField.this,keyboardHeight);
					}
				}
			});
		}
	}

	//移出焦点
	public void resignFirstResponder(){
		if(getStage()!=null)getStage().setKeyboardFocus(null);
		Game.game.var3dListener.removeListenerOnKeyboardChange();
	}


}
