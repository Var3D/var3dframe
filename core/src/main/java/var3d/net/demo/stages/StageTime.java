package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

public class StageTime extends VStage {

    private int time;
    private VLabel lab_time;

    @Override
    public void init() {
        setBackground(Color.BLACK);
        //创建标题
        VLabel lab_title = game.getLabel("计时器示例").setPosition(getWidth() / 2, getTop() - 10, Align.top)
                .touchOff().show();
        //返回
        Button btn_back = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_back.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(new StageMain());
            }
        });

        lab_time = game.getLabel("0").setAlignment(Align.center).setPosition(getRateX(0.5f), getRateY(0.6f), Align.center).touchOff().show();


        //开始
        Button btn_start = game.getButton().setColor(Color.valueOf("ff2266")).setSize(60, 40)
                .setPosition(getRateX(0.5f) - 10, getRateY(0.4f), Align.right).addClicAction().show();
        btn_start.add(game.getLabel("开始").setFontScale(0.6f).getActor());
        btn_start.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                time = 0;
                lab_time.setText("" + time);
                game.removeActions("time");
                game.addActions("time", Actions.forever(Actions.sequence(Actions.delay(0.1f, Actions.run(new Runnable() {
                    public void run() {
                        time++;
                        lab_time.setText("" + time);
                    }
                })))));
            }
        });

        //结束
        Button btn_end = game.getButton().setColor(Color.valueOf("ff2266")).setSize(60, 40)
                .setPosition(getRateX(0.5f) + 10, getRateY(0.4f), Align.left).addClicAction().show();
        btn_end.add(game.getLabel("结束").setFontScale(0.6f).getActor());
        btn_end.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.removeActions("time");
            }
        });
    }

    @Override
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
