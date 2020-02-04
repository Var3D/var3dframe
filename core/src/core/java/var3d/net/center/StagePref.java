package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StagePref extends VStage {
	private Image bg;
	private boolean isVisible = true;

	public StagePref(VGame game) {
		super(game);
		bg = game.getImage(game.WIDTH, game.HEIGHT, Color.BLACK).getActor();
		bg.setTouchable(Touchable.disabled);
		addActor(bg);
		getRoot().setTouchable(Touchable.disabled);
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void refush() {
		bg.setDrawable(game.getFullTextureRegionDrawable());
		bg.setColor(Color.WHITE);
	}

	public void draw() {
		if (isVisible) {
			super.act();
			super.draw();
		}
	}

	public void init() {

	}

	public void start() {

	}

	public void reStart() {
	}

	@Override
	public void show() {

	}

	@Override
	public void changing(float width, float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void back() {
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