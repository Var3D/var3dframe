package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.KeyboardType;
import var3d.net.center.ReturnKeyType;
import var3d.net.center.VDialog;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
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

        Button btn_close2 = game.getCloseButton().setPosition(getWidth()-2, getHeight()-2, Align.topRight).show();
        btn_close2.addListener(new ClickListener() {
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
               join();
            }
        });

        //输入框
        field_user=game.getTextField("").setSize(200,40).setPosition(getWidth()/2,lab_title.getY()-20,Align.top).show();
        game.getLabel("帐号:").setPosition(pref().getX(),pref().getY(Align.center),Align.right).show();
        field_user.setMessageText("请输入帐号");
        field_user.setKeyboardType(KeyboardType.ASCIICapable);//设置键盘类型
        //设置回车键文本为 下一项，设置为该类型或者 继续 类型，不用做回车键监听，当按下键盘回车键时，将自动将焦点移动到下一个输入框
        field_user.setReturnKeyType(ReturnKeyType.Next);


        field_password=game.getTextField("").setSize(200,40).setPosition(getWidth()/2
                ,field_user.getY()-10,Align.top).show();
        game.getLabel("密码:").setPosition(pref().getX(),pref().getY(Align.center),Align.right).show();
        field_password.setMessageText("请输入密码");
        field_password.setPasswordMode(true);
        field_password.setKeyboardType(KeyboardType.ASCIICapable);
        field_password.setReturnKeyType(ReturnKeyType.Join);//设置回车键文本为 加入
        field_password.setVTextFieldListener(new VTextField.VTextFieldListener() {
            public void didBeginEditing(VTextField vTextField) {
                // 当输入框获得焦点时，执行该方法 （光标出现时）。
            }

            public void didEndEditing(VTextField vTextField) {
                //当结束编辑时执行该方法(收起键盘键)
            }

            public String onEditingChanged(VTextField vTextField) {
                //当输入框的文本发生变化时调用，可用此方法实现字符长度限制，返回值将被重新设置给输入框
                return null;
            }

            public void keyboardWillShow(VTextField vTextField, boolean isShow, float keyboardHeight) {
                //当键盘出现或改变时调用(一般无需自己处理)
            }

            public boolean shouldReturn(VTextField vTextField) {
                //自定义回车键事件
                join();
                return true;//返回值为 true 将会在回车事件后隐藏键盘
            }
        });

    }

    private void join(){
        game.showMessege("登录失败！因为这只是个例子...\n帐号:"+field_user.getText()
                +"\n密码:"+field_password.getText());
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

    @Override
    public void back() {

    }

}
