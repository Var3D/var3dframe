package var3d.net.center.freefont;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feng on 2019/3/5.
 */

public class FreeBitmapFontData extends BitmapFont.BitmapFontData {

    public boolean isBreakChar(char c) {
        if (breakChars != null) {
            return super.isBreakChar(c);
        } else {
            if (isChinese("" + c, "[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF]"))
                return true;
            return false;
        }
    }


    public static boolean isChinese(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

//    public void getGlyphs (GlyphLayout.GlyphRun run, CharSequence str, int start, int end, boolean tightBounds) {
//       super.getGlyphs(run,str,start,end,tightBounds);
//    }

    public void getGlyphsForVTexField (GlyphLayout.GlyphRun run, CharSequence str, int start, int end, boolean tightBounds) {
        boolean markupEnabled = this.markupEnabled;
        float scaleX = this.scaleX;
        BitmapFont.Glyph missingGlyph = this.missingGlyph;
        Array<BitmapFont.Glyph> glyphs = run.glyphs;
        FloatArray xAdvances = run.xAdvances;

        // Guess at number of glyphs needed.
        glyphs.ensureCapacity(end - start);
        xAdvances.ensureCapacity(end - start + 1);

        BitmapFont.Glyph lastGlyph = null;
        while (start < end) {
            char ch = str.charAt(start++);
            BitmapFont.Glyph glyph = getGlyph(ch);
            if (glyph == null) {
                if (missingGlyph == null) continue;
                glyph = missingGlyph;
            }

            glyphs.add(glyph);

            if (lastGlyph == null) // First glyph.
                xAdvances.add((!tightBounds || glyph.fixedWidth) ? 0 : -glyph.xoffset * scaleX - padLeft);
            else
                xAdvances.add((lastGlyph.xadvance + lastGlyph.getKerning(ch)) * scaleX);
            lastGlyph = glyph;

            // "[[" is an escaped left square bracket, skip second character.
            //if (markupEnabled && ch == '[' && start < end && str.charAt(start) == '[') start++;
        }
        if (lastGlyph != null) {
            float lastGlyphWidth = (!tightBounds || lastGlyph.fixedWidth) ? lastGlyph.xadvance
                    : lastGlyph.xoffset + lastGlyph.width - padRight;
            xAdvances.add(lastGlyphWidth * scaleX);
        }
    }
}
