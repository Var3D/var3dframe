package var3d.net.center;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorRectRound extends Actor {
	private ShapeRenderer rend;
	private Color col1 = Color.valueOf("01264f"), col2 = Color
			.valueOf("0567d6");// 内部渐变色
	private Color outColor = Color.valueOf("05aae0");// 描边颜色
	private float radius = 20;// 圆角半径

	public ActorRectRound() {
		rend = new ShapeRenderer();
	}

	public ActorRectRound(float width, float height) {
		this();
		setSize(width, height);
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setOutColor(Color color) {
		this.outColor = color;
	}

	public void setColor(Color col1, Color col2) {
		this.col1 = col1;
		this.col2 = col2;
	}

	public void setColor(Color color) {
		col1 = col2 = color;
	}

	private void drawBgRect(float x, float y, float width, float height) {
		rend.setColor(outColor);
		rend.rect(x + radius, y + height - radius / 2, width - radius * 2,
				radius / 2);
		rend.arc(x + width - radius, y + height - radius, radius, 0, 90);
		rend.arc(x + radius, y + height - radius, radius, 90, 90);
		rend.setColor(outColor);
		rend.rect(x + radius, y, width - radius * 2, radius / 2);
		rend.arc(x + radius, y + radius, radius, 180, 90);
		rend.arc(x + width - radius, y + radius, radius, 270, 90);
		rend.rect(x, y + radius, radius / 2, height - radius * 2);
		rend.rect(x + width - radius / 2, y + radius, radius / 2, height
				- radius * 2);
	}

	private void drawCircleRect(float x, float y, float width, float height) {
		rend.setColor(col1);
		rend.rect(x + radius, y + height - radius, width - radius * 2, radius);
		rend.arc(x + width - radius, y + height - radius, radius, 0, 90);
		rend.arc(x + radius, y + height - radius, radius, 90, 90);
		rend.setColor(col2);
		rend.rect(x + radius, y, width - radius * 2, radius);
		rend.arc(x + radius, y + radius, radius, 180, 90);
		rend.arc(x + width - radius, y + radius, radius, 270, 90);
		rend.rect(x, y + radius, radius / 2, height - radius * 2, col2, col2,
				col1, col1);
		rend.rect(x + width - radius / 2, y + radius, radius / 2, height
				- radius * 2, col2, col2, col1, col1);
		rend.rect(x, y + radius, width, height - radius * 2, col2, col2, col1,
				col1);
	}

	public void draw(Batch batch, float a) {
		batch.end();
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.begin(ShapeType.Filled);
		drawBgRect(getX(), getY(), getWidth(), getHeight());
		drawCircleRect(getX() + 5, getY() + 5, getWidth() - 10,
				getHeight() - 10);

		rend.end();
		batch.begin();
	}
}
