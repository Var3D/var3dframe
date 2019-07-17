package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.KeyboardType;
import var3d.net.center.NativeTextField;
import var3d.net.center.ReturnKeyType;
import var3d.net.center.VDialog;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VListenerOnKeyboardChange;
import var3d.net.center.VStage;
import var3d.net.center.VTextField;

/**
 * Created by feng on 2018/3/23.
 * 登录对话框
 */

public class DialogLoginGdx extends VDialog {
    private VTextField field_user,field_password;

    public DialogLoginGdx(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(400, 300, Color.valueOf("0075ed"));
        setShowActions(ActionType.POPUP);
        setHideActions(ActionType.POPUP);
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
                game.showMessege("登录失败！因为这只是个例子...\n帐号:"+field_user.getText()
                        +"\n密码:"+field_password.getText());
            }
        });

        //输入框
        field_user=game.getTextField("").setSize(200,40).setPosition(getWidth()/2,lab_title.getY()-60,Align.top).show();
        game.getLabel("帐号:").setPosition(pref().getX(),pref().getY(Align.center),Align.right).show();
        field_user.setMessageText("请输入帐号");
        field_user.setKeyboardType(KeyboardType.ASCIICapable);//设置键盘类型
        field_user.setReturnKeyType(ReturnKeyType.Done);//设置回车键文本为 next
        //回调暂时有点问题，需修改
//        field_user.setVTextFieldListener(new VTextField.VTextFieldListener() {
//            @Override
//            public void didBeginEditing(VTextField vTextField) {
//                Gdx.app.log("aaaaaa","点击激活输入框时调用");
//            }
//
//            @Override
//            public void didEndEditing(VTextField vTextField) {
//                Gdx.app.log("aaaaaa","按了回车键结束编辑调用");
//                field_user.resignFirstResponder();
//                field_password.becomeFirstResponder();
//            }
//
//            @Override
//            public String onEditingChanged(VTextField vTextField) {
//                Gdx.app.log("aaaaaa","文本框字符发生变化时调用");
//                return vTextField.getText();
//            }
//
//            @Override
//            public void keyboardWillShow(VTextField vTextField, boolean isShow, float keyboardHeight) {
//                Gdx.app.log("aaaaaa","虚拟键盘高度发生变化时调用");
//            }
//        });


        field_password=game.getTextField("").setSize(200,40).setPosition(getWidth()/2
                ,field_user.getY()-10,Align.top).show();
        game.getLabel("密码:").setPosition(pref().getX(),pref().getY(Align.center),Align.right).show();
        field_password.setMessageText("请输入密码");
        field_password.setPasswordMode(true);
        field_password.setKeyboardType(KeyboardType.ASCIICapable);

    }

    @Override
    public void reStart() {
    }


    public void show() {
        field_password.resignFirstResponder();
        field_user.becomeFirstResponder();
    }

    @Override
    public void start() {
        //仅第一次弹出对话框时调用
    }

    @Override
    public void pause() {
        //当该对话框被其他对话框覆盖时调用
        field_user.resignFirstResponder();
        field_password.resignFirstResponder();
    }

    @Override
    public void resume() {

    }

}
