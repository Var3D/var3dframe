package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.KeyboardType;
import var3d.net.center.NativeTextField;
import var3d.net.center.ReturnKeyType;
import var3d.net.center.VDialog;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;

/**
 * Created by feng on 2018/3/23.
 * 登录对话框
 */

public class DialogLogin extends VDialog {
    private NativeTextField ntf_user,ntf_password;

    public DialogLogin(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(400, 300, Color.valueOf("0075ed"));
        //建议有原生输入框的界面关闭弹出动画和移出动画
        //setShowActions(ActionType.POPUP);
        //setHideActions(ActionType.POPUP);
        //设置标题
        final VLabel lab_title= game.getLabel("登录对话框").touchOff().setFontScale(1.3f).setPosition
               (getWidth() / 2, getHeight() - 30, Align.top).show();
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() *0.25f, 30, Align.bottom).show();
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //game.removeDialog(DialogRatio.this);
                game.removeDialog();//新的移除当前dialog方法,可以不带this参数
            }
        });

        //登录
        Button btn_login = game.getTextButton("登录", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth()*0.75f, 30, Align.bottom).show();
        btn_login.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //game.removeDialog(DialogRatio.this)
                game.showMessege("登录失败！因为这只是个例子...\n帐号:"+ntf_user.getText()
                        +"\n密码:"+ntf_password.getText());
            }
        });

        //原生输入框
        ntf_user=game.getUI(NativeTextField.class).setSize(200,40).setPosition(
                getWidth()/2,lab_title.getY()-20,Align.top).show();
        game.getLabel("帐号:").setPosition(0,pref().getHeight()/2f,Align.right).show(ntf_user);
        ntf_user.setMessageText("请输入帐号");
        ntf_user.setKeyboardType(KeyboardType.Alphabet);
        ntf_user.setReturnKeyType(ReturnKeyType.Next);
        ntf_user.setAdaptKeyboardType(NativeTextField.AdaptKeyboardType.None);
        ntf_user.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {
            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {
                setY(game.getCenterY(),Align.center);
                ntf_user.synchronousPosition();
                ntf_password.synchronousPosition();
            }

            @Override
            public boolean shouldReturn(NativeTextField nativeTextField) {
                ntf_user.resignFirstResponder();
                ntf_password.becomeFirstResponder();
                return true;
            }

            @Override
            public String onEditingChanged(NativeTextField nativeTextField) {
                if(nativeTextField.getText().length()>10){//限制输入字符的长度为10
                    return nativeTextField.getText().substring(0,10);
                }
                return null;
            }

            @Override
            public void keyboardWillShow(NativeTextField nativeTextField, float keyboardHeight) {
                float h=keyboardHeight-ntf_user.getY()-getCutHeight();
                if(getY()<h){
                    setY(h);
                    ntf_user.synchronousPosition();
                    ntf_password.synchronousPosition();
                }
            }
        });
        ntf_password=game.getUI(NativeTextField.class).setSize(200,40).setPosition(getWidth()/2
                ,ntf_user.getY()-10,Align.top).show();
        game.getLabel("密码:").setPosition(0,pref().getHeight()/2f,Align.right).show(ntf_password);
        ntf_password.setMessageText("请输入密码");
        ntf_password.setPasswordMode(true);
        ntf_password.setReturnKeyType(ReturnKeyType.Go);
        ntf_password.setAdaptKeyboardType(NativeTextField.AdaptKeyboardType.None);
        ntf_password.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {

            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {
                setY(game.getCenterY(),Align.center);
                ntf_user.synchronousPosition();
                ntf_password.synchronousPosition();
            }

            @Override
            public boolean shouldReturn(NativeTextField nativeTextField) {
                game.showMessege("登录失败！因为这只是个例子...\n帐号:"+ntf_user.getText()
                        +"\n密码:"+ntf_password.getText());
                return true;
            }

            @Override
            public String onEditingChanged(NativeTextField nativeTextField) {
                if(nativeTextField.getText().length()>5){//限制输入字符的长度为10
                    return nativeTextField.getText().substring(0,5);
                }
                return null;
            }

            @Override
            public void keyboardWillShow(NativeTextField nativeTextField, float keyboardHeight) {
                float h=keyboardHeight-ntf_password.getY()-getCutHeight();
                if(getY()<h){
                    setY(h);
                    ntf_user.synchronousPosition();
                    ntf_password.synchronousPosition();
                }
            }
        });

    }

    @Override
    public void reStart() {
    }

    @Override
    public void show() {
        ntf_password.resignFirstResponder();
        ntf_user.becomeFirstResponder();
    }

    @Override
    public void start() {
        //仅第一次弹出对话框时调用
    }

    @Override
    public void pause() {
        //当该对话框被其他对话框覆盖时调用
    }

    @Override
    public void resume() {
        //当该对话框恢复顶层时调用
    }

}
