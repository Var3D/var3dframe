package var3d.net.center.component;

/**
 * Created by yufeng on 2019/12/8.
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import var3d.net.center.VDialog;
import var3d.net.center.VGame;

/**
 * loading圈
 */

public class DialogLoad extends VDialog {

    private Array<Image> lines=new Array<>();
    private Group group;

    public DialogLoad(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        setBackground(Color.CLEAR);
        group=game.getGroup(30,30).touchOff().setOrigin(Align.center).setPosition(
                getWidth()*0.5f,getHeight()*0.5f,Align.center).show();
        for(int i=0;i<12;i++){
            Image img_line=game.getImage(group.getWidth(),8).setPosition(group.getWidth()*0.5f+25
                    ,group.getHeight()*0.5f,Align.left).touchOff().setOrigin(-25,self()
                    .getHeight()*0.5f).setAlpha(1-0.076f*i).show(group);
            img_line.setRotation(i*30);
            lines.add(img_line);
        }
        group.addAction(Actions.forever(Actions.rotateBy(-360,2)));

        Button img_close=game.getCloseButton().show();
        img_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(run!=null)run.run();
                game.removeDialog(DialogLoad.this);
            }
        });
    }

    private Runnable run;
    public void setCloseRunnable(Runnable run){
        this.run=run;
    }

    @Override
    public void reStart() {

    }

    @Override
    public void show() {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void back() {

    }
}