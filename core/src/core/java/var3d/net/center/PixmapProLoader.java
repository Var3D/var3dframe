package var3d.net.center;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by fengyu on 16/11/19.
 */
public class PixmapProLoader extends
		AsynchronousAssetLoader<PixmapPro, PixmapProLoader.PixmapParameter> {
	PixmapPro pixmapPro;

	public PixmapProLoader() {
		super(new InternalFileHandleResolver());
	}

	public void loadAsync(AssetManager manager, String fileName,
			FileHandle file, PixmapParameter parameter) {
		pixmapPro = null;
		pixmapPro = new PixmapPro(file);
	}

	public PixmapPro loadSync(AssetManager manager, String fileName,
			FileHandle file, PixmapParameter parameter) {
		PixmapPro pixmap = this.pixmapPro;
		this.pixmapPro = null;
		return pixmap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			FileHandle file, PixmapParameter parameter) {
		return null;
	}

	static public class PixmapParameter extends
			AssetLoaderParameters<PixmapPro> {
	}
}
