package var3d.net.center;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**
 * 抛物线Action
 * 
 * @author fengyu
 * 
 */
public class ActionParabola extends TemporalAction {
	// 表示水平速度和垂直速度以及重力加速度
	private float vx, vy, g;
	private Vector2 temp = null;

	/**
	 * 
	 * @param vx水平初速度
	 *            (像素/秒)
	 * @param vy垂直初速度
	 *            (像素/秒)
	 * @param g重力加速度
	 * @param duration动画持续时间
	 */
	public ActionParabola(float vx, float vy, float g, float duration) {
		super(duration);
		this.vx = vx / 60f;
		this.vy = vy / 60f;
		this.g = g / 60f;
	}

	@Override
	protected void update(float percent) {
		if (temp == null) {
			temp = new Vector2(actor.getX(), actor.getY());
		}
		// TODO Auto-generated method stub
		valueAt(temp, percent);
		actor.setPosition(temp.x, temp.y);
	}

	private void valueAt(Vector2 temp, float percent) {
		// Gdx.app.log("aaaaaaa", "percent=" + percent);
		temp.x += vx;
		vy -= g;
		temp.y += vy;
	}
}
