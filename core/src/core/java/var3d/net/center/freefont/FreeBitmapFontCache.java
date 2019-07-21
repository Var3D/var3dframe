package var3d.net.center.freefont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

/**
 * Created by fengyu on 2019/7/20.
 */

public class FreeBitmapFontCache extends BitmapFontCache{

    private final Array<GlyphLayout> pooledLayouts = new Array();
    private StringBuffer buffer;

    public FreeBitmapFontCache(BitmapFont font, boolean integer) {
        super(font, integer);
    }

    public GlyphLayout addText (CharSequence str, float x, float y, int start, int end, float targetWidth, int halign,
                                boolean wrap, String truncate) {
//        FreeBitmapFont freefont= (FreeBitmapFont) getFont();
//        if(freefont.isEmoji()){
//            if(buffer==null)buffer=new StringBuffer();
//            buffer.setLength(0);
//            Gdx.app.log("aaaa","start="+start);
//
//            for(int i=0;i<str.length();i++){
//                char c=str.charAt(i);
//                if(freefont.isCreatedEmoji4WithKey(""+c)||freefont.isCreatedEmoji2WithKey(""+c)){
//                    buffer.append("[白]");
//                    buffer.append(c);
//                    end+=3;
//                }else buffer.append(c);
//            }
//            str=buffer.toString();
//        }

        //Gdx.app.log("aaaaaa","执行吗"+str.subSequence(start,end));
        FreeGlyphLayout layout = Pools.obtain(FreeGlyphLayout.class);
        pooledLayouts.add(layout);
        layout.setText(getFont(), str, start, end, getColor(), targetWidth, halign, wrap, truncate);
        addText(layout, x, y);
        return layout;
    }

    public void clear () {
        super.clear();
        Pools.freeAll(pooledLayouts, true);
        pooledLayouts.clear();
    }
}
