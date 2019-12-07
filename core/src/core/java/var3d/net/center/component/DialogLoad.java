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

    private DialogLoadData data=new DialogLoadData();
    private Array<Image> lines=new Array<>();
    private Group group;

    public static class DialogLoadData{
        public Color color=Color.WHITE;
        public float size=50;
        public float speed=1f;

        public DialogLoadData(){

        }

        public DialogLoadData(Color color,float size,float speed){
            this.color=color;
            this.size=size;
            this.speed=speed;
        }
    }

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
        if(game.getData() ==null ||game.getData(0)==null )return;
        this.data=game.getData(0);
        for(Image img:lines){
            img.setColor(data.color.r,data.color.g,data.color.b,img.getColor().a);
            img.setWidth(data.size);
        }
        group.addAction(Actions.forever(Actions.rotateBy(360, 2/data.speed)));
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