package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.DialogMessge;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by fengyu on 16/4/8.
 */
public class StageTest extends VStage {
    private VLabel txt_logo;
    private Button btn_tost,btn_dialog;

    public StageTest(VGame game) {
        super(game,true);
        // game.loadFolderToPackExcept(R.image.class, R.image.tank_4);
    }

    @Override
    public void init() {

        setBackground(R.image.bg);//设置背景
        //设置标题
        txt_logo = game.getLabel(R.strings.logo).setColor(Color.BLACK).setPosition(getWidth() / 2, getHeight() - 20, Align.top).touchOff().show();

        //Tost
        btn_tost = game.getButton(R.image.pause_btn_bg).setPosition(0, 380).addClicAction().show();
        btn_tost.add(game.getLabel(R.strings.tost).getActor());
        btn_tost.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setUserData(DialogMessge.MODEL, new DialogMessge.Model("这是一个Tost范例"));
                game.showDialog(DialogMessge.class);
            }
        });

        //自定义对话框
        btn_dialog = game.getButton(R.image.pause_btn_bg).setPosition(0, 320).addClicAction().show();
        btn_dialog.add(game.getLabel(R.strings.dialogTitle).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(DialogTest.class);
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
