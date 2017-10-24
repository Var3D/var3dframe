package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


/**
 * Created by fengyu on 2017/10/24.
 */

public class DialogMessge extends VDialog {
    public static String MODEL = "_!5#0";

    public VLabel lab_msg;
    public Image img_bg;

    private VLabel lab_close;

    public DialogMessge(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        setBackground(game.getUI(new Actor()).setSize(game.WIDTH, game.HEIGHT).getActor());
        img_bg = game.getImage(getWidth() / 2, 1).show(this);
        lab_msg = game.getLabel("messge").setColor(Color.DARK_GRAY).show(this);
        lab_msg.setWrap(true);
        lab_msg.setAlignment(Align.center);
        lab_msg.setWidth(getWidth() / 2 - game.getDefaultFontSize());
        lab_msg.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.removeDialog(DialogMessge.this);
            }
        });
    }

    @Override
    public void reStart() {

    }

    @Override
    public void show() {
        clearActions();
        setStartActions(game.MOVEUP);
        Object obj = game.getUserData(MODEL);
        float time = 3;
        if (obj != null) {
            Model model = (Model) obj;
            lab_msg.setColor(model.labColor);
            if (model.labStrokeColor != null) lab_msg.setStroke(model.labStrokeColor);
            lab_msg.setText(model.messge);

            float height = lab_msg.getPrefHeight() + game.getDefaultFontSize();
            img_bg.setHeight(height);
            img_bg.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
            img_bg.setColor(model.bgColor);

            time = model.time;
            if (time != -1f) {
                addAction(Actions.delay(time, Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.removeDialog(DialogMessge.this);
                    }
                })));
            } else {
                if (lab_close == null) {
                    lab_close = game.getLabel("Ⓧ").setColor(Color.RED).touchOff()
                            .setPosition(img_bg.getWidth(), img_bg.getHeight(), Align.center).show(this);
                }
            }
        } else {
            addAction(Actions.delay(time, Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.removeDialog(DialogMessge.this);
                }
            })));
        }
    }

    @Override
    public void start() {

    }

    public class Model {
        public String messge = "";
        public float time = 3;
        public Color bgColor = Color.WHITE;
        public Color labColor = Color.DARK_GRAY;
        public Color labStrokeColor = null;
    }
}
