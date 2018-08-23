package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

import var3d.net.center.NativeTextField;
import var3d.net.center.SLabel;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.demo.ImageTest;
import var3d.net.demo.R;

/**
 * Created by feng on 2018/5/20.
 */

public class StageMain extends VStage {
    public StageMain(VGame game) {
        super(game,false);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("Var3D框架").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();
        //对话框
        Button btn_dialog = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40).setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_dialog.add(game.getLabel("对话框例子").setFontScale(0.6f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageDialogs.class);
            }
        });

        //舞台
        Button btn_stage = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_stage.add(game.getLabel("舞台例子").setFontScale(0.6f).getActor());
        btn_stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageStages.class);
            }
        });

        //舞台
        Button btn_card = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_card.add(game.getLabel("VCard示例").setFontScale(0.6f).getActor());
        btn_card.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageCards.class);
            }
        });

        //等待添加
        Button btn_more = game.getButton().setColor(Color.ORANGE).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_more.add(game.getLabel("等待添加...").setFontScale(0.6f).getActor());
        btn_more.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("更多效果尽请期待...");
            }
        });

        //测试自定义方法
        ImageTest test = game.getUI(new ImageTest(game.getDrawable(R.image.tank_4))).method("setSize", 100)
                .setWidth(self().getWidth() * 1.5f).setPosition(pref().getX(), 0).setColor(Color.YELLOW).show();

        //测试SLabel可缩放字体
        VLabel lab_test1 = game.getLabel("测试不可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW)
                .setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();
        lab_test1.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.WHITE, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));
        SLabel lab_test2 = game.getSLabel("测试可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW).setPosition(getRateX(0.5f), getRateY(0.4f), Align.center).show();
        lab_test2.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.CYAN, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));

        NativeTextField field=new NativeTextField(game);
        field.setBounds(getWidth()*0.25f,getHeight()*0.35f,400,50);
        addActor(field);
        //field.addAction(Actions.forever(Actions.sequence(Actions.moveBy(100,0,3),Actions.moveBy(-100,0,3))));
        field.setMessageText("动态原生输入框");//设置消息文本
        field.setColor(Color.ORANGE);//设置输入框背景色
        field.setFontColor(Color.WHITE);//设置输入文本颜色
        //field.setFontSize(30);//设置字号
        field.setBorderStyle(NativeTextField.BorderStyle.RoundedRect);//设置边框类型
        field.setAlignment(Align.center);//设置文本对齐方式
        field.setTintColor(Color.YELLOW);//设置光标颜色
        field.setMessageColor(Color.RED);//设置消息文本颜色
        //field.setKeyboardType(NativeTextField.KeyboardType.NumberPad);//设置键盘类型
        //field.setPasswordMode(true);//设置为密码模式
        field.setReturnKeyType(NativeTextField.ReturnKeyType.Done);//设置键盘 Renter 键的类型
        //field.setAdaptKeyboardType(NativeTextField.AdaptKeyboardType.Self);//适配键盘高度的类型
        field.setTextFieldListener(new NativeTextField.TextFieldListener() {
            @Override
            public void didBeginEditing(NativeTextField nativeTextField) {
                //当输入框获得焦点时，执行该方法
            }

            @Override
            public void didEndEditing(NativeTextField nativeTextField) {
                //当结束编辑时执行该方法
                //game.showMessege("你输入了这些字："+nativeTextField.getText());
            }

            @Override
            public boolean shouldReturn(NativeTextField nativeTextField) {
                // 当点击键盘的返回键（右下角）时，执行该方法。
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
