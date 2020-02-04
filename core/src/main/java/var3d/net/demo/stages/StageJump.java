package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by yufeng on 2019/12/7.
 */

public class StageJump extends VStage {
    private Image img_box,img_line;
    private float vx,vy;
    private float Gravity=0.5f;

    public StageJump(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //创建标题
        VLabel lab_title = game.getLabel("跳跃示例").setPosition(getWidth() / 2, getTop() - 10, Align.top)
                .touchOff().show();
        //关闭按钮
        Button btn_close = game.getTextButton("关闭", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(100, 40).addClicAction().setPosition(getWidth() / 2, 50, Align.bottom).show();
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //舞台其实不能关闭,只能跳转
                game.setStage(StageMain.class);
            }
        });


        img_line=game.getImage(getFullWidth(),1).setPosition(getWidth()*0.5f,btn_close.getTop()+30,Align.center).show();
        img_box=game.getImage(50,50).touchOff().setPosition(getWidth()*0.5f,img_line.getTop(),Align.bottom).show();


        //跳
        Button btn_jump = game.getTextButton("跳", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(40, 40).addClicAction().setPosition(btn_close.getRight()+50, btn_close.getY()).show();
        btn_jump.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                 vy=15f;
            }
        });

        //左
        Button btn_left = game.getTextButton("左", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(40, 40).addClicAction().setPosition(pref().getRight()+50, btn_close.getY()).show();
        btn_left.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                vx=-5;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                vx=0;
            }
        });

        //右
        Button btn_right= game.getTextButton("右", Color.WHITE, Color.valueOf("ff2266"))
                .setSize(40, 40).addClicAction().setPosition(pref().getRight(), btn_close.getY()).show();
        btn_right.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                vx=5;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                vx=0;
            }
        });
    }

    public void act(){
        super.act();
        vy-=Gravity;
        img_box.moveBy(vx,vy);
        if(img_box.getY()<=img_line.getY()) img_box.setY(img_line.getY());
    }

    public boolean keyDown(int arg0) {
        if(arg0== Input.Keys.NUM_1)vy=12;
       return super.keyDown(arg0);
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
