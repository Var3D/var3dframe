package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.SLabel;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VProgressBar;
import var3d.net.center.VStage;
import var3d.net.demo.R;

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
        VLabel lab_title = game.getLabel("常用控件").setPosition(getWidth() / 2
                , getTop() - 10, Align.top).touchOff().show();
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

        VLabel lab_title2 = game.getLabel("描边测试").setColor(Color.YELLOW).setPosition(getWidth() / 2
                , getTop() - 40, Align.top).touchOff().setStroke(Color.RED).show();
        lab_title2.addAction(Actions.forever(Actions.sequence(Actions.alpha(0,1),Actions.alpha(1,1))));
        VLabel lab_title3 = game.getLabel("emoji测试◐◐").setColor(Color.YELLOW).setPosition(getWidth() / 2
                , getTop() - 70, Align.top).touchOff().show();
        lab_title3.addAction(Actions.forever(Actions.sequence(Actions.alpha(0,1),Actions.alpha(1,1))));
        VLabel lab_title4 = game.getLabel("emoji带描边测试◐◐").setColor(Color.CYAN).setPosition(getWidth() / 2
                , getTop() - 100, Align.top).touchOff().setStroke(Color.BLUE).show();
        lab_title4.addAction(Actions.forever(Actions.sequence(Actions.alpha(0,1),Actions.alpha(1,1))));

        VLabel lab_title5 = game.getLabel("带背景的label").setColor(Color.CYAN).setPosition(getWidth() / 2
                , getTop() - 150, Align.top).touchOff().show();
        lab_title5.setBackground(game.getRectColorDrawable(1,1,Color.ORANGE));
//

        //测试SLabel可缩放字体
        VLabel lab_test1 = game.getLabel("测试不可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW)
                .setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();
        lab_test1.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.WHITE, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));
        SLabel lab_test2 = game.getSLabel("测试可缩放字体控件").setOrigin(Align.center).setColor(Color.YELLOW).setPosition(getRateX(0.5f), getRateY(0.4f), Align.center).show();
        lab_test2.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 1), Actions.color(Color.CYAN, 1), Actions.scaleTo(1f, 1f, 1), Actions.color(Color.RED, 1))));

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
        game.setStage(StageMain.class);
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
