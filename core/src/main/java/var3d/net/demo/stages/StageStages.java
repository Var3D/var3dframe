package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by feng on 2018/5/20.
 */

public class StageStages extends VStage {
    public StageStages(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("舞台例子").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();

        //返回
        Button btn_back = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), lab_title.getY() - 10, Align.topLeft).addClicAction().show();
        btn_back.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageMain.class);
            }
        });

        //等比例舞台
        Button btn_dialog = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), btn_back.getY() - 10, Align.topLeft).addClicAction().show();
        btn_dialog.add(game.getLabel("等比例舞台").setFontScale(0.6f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setStage(StageRatio.class);
            }
        });

        //非等比例对话框
        Button btn_unRatio = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), btn_dialog.getY() - 10, Align.topLeft).addClicAction().show();
        btn_unRatio.add(game.getLabel("非等比例舞台").setFontScale(0.6f).getActor());
        btn_unRatio.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setStage(StageUnRatio.class);
            }
        });

        //等待添加
        Button btn_more = game.getButton().setColor(Color.ORANGE).setSize(120, 40)
                .setPosition(getLeft(), btn_unRatio.getY() - 10, Align.topLeft).addClicAction().show();
        btn_more.add(game.getLabel("等待添加...").setFontScale(0.6f).getActor());
        btn_more.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("更多效果尽请期待...");
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
