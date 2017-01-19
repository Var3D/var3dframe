package var3d.net.center.freefont;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * freeFont接口
 * 
 * @author Var3D
 * 
 */
public interface FreeListener {
	// 返回一个字符纹理
	public Pixmap getFontPixmap(String txt, FreePaint vpaint);
}
