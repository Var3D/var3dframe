package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.NativeTextField;
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
        //设置标题
        VLabel lab_title= game.getLabel("登录对话框").touchOff().setFontScale(1.3f).setPosition
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

        //原生输入框(目前仅在 ios 端有效)
        ntf_user=game.getUI(NativeTextField.class).setSize(200,40).setPosition(
                getWidth()/2,lab_title.getY()-10,Align.top).show();
        game.getLabel("帐号:").setPosition(0,pref().getHeight()/2f,Align.right).show(ntf_user);
        ntf_user.setMessageText("请输入帐号");
        ntf_user.setBorderStyle(NativeTextField.BorderStyle.RoundedRect);
        ntf_user.setKeyboardType(NativeTextField.KeyboardType.Alphabet);
        ntf_user.setReturnKeyType(NativeTextField.ReturnKeyType.Next);
        ntf_user.becomeFirstResponder();
        ntf_user.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {
                ntf_password.resignFirstResponder();
                ntf_user.becomeFirstResponder();
            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {

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
                Gdx.app.log("aaaaaaa","keyboardHeight="+keyboardHeight);
            }
        });
        ntf_password=game.getUI(NativeTextField.class).setSize(200,40).setPosition(getWidth()/2
                ,ntf_user.getY()-10,Align.top).show();
        game.getLabel("密码:").setPosition(0,pref().getHeight()/2f,Align.right).show(ntf_password);
        ntf_password.setMessageText("请输入密码");
        ntf_password.setPasswordMode(true);
        ntf_password.setBorderStyle(NativeTextField.BorderStyle.RoundedRect);
        ntf_password.setReturnKeyType(NativeTextField.ReturnKeyType.Go);
        ntf_password.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {

            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {
                  Gdx.app.log("aaaaaaa","password.end");

            }

            @Override
            public boolean shouldReturn(NativeTextField nativeTextField) {
                game.showMessege("登录失败！因为这只是个例子...\n帐号:"+ntf_user.getText()
                        +"\n密码:"+ntf_password.getText());
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

            }
        });
    }

    @Override
    public void reStart() {
        //除第一次外每次弹出对话框时调用
        ntf_user.becomeFirstResponder();
    }

    @Override
    public void show() {
        //每次弹出对话框时调用
        setStartActions(ActionType.POPUP);
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
