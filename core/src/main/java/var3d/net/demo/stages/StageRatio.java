package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by feng on 2018/5/20.
 */

public class StageRatio extends VStage {

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("等比例舞台").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() / 2, 50, Align.bottom).show();
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //舞台其实不能关闭,只能跳转
                game.setStage(new StageStages());
            }
        });

        //线条，标识出黑边区域
        game.getImage(1,getFullHeight()).setPosition(0,getHeight()/2,Align.center).touchOff().show();
        game.getImage(1,getFullHeight()).setPosition(getWidth(),getHeight()/2,Align.center).touchOff().show();
        game.getImage(getFullWidth(),1).setPosition(getWidth()/2,0,Align.center).touchOff().show();
        game.getImage(getFullWidth(),1).setPosition(getWidth()/2,getHeight(),Align.center).touchOff().show();
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
