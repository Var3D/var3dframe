package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * loading界面
 * 
 * @author Administrator
 * 
 */
public class StageLoad extends VStage {
	private Label label;
	private ShapeRenderer rend;
	private Color color1, color2, color3, color4, color5, color6;
	private float load = 0;

	public StageLoad(VGame game) {
		super(game);
		rend = new ShapeRenderer();
		color1 = Color.valueOf("b9b51a");
		color2 = Color.valueOf("30883f");
		color3 = Color.valueOf("fafd01");
		color4 = Color.valueOf("01fd30");
		color5 = Color.valueOf("89fc00");
		color6 = Color.valueOf("ff2f00");
		label = (Label) game.getLabel("Loading...").getActor();
		label.setPosition(game.getCenterX() - label.getPrefWidth() / 2,
				game.HEIGHT * 0.23f);
		addActor(label);
	}

	private void drawBg() {
		rend.setColor(Color.BLACK);
		rend.rect(0, 0, getWidth(), getHeight());
		float h = 0.025f;
		rend.setColor(Color.WHITE);
		rend.rect(game.WIDTH * 0.1f, game.HEIGHT * 0.3f, game.WIDTH * 0.8f,
				game.HEIGHT * h, color3, color4, color4, color3);
		rend.setColor(color4);
		rend.arc(game.WIDTH * 0.9f, game.HEIGHT * (0.3f + h * 0.5f),
				game.HEIGHT * h * 0.5f, 270, 180);
		rend.setColor(color3);
		rend.arc(game.WIDTH * 0.1f, game.HEIGHT * (0.3f + h * 0.5f),
				game.HEIGHT * h * 0.5f, 90, 180);
	}

	private void drawBgTop() {
		float d = game.HEIGHT * 0.004f;
		float h = 0.025f;
		rend.rect(game.WIDTH * 0.1f + d, game.HEIGHT * 0.3f + d, game.WIDTH
				* 0.8f - d * 2, game.HEIGHT * h - d * 2, color1, color2,
				color2, color1);
		rend.setColor(color2);
		rend.arc(game.WIDTH * 0.9f - d, game.HEIGHT * (0.3f + h * 0.5f),
				game.HEIGHT * h * 0.5f - d, 270, 180);
		rend.setColor(color1);
		rend.arc(game.WIDTH * 0.1f + d, game.HEIGHT * (0.3f + h * 0.5f),
				game.HEIGHT * h * 0.5f - d, 90, 180);
	}

	private void drawLoad() {
		float d = game.HEIGHT * 0.004f;
		float h = 0.025f;
		rend.rect(game.WIDTH * 0.1f + d, game.HEIGHT * 0.3f + d,
				(game.WIDTH * 0.8f - d * 2) * load, game.HEIGHT * h - d * 2,
				color5, color6, color6, color5);
		rend.setColor(color6);
		rend.arc(game.WIDTH * 0.1f + d + (game.WIDTH * 0.8f - d * 2) * load,
				game.HEIGHT * (0.3f + h * 0.5f), game.HEIGHT * h * 0.5f - d,
				270, 180);
		rend.setColor(color5);
		rend.arc(game.WIDTH * 0.1f + d, game.HEIGHT * (0.3f + h * 0.5f),
				game.HEIGHT * h * 0.5f - d, 90, 180);
	}

	public void init() {
		// 控件创建(如果使用异步加载资源策略，控件实例化请勿放到构造函数里)
	}

	public void act(float delta) {
		load = delta;
	}

	public void draw() {
		rend.setProjectionMatrix(getBatch().getProjectionMatrix());
		rend.setTransformMatrix(getBatch().getTransformMatrix());
		rend.begin(ShapeType.Filled);
		drawBg();
		drawBgTop();
		drawLoad();
		rend.end();
		super.draw();
	}

	public void reStart() {

	}

	@Override
	public void back() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changing(float width, float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
