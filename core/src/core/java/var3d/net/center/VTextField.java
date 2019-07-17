package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import var3d.net.center.freefont.FreeBitmapFont;

import static var3d.net.center.VGame.game;

public class VTextField extends TextField {
	static private final char BACKSPACE = 8;
	static private final char TAB = '\t';
	VTextField field;
	private VTextFieldListener listener;
	private KeyboardType keyboardType=KeyboardType.Default;
	private ReturnKeyType returnKeyType=ReturnKeyType.Default;
	private AdaptKeyboardType adaptKeyboardType= AdaptKeyboardType.Default;

	//键盘高度的适应类型
	public enum AdaptKeyboardType{
		Default,//当输入框被键盘遮住时让自身所在的整个stage跟着键盘高度移动
		Sticky,//不论是否被键盘遮住，总是将输入框靠近键盘上边缘
		None//不移动
	}


	public interface VTextFieldListener{
		// 当输入框获得焦点时，执行该方法 （光标出现时）。
		void didBeginEditing(VTextField vTextField);

		//当结束编辑时执行该方法(收起键盘键)
		void didEndEditing(VTextField vTextField);

		// 当点击键盘的返回键（右下角）时，执行该方法。返回值为 true 时关闭键盘
		boolean shouldReturn(NativeTextField nativeTextField);

		//当输入框的文本发生变化时调用，可用此方法实现字符长度限制，返回值将被重新设置给输入框
		String onEditingChanged(VTextField vTextField);

		//当键盘出现或改变时调用(此刻会返回键盘高度,可用于动态调整输入框或整个界面的位置，以免键盘挡住了输入框)
		void keyboardWillShow(VTextField vTextField,boolean isShow,float keyboardHeight);
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
				Gdx.app.log("aaaaaa","character="+character);

				if(isDisabled()||character<32||character == TAB) return false;

				Stage stage = getStage();
				if (stage == null || stage.getKeyboardFocus() != VTextField.this)return false;

				if(character==ENTER_ANDROID||character==ENTER_DESKTOP){
					if(listener!=null){
						listener.didEndEditing(VTextField.this);
					}
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
		getListeners().insert(0,new ClickListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Gdx.app.log("aaaaaa","点击了输入框");
				if(listener!=null)listener.didBeginEditing(VTextField.this);
				//Gdx.input.setOnscreenKeyboardVisible(false);
				becomeFirstResponder();
				return true;
			}
		});//保证内置的这个监听早于父类内置的监听先执行

		setOnscreenKeyboard(new OnscreenKeyboard() {
			public void show(boolean visible) {
				//将父类此接口重置，可实现在显示键盘之前对本地输入框控件键盘进行属性设置
				game.var3dListener.linkVTextField(VTextField.this);
				Gdx.input.setOnscreenKeyboardVisible(visible);
			}
		});
	}

	public void updateDisplay(){
		super.setPasswordMode(field.isPasswordMode());
	}

	public void setPasswordCharacter(String passwordCharacter) {
		append(passwordCharacter, getStyle());
		super.setPasswordCharacter(passwordCharacter.charAt(0));
		super.setPasswordMode(true);
	}

	public void setPasswordMode(boolean isPasswordMode){
		setPasswordCharacter("*");
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
		final VStage stage= (VStage) getStage();
		if(stage!=null){
			stage.setKeyboardFocus(VTextField.this);
			//Gdx.input.setOnscreenKeyboardVisible(true);
			game.var3dListener.setListenerOnKeyboardChange(stage,new VListenerOnKeyboardChange() {
				public void onKeyboardChange(boolean visible, float keyboardHeight) {
					VStage stage= (VStage) getStage();
					if(stage==null)return;
					if (visible==false) {
						switch (adaptKeyboardType){
							case Default:
								stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), stage.getStartY(), 0.2f));
								break;
							case Sticky:
								stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), stage.getStartY(), 0.2f));
								break;
							case None:
								break;
						}
						game.var3dListener.removeListenerOnKeyboardChange();
						stage.setKeyboardFocus(null);
						if(listener!=null){
							listener.keyboardWillShow(VTextField.this,false,0);
							listener.didEndEditing(VTextField.this);
							//VTextField.this.next(UIUtils.shift());
						}
					}else {
						Actor focus=stage.getKeyboardFocus();
						if(focus instanceof VTextField) {
							float h = keyboardHeight - getStageY(focus, stage.getCutHeight());
							switch (((VTextField)focus).getAdaptKeyboardType()){
								case Default:
									if(h>0)stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), h, 0.2f));
									break;
								case Sticky:
									stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), h, 0.2f));
									break;
								case None:
									break;
							}
							if (listener != null)
								listener.keyboardWillShow(VTextField.this, true, keyboardHeight);
						}
					}

				}
			});
		}

	}

	private float getStageY(Actor actor,float y){
		if(!actor.hasParent())return actor.getY();
		if(actor.getStage().getRoot()==actor.getParent())return actor.getY()+y;
		return getStageY(actor.getParent(),y+actor.getY());
	}

	//移出焦点
	public void resignFirstResponder(){
		if(getStage()!=null)getStage().setKeyboardFocus(null);
		game.var3dListener.removeListenerOnKeyboardChange();
		Gdx.input.setOnscreenKeyboardVisible(false);
	}

	public AdaptKeyboardType getAdaptKeyboardType() {
		return adaptKeyboardType;
	}

	//设置适配键盘高度的类型
	public void setAdaptKeyboardType(AdaptKeyboardType adaptKeyboardType) {
		this.adaptKeyboardType = adaptKeyboardType;
	}

	//设置键盘类型
	public void setKeyboardType(KeyboardType keyboardType){
		this.keyboardType=keyboardType;
	}

	public KeyboardType getKeyboardType(){
		return keyboardType;
	}

	//设置键盘返回键上的文本
	public void setReturnKeyType(ReturnKeyType returnKeyType){
		this.returnKeyType=returnKeyType;
	}

	public ReturnKeyType getReturnKeyType(){
		return returnKeyType;
	}
}
