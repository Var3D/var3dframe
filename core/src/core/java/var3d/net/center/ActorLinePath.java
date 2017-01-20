package var3d.net.center;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * 路径线段类
 * 
 * @author Var3D
 * 
 */
public class ActorLinePath extends Actor {
	private ShapeRenderer rend;
	private Array<Vector2> path;
	private float strokeWidth = 1;

	public ActorLinePath() {
		rend = new ShapeRenderer();
	}

	public ActorLinePath(float strokeWidth) {
		this();
		this.strokeWidth = strokeWidth;
	}

	public ActorLinePath(Array<Vector2> path) {
		this();
		this.path = path;
	}

	public ActorLinePath(Vector2... vector2s) {
		this();
		path = new Array<Vector2>();
		for (Vector2 vector : vector2s) {
			path.add(vector);
		}
	}

	/**
	 * 设置线条粗细
	 * 
	 * @param strokeWidth
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * 新增一个点
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(float x, float y) {
		if (path == null)
			path = new Array<Vector2>();
		path.add(new Vector2(x, y));
	}

	public void draw(Batch batch, float a) {
		batch.end();
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.setColor(getColor());
		rend.begin(ShapeType.Filled);
		for (int i = 0; i < path.size - 1; i++) {
			Vector2 now = path.get(i);
			Vector2 next = path.get(i + 1);
			rend.rectLine(now.x + getX(), now.y + getY(), next.x + getX(),
					next.y + getY(), strokeWidth);
			rend.circle(now.x + getX(), now.y + getY(), strokeWidth / 2);
		}
		rend.circle(path.peek().x + getX(), path.peek().y + getY(),
				strokeWidth / 2);
		rend.end();
		batch.begin();
	}
}
