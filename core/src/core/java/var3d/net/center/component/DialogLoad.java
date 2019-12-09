package var3d.net.center.component;

/**
 * Created by yufeng on 2019/12/8.
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
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
        setBackground(50,50, Color.CLEAR);
        group=game.getGroup(50,50).touchOff().setOrigin(Align.center).setPosition(
                getWidth()*0.5f,getHeight()*0.5f,Align.center).show();
        for(int i=0;i<8;i++){
            Image img_line=game.getImage(group.getWidth(),12).setPosition(getWidth()*0.5f+30
                    ,getHeight()*0.5f,Align.left).touchOff().setOrigin(-30,self()
                    .getHeight()*0.5f).setAlpha(0.1f+0.09f*i).show(group);
            img_line.setRotation(i*45);
            lines.add(img_line);
        }
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