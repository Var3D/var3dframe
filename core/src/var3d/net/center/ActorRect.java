package var3d.net.center;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 线段类
 * 
 * @author Var3D
 * 
 */
public class ActorRect extends Actor {
	private ShapeRenderer rend;
	private float startX, startY, endX, endY;

	public ActorRect(float startX, float startY, float endX, float endY) {
		rend = new ShapeRenderer();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public void setStartPosition(float startX, float startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public void setEndPosition(float endX, float endY) {
		this.endX = endX;
		this.endY = endY;
	}

	public void draw(Batch batch, float a) {
		batch.end();
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.setColor(getColor());
		rend.begin(ShapeType.Line);
		// rend.line(startX, startY, endX, endY);
		rend.rect(startX, startY, endX, endY, getOriginX(), getOriginY(),
				getScaleX(), getScaleY(), getRotation());
		rend.end();
		batch.begin();
	}
}
