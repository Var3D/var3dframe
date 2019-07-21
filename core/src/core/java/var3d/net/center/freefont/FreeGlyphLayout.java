package var3d.net.center.freefont;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 * Created by fengyu on 2019/7/20.
 */

public class FreeGlyphLayout extends GlyphLayout {
    private final Array<Color> colorStack = new Array(4);
    private Color inColor;

    public void setText (BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign,
                         boolean wrap, String truncate) {
        FreeBitmapFont freefont= (FreeBitmapFont) font;

        inColor=color;
        int intStart=start;

        BitmapFont.BitmapFontData fontData = font.getData();
        boolean markupEnabled = fontData.markupEnabled;

        Pool<GlyphRun> glyphRunPool = Pools.get(GlyphRun.class);
        Array<GlyphRun> runs = this.runs;
        glyphRunPool.freeAll(runs);
        runs.clear();

        float x = 0, y = 0, width = 0;
        int lines = 0, blankLines = 0;

        Array<Color> colorStack = this.colorStack;
        Color nextColor = null;

        colorStack.add(nextColor);

        Pool<Color> colorPool = Pools.get(Color.class);

        int runStart = start;
        outer:
        while (true) {
            // Each run is delimited by newline or left square bracket.
            int runEnd = -1;
            boolean  colorRun = false;
            if (start == end) {
                if (runStart == end) break; // End of string with no run to process, we're done.
                runEnd = end; // End of string, process last run.
            } else {
                char nowC=str.charAt(start++);
                char nextC = 0;
                if(start<str.length())nextC=str.charAt(start);

                if(nextC!=0&&freefont.isCreateEmojiWithKey(""+nextC)){
                    if(intStart+1==start){
                        GlyphRun run = glyphRunPool.obtain();
                        run.color.set(Color.WHITE);
                        run.x = x;
                        run.y = y;
                        fontData.getGlyphs(run, str, start-1, start, true);
                        if (run.glyphs.size == 0)
                            glyphRunPool.free(run);
                        else {
                            runs.add(run);

                            float[] xAdvances = run.xAdvances.items;

                            for (int i = 0, n = run.xAdvances.size; i < n; i++) {
                                float xAdvance = xAdvances[i];
                                x += xAdvance;
                                run.width += xAdvance;
                            }
                        }
                        runStart=start;
                    }
                    runEnd = start;
                    nextColor=Color.WHITE;
                    colorRun = true;
                }else if(freefont.isCreateEmojiWithKey(""+nowC)){
                    if(intStart+1==start){
                        GlyphRun run = glyphRunPool.obtain();
                        run.color.set(Color.WHITE);
                        run.x = x;
                        run.y = y;
                        fontData.getGlyphs(run, str, start-1, start, true);
                        if (run.glyphs.size == 0)
                            glyphRunPool.free(run);
                        else {
                            runs.add(run);

                            float[] xAdvances = run.xAdvances.items;

                            for (int i = 0, n = run.xAdvances.size; i < n; i++) {
                                float xAdvance = xAdvances[i];
                                x += xAdvance;
                                run.width += xAdvance;
                            }
                        }
                        runStart=start;
                    }
                    if(nextC!=0&&freefont.isCreateEmojiWithKey(""+nextC)){
                        nextColor=Color.WHITE;
                    }else nextColor=inColor;
                    runEnd = start;
                    colorRun = true;
                    //Gdx.app.log("aaaaa","now");
                }else {
                    switch (nowC) {
                        case '\n':
                            // End of line.
                            runEnd = start - 1;
                            break;
                        case '[':
                            if (markupEnabled) {
                                int length = parseColorMarkup(str, start, end, colorPool);
                                if (length >= 0) {
                                    runEnd = start - 1;
                                    start += length + 1;
                                    nextColor = colorStack.peek();
                                    colorRun = true;
                                } else if (length == -2) {
                                    start++; // Skip first of "[[" escape sequence.
                                    continue outer;
                                }
                            }
                            break;
                    }
                }
            }

            if (runEnd != -1) {
                if (runEnd != runStart) { // Can happen (eg) when a color tag is at text start or a line is "\n".
                    // Store the run that has ended.
                    GlyphRun run = glyphRunPool.obtain();
                    run.color.set(color);
                    run.x = x;
                    run.y = y;
                    fontData.getGlyphs(run, str, runStart, runEnd, colorRun);
                    if (run.glyphs.size == 0)
                        glyphRunPool.free(run);
                    else {
                        runs.add(run);

                        // Compute the run width, wrap if necessary, and position the run.
                        float[] xAdvances = run.xAdvances.items;

                        for (int i = 0, n = run.xAdvances.size; i < n; i++) {
                            float xAdvance = xAdvances[i];
                            x += xAdvance;
                            run.width += xAdvance;
                        }
                    }
                }

                runStart = start;
                color = nextColor;
            }
        }
        width = Math.max(width, x);

        for (int i = 1, n = colorStack.size; i < n; i++)
            colorPool.free(colorStack.get(i));
        colorStack.clear();

        // Align runs to center or right of targetWidth.
        if ((halign & Align.left) == 0) { // Not left aligned, so must be center or right aligned.
            boolean center = (halign & Align.center) != 0;
            float lineWidth = 0, lineY = Integer.MIN_VALUE;
            int lineStart = 0, n = runs.size;
            for (int i = 0; i < n; i++) {
                GlyphRun run = runs.get(i);
                if (run.y != lineY) {
                    lineY = run.y;
                    float shift = targetWidth - lineWidth;
                    if (center) shift /= 2;
                    while (lineStart < i)
                        runs.get(lineStart++).x += shift;
                    lineWidth = 0;
                }
                lineWidth += run.width;
            }
            float shift = targetWidth - lineWidth;
            if (center) shift /= 2;
            while (lineStart < n) runs.get(lineStart++).x += shift;
        }

        this.width = width;
        this.height = fontData.capHeight + lines * fontData.lineHeight + blankLines * fontData.lineHeight * fontData.blankLineScale;


        //super.setText(font,str,start,end,color,targetWidth,halign,wrap,truncate);
    }

    private int parseColorMarkup (CharSequence str, int start, int end, Pool<Color> colorPool) {
        if (start == end) return -1; // String ended with "[".
        switch (str.charAt(start)) {
            case '#':
                // Parse hex color RRGGBBAA where AA is optional and defaults to 0xFF if less than 6 chars are used.
                int colorInt = 0;
                for (int i = start + 1; i < end; i++) {
                    char ch = str.charAt(i);
                    if (ch == ']') {
                        if (i < start + 2 || i > start + 9) break; // Illegal number of hex digits.
                        if (i - start <= 7) { // RRGGBB or fewer chars.
                            for (int ii = 0, nn = 9 - (i - start); ii < nn; ii++)
                                colorInt = colorInt << 4;
                            colorInt |= 0xff;
                        }
                        Color color = colorPool.obtain();
                        colorStack.add(color);
                        Color.rgba8888ToColor(color, colorInt);
                        return i - start;
                    }
                    if (ch >= '0' && ch <= '9')
                        colorInt = colorInt * 16 + (ch - '0');
                    else if (ch >= 'a' && ch <= 'f')
                        colorInt = colorInt * 16 + (ch - ('a' - 10));
                    else if (ch >= 'A' && ch <= 'F')
                        colorInt = colorInt * 16 + (ch - ('A' - 10));
                    else
                        break; // Unexpected character in hex color.
                }
                return -1;
            case '[': // "[[" is an escaped left square bracket.
                return -2;
            case ']': // "[]" is a "pop" color tag.
                if (colorStack.size > 1) colorPool.free(colorStack.pop());
                return 0;
        }
        // Parse named color.
        int colorStart = start;
        for (int i = start + 1; i < end; i++) {
            char ch = str.charAt(i);
            if (ch != ']') continue;
            Color namedColor = Colors.get(str.subSequence(colorStart, i).toString());
            if (namedColor == null) return -1; // Unknown color name.
            Color color = colorPool.obtain();
            colorStack.add(color);
            color.set(namedColor);
            return i - start;
        }
        return -1; // Unclosed color tag.
    }

}
