package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.center.component.DialogLoad;
import var3d.net.demo.Game;
import var3d.net.demo.dialogs.DialogAD;
import var3d.net.demo.dialogs.DialogLoginGdx;
import var3d.net.demo.dialogs.DialogRatio;
import var3d.net.demo.dialogs.DialogTestFull;
import var3d.net.demo.dialogs.DialogUnRatio;

/**
 * Created by feng on 2018/5/20.
 */

public class StageDialogs extends VStage {

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("对话框例子").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();


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

        //等比例对话框
        Button btn_dialog = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_dialog.add(game.getLabel("等比例对话框").setFontScale(0.6f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogRatio());
            }
        });

        //非等比例对话框
        Button btn_unRatio = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_unRatio.add(game.getLabel("非等比例对话框").setFontScale(0.6f).getActor());
        btn_unRatio.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogUnRatio());
            }
        });

        //全屏对话框
        Button btn_full = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_full.add(game.getLabel("全屏对话框").setFontScale(0.6f).getActor());
        btn_full.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogTestFull());
            }
        });

        //登录对话框(libgdx输入框)
        Button btn_login_gdx = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_login_gdx.add(game.getLabel("登录(gdx输入)").setFontScale(0.6f).getActor());
        btn_login_gdx.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogLoginGdx());
            }
        });

        //吐司
        Button btn_tost = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_tost.add(game.getLabel("Tost测试").setFontScale(0.6f).getActor());
        btn_tost.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("这是一个Tost范例,字多一些看看效果如何");
            }
        });

        //自推广广告
        Button btn_ad = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_ad.add(game.getLabel("自推广广告").setFontScale(0.6f).getActor());
        btn_ad.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogAD());
            }
        });

        //默认loading圈
        Button btn_load = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_load.add(game.getLabel("Loading圈").setFontScale(0.6f).getActor());
        btn_load.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(new DialogLoad());
            }
        });

        //等待添加
        Button btn_more = game.getButton().setColor(Color.ORANGE).setSize(120, 40)
                .setPosition(pref().getRight()+10, lab_title.getY() - 10, Align.topLeft).addClicAction().show();
        btn_more.add(game.getLabel("等待添加...").setFontScale(0.6f).getActor());
        btn_more.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("更多效果尽请期待...");
            }
        });


        //线条，标识出黑边区域
//        game.getImage(1,getFullHeight()).setPosition(0,getHeight()/2,Align.center).touchOff().show();
//        game.getImage(1,getFullHeight()).setPosition(getWidth(),getHeight()/2,Align.center).touchOff().show();
//        game.getImage(getFullWidth(),1).setPosition(getWidth()/2,0,Align.center).touchOff().show();
//        game.getImage(getFullWidth(),1).setPosition(getWidth()/2,getHeight(),Align.center).touchOff().show();
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
