package var3d.net.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpResponseHeader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.ActorLine;
import var3d.net.center.VDialog;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;

/**
 * Created by feng on 2018/3/23.
 */

public class DialogAD extends VDialog {
    private Image img_ad;

    public DialogAD(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.valueOf("eeeeee"));
        setShowActions(ActionType.POPUP);
        setHideActions(ActionType.POPUP);
        //获取默认字体大小
        float fontSize=game.getFont().getLineHeight();
        //设置标题
//        game.getLabel("自家游戏推荐").touchOff().setFontScale(1.3f).setPosition(getWidth() / 2
//                , getHeight() - 30, Align.top).setColor(Color.BLACK).show();
        //关闭按钮
        int r=(int)(Math.min(getWidth(),getHeight())*0.1);
        Texture texture=game.getCircleTexture(r);
        texture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);

        Image img_close_bg=game.getUI(new Image(texture)).setSize(r,r)
                .setPosition(getWidth()-r/2f,getHeight()-r/2f,Align.topRight).touchOff()
                .setColor(Color.DARK_GRAY).setAlpha(0.5f).show();
        Image img_close=game.getUI(new Image(texture)).setSize(img_close_bg).setPosition
                (img_close_bg.getX(),img_close_bg.getY()+2).setColor(Color.WHITE).addClicAction().show();
        float left=img_close.getX(Align.center)-r*0.24f;
        float right=img_close.getX(Align.center)+r*0.24f;
        float top=img_close.getY(Align.center)+r*0.24f;
        float down=img_close.getY(Align.center)-r*0.24f;
        ActorLine line1=game.getUI(new ActorLine(left,top,right,down)).touchOff().setColor(Color.BLACK).show();
        ActorLine line2=game.getUI(new ActorLine(left,down,right,top)).touchOff().setColor(Color.BLACK).show();
        img_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.removeDialog();
            }
        });

        //广告图片
        img_ad=game.getImage(getWidth()/2-r-getSafeLeft(),getHeight()-r-getSafeBottom())
                .setPosition(getLeft()+r/2f,getBottom()+r/2f).show();

        //获取
        int size=(int)(r);
        NinePatch patch=new NinePatch(texture,size,size,size,size);
        Image img_down=game.getUI(new Image(patch)).setSize(r*10,r*2).setOrigin(Align.center)
                .setScale(0.5f).setPosition(getRateX(0.75f),getRateY(0.25f),Align.center)
                .setColor(Color.valueOf("0172d1")).addClicAction().show();
        VLabel lab_down=game.getLabel("获取").setPosition(img_down.getX(Align.center),img_down.getY(Align.center)
                ,Align.center).touchOff().show();

        loadURL("http://www.var3d.net/data.js");
    }


    //从网络读取广告数据
    public void loadURL(final String url){
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
