package var3d.net.center.component;

/**
 * Created by yufeng on 2019/12/8.
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
        setBackground(30,30, Color.CLEAR);
        group=game.getGroup(getWidth(),getHeight()).touchOff().setOrigin(Align.center).setPosition(
                getWidth()*0.5f,getHeight()*0.5f,Align.center).show();
        for(int i=0;i<12;i++){
            Image img_line=game.getImage(group.getWidth(),8).setPosition(getWidth()*0.5f+25
                    ,getHeight()*0.5f,Align.left).touchOff().setOrigin(-25,self()
                    .getHeight()*0.5f).setAlpha(1-0.076f*i).show(group);
            img_line.setRotation(i*30);
            lines.add(img_line);
        }
        group.addAction(Actions.forever(Actions.rotateBy(-360,2)));
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
}