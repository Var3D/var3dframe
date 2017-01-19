package var3d.net.center;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by fengyu on 16/11/19.
 */
public class PixmapPro implements Disposable {
    private Pixmap pixmap;
    private byte[] defByte = new byte[2];
    private String name;

    public PixmapPro(FileHandle file) {
        name = file.path();
        pixmap = getPixmap(file);
    }

    public String getName() {
        return name;
    }

    public Pixmap getPixmap() {
        return pixmap;
    }

    public Texture getTextrue() {
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Pixmap getPixmap(FileHandle file) {
        byte[] bs = file.readBytes();
        defByte[0] = bs[0];
        defByte[1] = bs[1];
        String str_head = bytesToHexString(defByte);
        if (str_head.equals("8950") || str_head.equals("ffd8")) {
            return new Pixmap(bs, 0, bs.length);
        }
        int k = bs[0];
        for (int i = 1; i < bs.length; i++) {
            bs[i] = (byte) (bs[i] ^ k);
        }
        return new Pixmap(bs, 1, bs.length);
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == src || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @Override
    public void dispose() {
//        pixmap.dispose();
    }
}
