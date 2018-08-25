package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.NativeTextField;
import var3d.net.center.SLabel;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by fengyu on 2018/8/23.
 */

public class StageActors extends VStage {
    public StageActors(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("常用控件").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();
        //返回
        Button btn_back = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_back.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageMain.class);
            }
        });

        //测试SLabel可缩放字体
        VLabel lab_test1 = game.getLabel("测试不可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW)
                .setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();
        lab_test1.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.WHITE, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));
        SLabel lab_test2 = game.getSLabel("测试可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW).setPosition(getRateX(0.5f), getRateY(0.4f), Align.center).show();
        lab_test2.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.CYAN, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));

        NativeTextField field=game.getUI(NativeTextField.class).setSize(400,50).setPosition(getWidth()/2
                ,getBottom(),Align.bottom).show();
        field.setMessageText("原生输入框NativeTextField");//设置消息文本
        field.setColor(Color.ORANGE);//设置输入框背景色
        field.setFontColor(Color.WHITE);//设置输入文本颜色
        //field.setFontSize(30);//设置字号
        field.setBorderStyle(NativeTextField.BorderStyle.Line);//设置边框类型
        field.setAlignment(Align.center);//设置文本对齐方式
        field.setTintColor(Color.YELLOW);//设置光标颜色
        field.setMessageColor(Color.RED);//设置消息文本颜色
        //field.setKeyboardType(NativeTextField.KeyboardType.NumberPad);//设置键盘类型
        //field.setPasswordMode(true);//设置为密码模式
        field.setReturnKeyType(NativeTextField.ReturnKeyType.Done);//设置键盘 Renter 键的类型
        field.setAdaptKeyboardType(NativeTextField.AdaptKeyboardType.Self);//适配键盘高度的类型
        field.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {
                //当输入框获得焦点时，执行该方法
                Gdx.app.log("aaaaaaa","获取焦点");
            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {
                //当结束编辑时执行该方法
                //game.showMessege("你输入了这些字："+nativeTextField.getText());
                Gdx.app.log("aaaaaaa","失去焦点");
            }

            @Override
            public boolean shouldReturn(NativeTextField nativeTextField) {
                // 当点击键盘的返回键（右下角）时，执行该方法,desktop版本为回车键。
                game.showMessege("你输入了这些字："+nativeTextField.getText());
                return true;
            }

            @Override
            public String onEditingChanged(NativeTextField nativeTextField) {
                return null;
            }

            @Override
            public void keyboardWillShow(NativeTextField nativeTextField, float keyboardHeight) {
                Gdx.app.log("aaaaaaa","keyboardHeight="+keyboardHeight);
            }
        });
    }

    @Override
    public void reStart() {

    }

    @Override
    public void back() {

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
