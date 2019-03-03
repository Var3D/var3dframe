package var3d.net.demo.dialogs;

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

public class DialogUnRatio extends VDialog {
    public DialogUnRatio(VGame game) {
        super(game, true);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(400, 300, Color.valueOf("0075ed"));
        //设置标题
        game.getLabel("非等比例对话框").touchOff().setFontScale(1.3f).setPosition(getWidth() / 2, getHeight() - 30, Align.top).show();
        //对话框堆叠测试
        Button btn_dialog = game.getButton().setColor(Color.ORANGE).setSize(120, 40)
                .setPosition(getWidth() / 2, getHeight() / 2, Align.center).addClicAction().show();
        btn_dialog.add(game.getLabel("对话框堆叠测试").setFontScale(0.5f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(DialogRatio.class);
            }
        });
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() / 2, 50, Align.bottom).show();
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.removeDialog();
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
