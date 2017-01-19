package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 渐变类
 * 
 * @author Var3D
 * 
 */
public class ActorGradient extends Actor {
	private ShapeRenderer rend;
	private Color col1, col2, col3, col4;

	public ActorGradient() {
		rend = new ShapeRenderer();
	}

	public ActorGradient(float width, float height) {
		this();
		setSize(width, height);
	}

	public ActorGradient(float width, float height, Color color1, Color color2) {
		this(width, height);
		setColor(color1, color2);
	}

	public void setColor(Color col1, Color col2, Color col3, Color col4) {
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.col4 = col4;
	}

	public void setColor(Color col1, Color col2) {
		this.col1 = this.col2 = col1;
		this.col3 = this.col4 = col2;
	}

	public void setColor(Color color) {
		col1 = col2 = col3 = col4 = color;
	}

	public void draw(Batch batch, float a) {
		batch.end();
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.begin(ShapeType.Filled);
		rend.rect(getX(), getY(), getWidth(), getHeight(), col1, col2, col3,
				col4);
		rend.end();
		batch.begin();
	}
}
