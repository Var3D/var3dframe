package var3d.net.center;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import var3d.net.center.freefont.FreeBitmapFont;

import static var3d.net.center.VGame.game;

public class VTextField extends TextField {
	static private final char TAB = '\t';
	public static  final char BACKSPACE = 8;//libgdx的删除键值
	static private final char DELETE = 127;
	public static  final char ENTER = 13;
	private char passwordCharacter = 149;
	boolean focusTraversal = true,onlyFontChars=true;
	String undoText = "";
	Clipboard clipboard;
	private float blinkTime = 0.32f;
	boolean cursorOn = true;
	long lastBlink;
	long lastChangeTime;
	float renderOffset;
	TextFieldListener listener;
	//private int textHAlign = Align.left;
	//private float selectionX, selectionWidth;

	//VTextField field;
	private VTextFieldListener vlistener;
	private KeyboardType keyboardType=KeyboardType.Default;
	private ReturnKeyType returnKeyType=ReturnKeyType.Default;
	private AdaptKeyboardType adaptKeyboardType= AdaptKeyboardType.Default;

	//键盘高度的适应类型
	public enum AdaptKeyboardType{
		Default,//当输入框被键盘遮住时让自身所在的整个stage跟着键盘高度移动
		Sticky,//不论是否被键盘遮住，总是将输入框靠近键盘上边缘
		None//不移动
	}


	public  interface VTextFieldListener{
		// 当输入框获得焦点时，执行该方法 （光标出现时）。
		void didBeginEditing(VTextField vTextField);

		//当结束编辑时执行该方法(收起键盘键)
		void didEndEditing(VTextField vTextField);

		//当输入框的文本发生变化时调用，可用此方法实现字符长度限制，返回值将被重新设置给输入框
		String onEditingChanged(VTextField vTextField);

		//当键盘出现或改变时调用(此刻会返回键盘高度,可用于动态调整输入框或整个界面的位置，以免键盘挡住了输入框)
		void keyboardWillShow(VTextField vTextField,boolean isShow,float keyboardHeight);

		// 当点击键盘的回车键时，执行该方法。返回值为 true 时关闭键盘
		boolean shouldReturn(VTextField vTextField);

	}


	public void setVTextFieldListener(VTextFieldListener listener) {
		this.vlistener =listener;
	}

	public VTextFieldListener getVTextFieldListener(){
		return vlistener;
	}


	public VTextField(String text, TextFieldStyle style) {
		super(append(text, style), style);
		setOnlyFontChars(false);
		//field = this;
		setOnscreenKeyboard(new OnscreenKeyboard() {
			public void show(boolean visible) {
				//将父类此接口重置，可实现在显示键盘之前对本地输入框控件键盘进行属性设置
				game.var3dListener.linkVTextField(VTextField.this);
				//Gdx.input.setOnscreenKeyboardVisible(visible);
				game.var3dListener.setOnscreenKeyboardVisible(visible);
			}
		});
		ClickListener appendListener = new ClickListener() {

			public boolean keyTyped(InputEvent event, char character) {

				//if(isDisabled()||character<32||character == TAB) return false;
				if(isDisabled()||character == TAB) return false;

				Stage stage1 = getStage();
				if (!(stage1 instanceof VStage)||stage1 == null || stage1.getKeyboardFocus() != VTextField.this)return false;
				final VStage stage= (VStage) stage1;
				if(character==ENTER_ANDROID||character==ENTER_DESKTOP||character==ENTER){
					if(returnKeyType==ReturnKeyType.Next||returnKeyType==ReturnKeyType.Continue){
						VTextField.this.next(false);
						if(stage.getKeyboardFocus() instanceof VTextField){
							final VTextField next=(VTextField)stage.getKeyboardFocus();
							getOnscreenKeyboard().show(false);
							game.delayRun(0.2f, new Runnable() {
								public void run() {
									next.becomeFirstResponder();
									next.getOnscreenKeyboard().show(true);
								}
							});
						}
					}else if(vlistener !=null){
						boolean isHideKeybord= vlistener.shouldReturn(VTextField.this);
						if(isHideKeybord){
							getOnscreenKeyboard().show(false);
							stage.getRoot().getActions();
							stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), stage.getStartY(), 0.2f));
						}
					} else {
						getOnscreenKeyboard().show(false);
						stage.getRoot().getActions();
						stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), stage.getStartY(), 0.2f));
					}
				} else {
					boolean enter = character == ENTER_DESKTOP || character == ENTER_ANDROID;
					boolean add = enter ? writeEnters : true;
					if (add) {
//						if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
//							append(VTextField.super.text, getStyle());
//							updateDisplay();
//						}
						//updateDisplayText();
						if(vlistener !=null){
							String newText= vlistener.onEditingChanged(VTextField.this);
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
				if(vlistener !=null) vlistener.didBeginEditing(VTextField.this);
				getOnscreenKeyboard().show(false);
				becomeFirstResponder();
				return true;
			}
		});//保证内置的这个监听早于父类内置的监听先执行
	}

	//设置输入框文本颜色
	public void setFontColor(Color color){
		getStyle().fontColor.set(color.r,color.g,color.b,color.a);
	}


	public void updateDisplay(){
		super.setPasswordMode(super.isPasswordMode());
	}

	public void setPasswordCharacter(String passwordCharacter) {
		append(passwordCharacter, getStyle());
		this.passwordCharacter=passwordCharacter.charAt(0);
		super.setPasswordCharacter(this.passwordCharacter);
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
		return ((FreeBitmapFont) style.font).appendTextPro(text);
	}

	public void appendText(String str) {
		super.appendText(append(str, getStyle()));
	}

	public void setText(String str) {
		super.setText(append(str, getStyle()));
	}

	/**
	 * 只有用这个方法才能返回真正的原始文本(还原后的 emoji)
	 * @return
     */
	public String getText(){
		return ((FreeBitmapFont)getStyle().font).emojiKeyToEmoji(super.text);
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
						stage.getRoot().getActions();
						stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), stage.getStartY(), 0.2f));
						game.var3dListener.removeListenerOnKeyboardChange();
						stage.setKeyboardFocus(null);
						if(vlistener !=null){
							vlistener.keyboardWillShow(VTextField.this,false,0);
							vlistener.didEndEditing(VTextField.this);
						}
					}else {
						Actor focus=stage.getKeyboardFocus();
						if(focus instanceof VTextField) {
							float h = keyboardHeight - getStageY(focus, stage.getCutHeight());
							switch (((VTextField)focus).getAdaptKeyboardType()){
								case Default:
									if(h>0){
										stage.getRoot().getActions();
										stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), h, 0.2f));
									}
									break;
								case Sticky:
									stage.getRoot().getActions();
									stage.getRoot().addAction(Actions.moveTo(stage.getStartX(), h, 0.2f));
									break;
								case None:
									break;
							}
							if (vlistener != null)
								vlistener.keyboardWillShow(VTextField.this, true, keyboardHeight);
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
		getOnscreenKeyboard().show(false);
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

	private int visibleTextStart, visibleTextEnd;

	protected void drawText (Batch batch, BitmapFont font, float x, float y) {
//		FreeBitmapFont freefont= (FreeBitmapFont) getStyle().font;
//		if(freefont.isEmoji()){
//			if(buffer==null)buffer=new StringBuffer();
//			buffer.setLength(0);
//			Gdx.app.log("aaaa","start="+start);
//
//			for(int i=0;i<str.length();i++){
//				char c=str.charAt(i);
//				if(freefont.isCreatedEmoji4WithKey(""+c)||freefont.isCreatedEmoji2WithKey(""+c)){
//					buffer.append("[白]");
//					buffer.append(c);
//					end+=3;
//				}else buffer.append(c);
//			}
//			str=buffer.toString();
//		}

		//font.draw(batch, displayText, x + textOffset, y, 0, text.length(), 0, Align.left, false);
		super.drawText( batch, font,x,y);
	}


	private StringBuilder passwordBuffer;

	void updateDisplayText () {
		FreeBitmapFont font = (FreeBitmapFont) getStyle().font;
		BitmapFont.BitmapFontData data = font.getData();

		String text = this.text;
		int textLength = text.length();
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < textLength; i++) {
			char c = text.charAt(i);
			buffer.append(data.hasGlyph(c) ? c : ' ');
		}
		String newDisplayText = buffer.toString();

		if (isPasswordMode()) {
			if (passwordBuffer == null) passwordBuffer = new StringBuilder(newDisplayText.length());
			if (passwordBuffer.length() > textLength)
				passwordBuffer.setLength(textLength);
			else {
				for (int i = passwordBuffer.length(); i < textLength; i++)
					passwordBuffer.append(passwordCharacter);
			}
			displayText = passwordBuffer;
		} else displayText = newDisplayText;

		layout.setText(font, displayText);
		glyphPositions.clear();
		float x = 0;
		if (layout.runs.size > 0) {
			GlyphLayout.GlyphRun run = layout.runs.first();
			FloatArray xAdvances = run.xAdvances;
			fontOffset = xAdvances.first();
			for (int i = 1, n = xAdvances.size; i < n; i++) {
				glyphPositions.add(x);
				x += xAdvances.get(i);
			}
		} else fontOffset = 0;
		glyphPositions.add(x);

		if (selectionStart > newDisplayText.length()) selectionStart = textLength;
	}

	public void setFocusTraversal (boolean focusTraversal) {
		this.focusTraversal = focusTraversal;
		super.setFocusTraversal(focusTraversal);
	}

	public void setOnlyFontChars (boolean onlyFontChars) {
		this.onlyFontChars = onlyFontChars;
		super.setOnlyFontChars(onlyFontChars);
	}

	protected InputListener createInputListener () {
		return new FreeTextFieldClickListener();
	}

	public class FreeTextFieldClickListener extends TextField.TextFieldClickListener {
		public boolean keyDown (InputEvent event, int keycode) {
			if (isDisabled()) return false;

			lastBlink = 0;
			cursorOn = false;

			Stage stage = getStage();
			if (stage == null || stage.getKeyboardFocus() != VTextField.this) return false;

			boolean repeat = false;
			boolean ctrl = UIUtils.ctrl();
			boolean jump = ctrl && !isPasswordMode();
			boolean handled = true;

			if (ctrl) {
				switch (keycode) {
					case Input.Keys.V:
						paste(clipboard.getContents(), true);
						repeat = true;
						break;
					case Input.Keys.C:
					case Input.Keys.INSERT:
						copy();
						return true;
					case Input.Keys.X:
						cut(true);
						return true;
					case Input.Keys.A:
						selectAll();
						return true;
					case Input.Keys.Z:
						String oldText = text;
						setText(undoText);
						undoText = oldText;
						updateDisplayText();
						return true;
					default:
						handled = false;
				}
			}

			if (UIUtils.shift()) {
				switch (keycode) {
					case Input.Keys.INSERT:
						paste(clipboard.getContents(), true);
						break;
					case Input.Keys.FORWARD_DEL:
						cut(true);
						break;
				}

				selection:
				{
					int temp = cursor;
					keys:
					{
						switch (keycode) {
							case Input.Keys.LEFT:
								moveCursor(false, jump);
								repeat = true;
								handled = true;
								break keys;
							case Input.Keys.RIGHT:
								moveCursor(true, jump);
								repeat = true;
								handled = true;
								break keys;
							case Input.Keys.HOME:
								goHome(jump);
								handled = true;
								break keys;
							case Input.Keys.END:
								goEnd(jump);
								handled = true;
								break keys;
						}
						break selection;
					}
					if (!hasSelection) {
						selectionStart = temp;
						hasSelection = true;
					}
				}
			} else {
				// Cursor movement or other keys (kills selection).
				switch (keycode) {
					case Input.Keys.LEFT:
						moveCursor(false, jump);
						clearSelection();
						repeat = true;
						handled = true;
						break;
					case Input.Keys.RIGHT:
						moveCursor(true, jump);
						clearSelection();
						repeat = true;
						handled = true;
						break;
					case Input.Keys.HOME:
						goHome(jump);
						clearSelection();
						handled = true;
						break;
					case Input.Keys.END:
						goEnd(jump);
						clearSelection();
						handled = true;
						break;
				}
			}

			cursor = MathUtils.clamp(cursor, 0, text.length());

			if (repeat) scheduleKeyRepeatTask(keycode);
			return handled;
		}


		public boolean keyTyped (InputEvent event, char character) {
			//super.keyTyped(event,character);
			if (isDisabled()) return false;

			// Disallow "typing" most ASCII control characters, which would show up as a space when onlyFontChars is true.
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
			if (stage == null || stage.getKeyboardFocus() != VTextField.this) return false;

			if (UIUtils.isMac && Gdx.input.isKeyPressed(Input.Keys.SYM)) return true;

			if ((character == TAB || character == ENTER_ANDROID) && focusTraversal) {
				next(UIUtils.shift());
			} else {
				boolean delete = character == DELETE;
				boolean backspace = character == BACKSPACE;
				boolean enter = character == ENTER_DESKTOP || character == ENTER_ANDROID;

				boolean add = enter ? writeEnters : (!onlyFontChars|| getStyle().font.getData().hasGlyph(character));
				boolean remove = backspace || delete;
				if (add || remove) {
					String oldText = text;
					int oldCursor = cursor;
					if (hasSelection)
						cursor = delete(false);
					else {
						if (backspace && cursor > 0) {
							text = text.substring(0, cursor - 1) + text.substring(cursor--);
							renderOffset = 0;
						}
						if (delete && cursor < text.length()) {
							text = text.substring(0, cursor) + text.substring(cursor + 1);
						}
					}
					if (add && !remove) {
						// Character may be added to the text.
						if (!enter && getTextFieldFilter() != null && !getTextFieldFilter().acceptChar(VTextField.this, character)) return true;
						if (!withinMaxLength(text.length())) return true;
						String insertion = enter ? "\n" : String.valueOf(character);
						text = insert(cursor++, insertion, text);
						if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
							append(text, getStyle());
						}
					}
					String tempUndoText = undoText;
					if (changeText(oldText, text)) {
						long time = System.currentTimeMillis();
						if (time - 750 > lastChangeTime) undoText = oldText;
						lastChangeTime = time;
					} else cursor = oldCursor;
					updateDisplayText();
				}
			}
			if (listener != null) listener.keyTyped(VTextField.this, character);
			return true;
		}
	}

	String insert (int position, CharSequence text, String to) {
		if (to.length() == 0) return text.toString();
		return to.substring(0, position) + text + to.substring(position, to.length());
	}

	int delete (boolean fireChangeEvent) {
		int from = selectionStart;
		int to = cursor;
		int minIndex = Math.min(from, to);
		int maxIndex = Math.max(from, to);
		String newText = (minIndex > 0 ? text.substring(0, minIndex) : "")
				+ (maxIndex < text.length() ? text.substring(maxIndex, text.length()) : "");
		if (fireChangeEvent)
			changeText(text, newText);
		else
			text = newText;
		clearSelection();
		return minIndex;
	}

	boolean changeText (String oldText, String newText) {
		if (newText.equals(oldText)) return false;
		text = newText;
		ChangeListener.ChangeEvent changeEvent = Pools.obtain(ChangeListener.ChangeEvent.class);
		boolean cancelled = fire(changeEvent);
		text = cancelled ? oldText : newText;
		Pools.free(changeEvent);
		return !cancelled;
	}

	boolean withinMaxLength (int size) {
		return getMaxLength() <= 0 || size <getMaxLength();
	}

	void paste (String content, boolean fireChangeEvent) {
		if (content == null) return;
		StringBuilder buffer = new StringBuilder();
		int textLength = text.length();
		if (hasSelection) textLength -= Math.abs(cursor - selectionStart);
		BitmapFont.BitmapFontData data = getStyle().font.getData();
		for (int i = 0, n = content.length(); i < n; i++) {
			if (!withinMaxLength(textLength + buffer.length())) break;
			char c = content.charAt(i);
			if (!(writeEnters && (c == ENTER_ANDROID || c == ENTER_DESKTOP))) {
				if (c == '\r' || c == '\n') continue;
				if (onlyFontChars && !data.hasGlyph(c)) continue;
				if (getTextFieldFilter() != null && !getTextFieldFilter().acceptChar(this, c)) continue;
			}
			buffer.append(c);
		}
		content = buffer.toString();

		if (hasSelection) cursor = delete(fireChangeEvent);
		if (fireChangeEvent)
			changeText(text, insert(cursor, content, text));
		else
			text = insert(cursor, content, text);
		updateDisplayText();
		cursor += content.length();
	}

	void cut (boolean fireChangeEvent) {
		if (hasSelection && !isPasswordMode()) {
			copy();
			cursor = delete(fireChangeEvent);
			updateDisplayText();
		}
	}

	public void setClipboard (Clipboard clipboard) {
		super.setClipboard(clipboard);
		this.clipboard = clipboard;
	}

	private void blink () {
		if (!Gdx.graphics.isContinuousRendering()) {
			cursorOn = true;
			return;
		}
		long time = TimeUtils.nanoTime();
		if ((time - lastBlink) / 1000000000.0f > blinkTime) {
			cursorOn = !cursorOn;
			lastBlink = time;
		}
	}

	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		Stage stage = getStage();
		boolean focused = stage != null && stage.getKeyboardFocus() == this;
		if (focused && !isDisabled()) {
			blink();
		}
	}


	public void setBlinkTime (float blinkTime) {
		this.blinkTime = blinkTime;
		super.setBlinkTime(blinkTime);
	}

	public void setTextFieldListener (TextFieldListener listener) {
		super.setTextFieldListener(listener);
		this.listener = listener;
	}

	protected void setCursorPosition (float x, float y) {
		lastBlink = 0;
		cursorOn = false;
		cursor = letterUnderCursor(x);
	}

//	protected void calculateOffsets () {
//		super.calculateOffsets();
//		float visibleWidth = getWidth();
//		Drawable background = getStyle().background;
//		if (background != null) visibleWidth -= background.getLeftWidth() + background.getRightWidth();
//
//		int glyphCount = glyphPositions.size;
//		float[] glyphPositions = this.glyphPositions.items;
//
//		// Check if the cursor has gone out the left or right side of the visible area and adjust renderOffset.
//		float distance = glyphPositions[Math.max(0, cursor - 1)] + renderOffset;
//		if (distance <= 0)
//			renderOffset -= distance;
//		else {
//			int index = Math.min(glyphCount - 1, cursor + 1);
//			float minX = glyphPositions[index] - visibleWidth;
//			if (-renderOffset < minX) renderOffset = -minX;
//		}
//
//		// Prevent renderOffset from starting too close to the end, eg after text was deleted.
//		float maxOffset = 0;
//		float width = glyphPositions[glyphCount - 1];
//		for (int i = glyphCount - 2; i >= 0; i--) {
//			float x = glyphPositions[i];
//			if (width - x > visibleWidth) break;
//			maxOffset = x;
//		}
//		if (-renderOffset > maxOffset) renderOffset = -maxOffset;
//
//		// calculate first visible char based on render offset
//		visibleTextStart = 0;
//		float startX = 0;
//		for (int i = 0; i < glyphCount; i++) {
//			if (glyphPositions[i] >= -renderOffset) {
//				visibleTextStart = Math.max(0, i);
//				startX = glyphPositions[i];
//				break;
//			}
//		}
//
//		// calculate last visible char based on visible width and render offset
//		int length = Math.min(displayText.length(), glyphPositions.length - 1);
//		visibleTextEnd = Math.min(length, cursor + 1);
//		for (; visibleTextEnd <= length; visibleTextEnd++)
//			if (glyphPositions[visibleTextEnd] > startX + visibleWidth) break;
//		visibleTextEnd = Math.max(0, visibleTextEnd - 1);
//
//
//
//		if ((textHAlign & Align.left) == 0) {
//			textOffset = visibleWidth - (glyphPositions[visibleTextEnd] - startX);
//			if ((textHAlign & Align.center) != 0) textOffset = Math.round(textOffset * 0.5f);
//		} else
//			textOffset = startX + renderOffset;
//
//		// calculate selection x position and width
//		if (hasSelection) {
//			int minIndex = Math.min(cursor, selectionStart);
//			int maxIndex = Math.max(cursor, selectionStart);
//			float minX = Math.max(glyphPositions[minIndex] - glyphPositions[visibleTextStart], -textOffset);
//			float maxX = Math.min(glyphPositions[maxIndex] - glyphPositions[visibleTextStart], visibleWidth - textOffset);
//			selectionX = minX;
//			selectionWidth = maxX - minX -getStyle().font.getData().cursorX;
//		}
//	}

//	public void setAlignment (int alignment) {
//		super.setAlignment(alignment);
//		this.textHAlign = alignment;
//	}
}
