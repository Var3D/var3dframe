package var3d.net.demo.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.demo.R;

public class Stage3DTest extends VStage {

    private PerspectiveCamera cam;//摄像机
    public CameraInputController camController;//摄像机控制器
    public Environment environment;//灯光
    public ModelBatch modelBatch;//模型绘制器（类似于Batch）
    public Array<ModelInstance> instances = new Array<>();

    public Stage3DTest(VGame game) {
        super(game);
        game.loadToModelAll(R.model.test);//将模型打包到模型库(多个模型的纹理将被打包为大图)
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);

        //创建标题
        VLabel lab_title = game.getLabel("3D示例").setPosition(getWidth() / 2, getTop() - 10, Align.top)
                .touchOff().show();
        //返回
        Button btn_close = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_close.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.removeProcessor(camController);//自己添加的操控对象需要自己移除
                game.setStage(StageMain.class);
            }
        });


        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1f));//添加环境光
        environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, -.5f, -0.6f, -1f));//添加方向光
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(5f, 5f, 5f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 1000f;
        cam.update();
        camController = new CameraInputController(cam);
        //game.addProcessor(camController);

        ModelInstance test = game.getModelInstance("cube");//从模型库中创建名叫cube的对象
        instances.add(test);
    }

    public void draw() {
        drawBackground();//绘制背景

        //绘制3D部分
        camController.update();
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();

        drawForeground();//绘制2DUI
    }

    @Override
    public void start() {

    }

    @Override
    public void reStart() {
    }

    @Override
    public void show() {
        game.addProcessor(camController);
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
