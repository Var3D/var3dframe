package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

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
        super(game);
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
        VLabel lab_test1 = game.getLabel("测试不可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW).setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();
        lab_test1.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.WHITE, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));
        SLabel lab_test2 = game.getSLabel("测试可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW).setPosition(getRateX(0.5f), getRateY(0.4f), Align.center).show();
        lab_test2.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.CYAN, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));
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
