package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.KeyboardType;
import var3d.net.center.ReturnKeyType;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.center.VTextField;

/**
 * Created by fengyu on 2018/8/23.
 */

public class StageTextFeild extends VStage {

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("输入框").setPosition(getWidth() / 2
                , getTop() - 10, Align.top).touchOff().show();
        //返回
        Button btn_back = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_back.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(new StageMain());
            }
        });


        VTextField field=game.getTextField("").setSize(400,50).setPosition(getWidth()/2
                ,getBottom(),Align.bottom).show();
        field.setColor(Color.ORANGE);//设置输入框背景色
        field.setMessageText("输入数字");//设置消息文本
        field.setAlignment(Align.center);//设置文本对齐方式
        field.setKeyboardType(KeyboardType.NumberPad);//设置为只能输入数字
        field.setReturnKeyType(ReturnKeyType.Continue);

        VTextField field4=game.getTextField("").setSize(400,50).setPosition(getWidth()/2
                ,pref().getTop()+10,Align.bottom).show();
        field4.setColor(Color.WHITE);//设置输入框背景色
        field4.setMessageText("输入网址");//设置消息文本
        field4.setAlignment(Align.left);//设置文本对齐方式
        field4.setKeyboardType(KeyboardType.URL);//网址
        field4.setReturnKeyType(ReturnKeyType.Continue);

        VTextField field2=game.getTextField("",game.getTextFieldStyle(game.getFont(),Color.CYAN,Color.YELLOW,Color.BLACK))
                .setSize(400,50).setPosition(getWidth()/2,getTop(),Align.top).show();
        //field2.setColor(Color.BLACK);//设置输入框背景色
        field2.setFontColor(Color.WHITE);
        field2.setMessageText("输入任意字符");//设置消息文本
        field2.setAlignment(Align.left);//设置文本对齐方式
        field2.setKeyboardType(KeyboardType.Default);//设置为可输入任意字符
        field2.setAdaptKeyboardType(VTextField.AdaptKeyboardType.None);//设置为不适配键盘移动
        field2.setReturnKeyType(ReturnKeyType.Continue);


        VTextField field3=game.getTextField("").setSize(400,50).setPosition(getWidth()/2
                ,pref().getY()-10,Align.top).show();
        field3.setColor(Color.WHITE);//设置输入框背景色
        field3.setMessageText("电子邮件");//设置消息文本
        field3.setAlignment(Align.left);//设置文本对齐方式
        field3.setKeyboardType(KeyboardType.EmailAddress);//电子邮件键盘
        field3.setAdaptKeyboardType(VTextField.AdaptKeyboardType.Sticky);//设置为沾粘到键盘
        field3.setReturnKeyType(ReturnKeyType.Continue);
    }

    public void start() {

    }

    @Override
    public void reStart() {

    }

    @Override
    public void show() {

    }

    @Override
    public void back() {
        game.setStage(new StageMain());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void changing(float width, float height) {

    }
}
