package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

/**
 * Created by fengyu on 2018/8/19.
 * 原生输入框
 */

public class NativeTextField extends Group {
    private VGame game;
    private String text,messageText;
    //public Image bg;
    private boolean isHidden,isPasswordMode;
    private Color fontColor,tintColor,messageColor;
    private float fontSize;
    private int align = Align.left;
    private KeyboardType keyboardType=KeyboardType.Default;
    private BorderStyle borderStyle=BorderStyle.RoundedRect;
    private ReturnKeyType returnKeyType=ReturnKeyType.Default;
    private TextFieldListener textFieldListener;
    private AdaptKeyboardType adaptKeyboardType=AdaptKeyboardType.Self;

    public AdaptKeyboardType getAdaptKeyboardType() {
        return adaptKeyboardType;
    }

    //设置适配键盘高度的类型
    public void setAdaptKeyboardType(AdaptKeyboardType adaptKeyboardType) {
        this.adaptKeyboardType = adaptKeyboardType;
    }


    public interface TextFieldListener{

        // 当输入框获得焦点时，执行该方法 （光标出现时）。
        void didBeginEditing(NativeTextField nativeTextField);

        //当结束编辑时执行该方法(收起键盘键)
        void didEndEditing(NativeTextField nativeTextField);

        // 当点击键盘的返回键（右下角）时，执行该方法。返回值为 true 时关闭键盘
        boolean shouldReturn(NativeTextField nativeTextField);

        //当输入框的文本发生变化时调用，可用此方法实现字符长度限制，返回值将被重新设置给输入框
        String onEditingChanged(NativeTextField nativeTextField);

        //当键盘出现或改变时调用(此刻会返回键盘高度,可用于动态调整输入框或整个界面的位置，以免键盘挡住了输入框)
        void keyboardWillShow(NativeTextField nativeTextField,float keyboardHeight);
    }

    public enum Method{
        newObject,setText,setVisible,positionChanged,sizeChanged,remove,setHidden,setPasswordMode
        ,setMessageText,setFontColor,setFontSize,setAlignment,setKeyboardType,setMessageColor
        ,setTintColor,setBorderStyle,setBackgroundColor,setReturnKeyType,setTextFieldListener
        ,becomeFirstResponder,resignFirstResponder
    }

    //键盘高度的适应类型
    public enum AdaptKeyboardType{
        Self,//只适配自身
        None//不适配
    }

    //键盘类型
    public enum KeyboardType{
        Default(0L),//默认键盘，支持所有字符
        ASCIICapable(1L),//支持ASCII的默认键盘
        NumbersAndPunctuation(2L),//标准电话键盘，支持＋＊＃字符
        URL(3L),//URL键盘，支持.com按钮 只支持URL字符
        NumberPad(4L),//数字键盘
        PhonePad(5L),//电话键盘
        NamePhonePad(6L),//电话键盘，也支持输入人名
        EmailAddress(7L),//用于输入电子 邮件地址的键盘
        DecimalPad(8L),//数字键盘 有数字和小数点
        Twitter(9L),//优化的键盘，方便输入@、#字符
        WebSearch(10L),//方便浏览器使用的键盘
        ASCIICapableNumberPad(11L),//看起来像数字键盘
        Alphabet(1L);//字母键盘，等于ASCIICapable

        private final long n;

        KeyboardType(long n) {
            this.n = n;
        }

        public long value() {
            return this.n;
        }
    }

    //边框类型
    public enum BorderStyle{
        None(0L),
        Line(1L),
        Bezel(2L),
        RoundedRect(3L);

        private final long n;

        BorderStyle(long n) {
            this.n = n;
        }

        public long value() {
            return this.n;
        }
    }

    //键盘 Reterun 键类型
    public enum ReturnKeyType{
        Default(0L),
        Go(1L),
        Google(2L),
        Join(3L),
        Next(4L),
        Route(5L),
        Search(6L),
        Send(7L),
        Yahoo(8L),
        Done(9L),
        EmergencyCall(10L),
        Continue(11L);

        private final long n;

        ReturnKeyType(long n) {
            this.n = n;
        }

        public long value() {
            return this.n;
        }
    }

    public NativeTextField(VGame game){
        this.game=game;
        game.var3dListener.linkNativeTextField(this,Method.newObject);
        //bg=game.getImage(100,50).show(this);
        setSize(100,50);
        setTransform(false);
        //setDebug(true);
    }

    public void setFontColor(Color color){
        this.fontColor=color;
        game.var3dListener.linkNativeTextField(this,Method.setFontColor);
    }

    public Color getFontColor(){
        return fontColor;
    }

    public void setFontSize(float fontSize){
        this.fontSize=fontSize;
        game.var3dListener.linkNativeTextField(this,Method.setFontSize);
    }

    public float getFontSize(){
        return fontSize;
    }

    public void setAlignment (int align) {
        this.align=align;
        game.var3dListener.linkNativeTextField(this,Method.setAlignment);
    }

    public int getAlignment () {
        return align;
    }


    //设置键盘类型()
    public void setKeyboardType(KeyboardType keyboardType){
        this.keyboardType=keyboardType;
        game.var3dListener.linkNativeTextField(this,Method.setKeyboardType);
    }

    public KeyboardType getKeyboardType(){
        return keyboardType;
    }

    public void setColor(Color color){
        super.setColor(color);
        //bg.setColor(color);
        game.var3dListener.linkNativeTextField(this,Method.setBackgroundColor);
    }

    public void setColor(float r,float g,float b,float a){
        setColor(new Color(r,g,b,a));
    }

    public void setText(String text){
        this.text=text;
        game.var3dListener.linkNativeTextField(this,Method.setText);
    }

    public void setOnlyText(String text){
        this.text=text;
    }

    public void setVisible (boolean visible) {
        super.setVisible(visible);
        if(isHidden)return;
        game.var3dListener.linkNativeTextField(this,Method.setVisible);
    }

    protected void positionChanged () {
        super.positionChanged();
        game.var3dListener.linkNativeTextField(this,Method.positionChanged);
    }

    public void synchronousPosition(){
        Gdx.app.postRunnable(new Runnable() {
            public void run() {
                game.var3dListener.linkNativeTextField(NativeTextField.this,Method.positionChanged);
            }
        });
    }

    protected void sizeChanged () {
        super.sizeChanged();
        //bg.setSize(getWidth(),getHeight());
        game.var3dListener.linkNativeTextField(this,Method.sizeChanged);
    }

    public String getText(){
        return text;
    }

    public boolean remove(){
        game.var3dListener.linkNativeTextField(this,Method.remove);
        return super.remove();
    }

    //隐藏或显示原生输入框(当界面被隐藏时，应当主动隐藏原生的输入框)
    public void setHidden(boolean isHidden){
        this.isHidden=isHidden;
        if(isHidden){
            game.var3dListener.linkNativeTextField(this,Method.setHidden);
        }else{
            if(isVisible())game.var3dListener.linkNativeTextField(this,Method.setHidden);
        }
    }

    public boolean isHidden(){
        return isHidden;
    }

    //设置为密码输入框
    public void setPasswordMode(boolean isPasswordMode){
        this.isPasswordMode=isPasswordMode;
        game.var3dListener.linkNativeTextField(this,Method.setPasswordMode);
    }

    public boolean isPasswordMode(){
        return isPasswordMode;
    }

    //设置提示消息
    public void setMessageText(String messageText) {
        this.messageText=messageText;
        game.var3dListener.linkNativeTextField(this,Method.setMessageText);
    }

    public String getMessageText(){
        return messageText;
    }

    public void setMessageColor(Color messageColor){
        this.messageColor=messageColor;
        game.var3dListener.linkNativeTextField(this,Method.setMessageColor);
    }

    public Color getMessageColor(){
        return messageColor;
    }

    //设置光标颜色
    public void setTintColor(Color tintColor){
        this.tintColor=tintColor;
        game.var3dListener.linkNativeTextField(this,Method.setTintColor);
    }

    public Color getTintColor(){
        return tintColor;
    }

    //设置边框样式
    public void setBorderStyle(BorderStyle borderStyle){
        this.borderStyle=borderStyle;
        game.var3dListener.linkNativeTextField(this,Method.setBorderStyle);
    }

    public BorderStyle getBorderStyle(){
        return borderStyle;
    }

    //设置键盘 Reterun 键
    public void setReturnKeyType(ReturnKeyType returnKeyType){
        this.returnKeyType=returnKeyType;
        game.var3dListener.linkNativeTextField(this,Method.setReturnKeyType);
    }

    public ReturnKeyType getReturnKeyType(){
        return returnKeyType;
    }

    public void setTextFieldListener(TextFieldListener textFieldListener){
        this.textFieldListener=textFieldListener;
        game.var3dListener.linkNativeTextField(this,Method.setTextFieldListener);
    }

    public TextFieldListener getTextFieldListener(){
        return textFieldListener;
    }

    //用于保存本地的监听回调，便于移出该控件同时移出回调
    private Array<Object> nativeAllListener=new Array<>();
    public void addNativeListener(Object listener){
        nativeAllListener.add(listener);
    }

    public Array<Object> getAllNativeListener(){
        return nativeAllListener;
    }


    //设置焦点
    public void becomeFirstResponder(){
        game.var3dListener.linkNativeTextField(this,Method.becomeFirstResponder);

    }

    //取消焦点
    public void resignFirstResponder(){
        game.var3dListener.linkNativeTextField(this,Method.resignFirstResponder);
    }


    public void setStage(Stage stage){
        super.setStage(stage);
        game.var3dListener.linkNativeTextField(this,Method.sizeChanged);
        game.var3dListener.linkNativeTextField(this,Method.positionChanged);
        game.var3dListener.linkNativeTextField(this,Method.setFontSize);
        if(isVisible())game.var3dListener.linkNativeTextField(this,Method.setHidden);
    }
}
