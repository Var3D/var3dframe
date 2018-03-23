package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.ActorGradient;
import var3d.net.center.VDialog;
import var3d.net.center.VGame;

/**
 * Created by feng on 2018/3/23.
 */

public class DialogTest extends VDialog {
    public DialogTest(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(new ActorGradient(400, 300, Color.CYAN, Color.CORAL));
        //设置标题(对话框使用show方法的时候不能省略父对象参数)
        game.getLabel(R.strings.dialogTitle).touchOff().setPosition(getWidth() / 2, getHeight() - 20, Align.top)
                .setColor(Color.RED).setStroke(Color.YELLOW).show(this);
        //关闭按钮
        Button btn_close = game.getButton(R.image.pause_btn_bg).addClicAction().setPosition(getWidth() / 2, 50, Align.bottom).show(this);
        btn_close.add(game.getLabel("关闭").setColor(Color.RED).getActor());
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.removeDialog(DialogTest.this);
                //or game.removeTopDialog();
                //or game.removeAllDialog();
            }
        });
    }

    @Override
    public void reStart() {
        //除第一次外每次弹出对话框时调用
    }

    @Override
    public void show() {
        //每次弹出对话框时调用
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
