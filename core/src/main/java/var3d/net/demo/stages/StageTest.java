package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.DialogMessge;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.demo.R;
import var3d.net.demo.dialogs.DialogRatio;
import var3d.net.demo.dialogs.DialogTestFull;

/**
 * Created by fengyu on 16/4/8.
 */
public class StageTest extends VStage {
    private VLabel txt_logo;
    private Button btn_tost, btn_dialog, btn_dialogfull;

    public StageTest(VGame game) {
        super(game, true);
        game.loadFolderToPack(R.image.class);
    }

    @Override
    public void init() {
        //setBackground(R.image.bg);//设置背景
        setBackground(Color.DARK_GRAY);//设置背景
        //设置标题
        txt_logo = game.getLabel(game.bundle.get("logo")).setColor(Color.BLUE).setStroke(Color.CYAN)
                .setPosition(getWidth() / 2, getHeight() / 2f, Align.center).setAlpha(0.9f).touchOff().show();
        //Tost
        // Gdx.app.log("aaaaaa", "cutWidth=" + getCutWidth() + "*" + getCutHeight());
        btn_tost = game.getButton(R.image.pause_btn_bg).setSize(300, 50)
                .setPosition(getWidth() / 2, getTop(), Align.top).addClicAction().show();
        btn_tost.add(game.getLabel(R.strings.tost).getActor());
        btn_tost.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setUserData(DialogMessge.MODEL, new DialogMessge.Model("这是一个Tost范例"));
                game.showDialog(DialogMessge.class);
            }
        });

        //自定义对话框
        btn_dialog = game.getButton(R.image.pause_btn_bg).setSize(300, 50)
                .setPosition(getWidth() / 2, getBottom(), Align.bottom).addClicAction().show();
        btn_dialog.add(game.getLabel("等比例对话框").getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(DialogRatio.class);
            }
        });

        //全屏对话框
        btn_dialogfull = game.getButton(R.image.pause_btn_bg).setSize(300, 50)
                .setPosition(getWidth() / 2, btn_dialog.getTop()+10, Align.bottom).addClicAction().show();
        btn_dialogfull.add(game.getLabel("拉伸全屏对话框").getActor());
        btn_dialogfull.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showDialog(DialogTestFull.class);
            }
        });

        final Image left = game.getImage(50, 50).setPosition(getLeft(), getHeight() / 2, Align.left).show();
        left.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                left.moveBy(10, 0);
            }
        });
        Image right = game.getImage(50, 50).setPosition(getRight(), getHeight() / 2, Align.right).show();

        Image testTopRight = game.getImage(50, 50, Color.RED)
                .setPosition(getRight(), getTop(), Align.topRight).show();

        Image test = game.getImage(50, 50, Color.YELLOW).setFillet(30).setOrigin(Align.center)
                .setPosition(getWidth() / 2, 350, Align.center).show();
        test.addAction(Actions.forever(Actions.rotateBy(3)));

        //测试百分比坐标
        Image rate = game.getImage(50, 50).setColor(Color.BLUE).setPosition(getRateX(0.5f), getRateY(0.5f), Align.center).show();

        //shader测试
//        WaterActor waterActor = game.getUI(new WaterActor(game.getTextureRegion(R.image.badlogic))).show(Align.center);
//        OutLineActor outLineActor = game.getUI(new OutLineActor(game.getTextureRegion(R.image.tank_4)))
//                .setPosition(getWidth() / 2, waterActor.getY() - 10, Align.top).show();

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
