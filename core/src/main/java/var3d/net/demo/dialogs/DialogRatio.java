package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VDialog;
import var3d.net.center.VGame;

/**
 * Created by feng on 2018/3/23.
 * 等比例对话框
 */

public class DialogRatio extends VDialog {

    @Override
    public void init() {
        //设置对话框对齐方式
        setAlignment(Align.center);
        //设置对话框底层透明度
        setEndAlpha(0.5f);
        //设置显示对话框时的动画
        setShowActions(ActionType.FADE);
        //设置移除对话框时的动画
        setHideActions(ActionType.FADE);
        //设置背景
        setBackground(400, 300, Color.valueOf("0075ed"));
        //设置标题
        game.getLabel("等比例对话框").touchOff().setFontScale(1.3f).setPosition(getWidth() / 2, getHeight() - 30, Align.top).show();
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() / 2, 50, Align.bottom).show();
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //game.removeDialog(DialogRatio.this);
                //game.removeDialog();//新的移除当前dialog方法,可以不带this参数
                //game.removeTopDialog();
                game.removeAllDialog();
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

    @Override
    public void back() {
        game.removeDialog(this);
    }

}
