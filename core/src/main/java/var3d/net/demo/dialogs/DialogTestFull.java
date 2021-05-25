package var3d.net.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VDialog;
import var3d.net.center.VGame;

/**
 * Created by feng on 2018/3/23.
 */

public class DialogTestFull extends VDialog {

    @Override
    public void init() {
        //设置背景
        setBackground(Color.valueOf("0075ed"));
        setShowActions(ActionType.POPUP);
        setHideActions(ActionType.POPUP);
        //设置标题
        game.getLabel("全屏对话框").touchOff().setFontScale(1.3f).setPosition(getWidth() / 2, getHeight() - 30, Align.top).show();
        //左右区域测试
        Image left = game.getImage(50, 50).setWidth(self().getWidth() * 2f).setColor(Color.RED).setPosition(getLeft(), getHeight() / 2, Align.left).show();
        Image right = game.getImage(50, 50).setColor(Color.RED).setPosition(getRight(), getHeight() / 2, Align.right).show();
        //测试百分比坐标
        Image rate = game.getImage(50, 50).setColor(Color.BLUE).setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() / 2, getBottom() + 10, Align.bottom).show();
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

    @Override
    public void back() {

    }

}
