package var3d.net.center.freefont;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import var3d.net.center.tool.Reflex;

/**
 * Created by fengyu on 2019/7/20.
 */

public class FreeGlyphLayout extends GlyphLayout {
    private final Array<Color> colorStack = new Array(4);
    private Color inColor;

    public void setText(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign,
                        boolean wrap, String truncate) {
        FreeBitmapFont freefont = (FreeBitmapFont) font;

        inColor = color;
        int intStart = start;

        BitmapFont.BitmapFontData fontData = font.getData();
        //boolean markupEnabled = fontData.markupEnabled;
        FreeBitmapFontData freeData = (FreeBitmapFontData) fontData;

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
            boolean colorRun = false;
            if (start == end) {
                if (runStart == end) break; // End of string with no run to process, we're done.
                runEnd = end; // End of string, process last run.
            } else {
                char nowC = str.charAt(start++);
                char nextC = 0;
                if (start < str.length()) nextC = str.charAt(start);

                if (nextC != 0 && freefont.isCreateEmojiWithKey("" + nextC)) {
                    if (intStart + 1 == start) {
                        GlyphRun run = glyphRunPool.obtain();
                        run.color.set(Color.WHITE);
                        run.x = x;
                        run.y = y;
                       // fontData.getGlyphs(run, str, start - 1, start, null);
                       // fontData.getGlyphs(run, str, start - 1, start, true);
                        try{
                            Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,null);
                        }catch (Exception e){
                            Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,true);
                        }
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
                        runStart = start;
                    }
                    runEnd = start;
                    nextColor = Color.WHITE;
                    colorRun = true;
                } else if (freefont.isCreateEmojiWithKey("" + nowC)) {
                    if (intStart + 1 == start) {
                        GlyphRun run = glyphRunPool.obtain();
                        run.color.set(Color.WHITE);
                        run.x = x;
                        run.y = y;
                        //fontData.getGlyphs(run, str, start - 1, start, null);
                        //fontData.getGlyphs(run, str, start - 1, start, true);
                        try{
                            Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,null);
                        }catch (Exception e){
                            Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,true);
                        }
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
                        runStart = start;
                    }
                    if (nextC != 0 && freefont.isCreateEmojiWithKey("" + nextC)) {
                        nextColor = Color.WHITE;
                    } else nextColor = inColor;
                    runEnd = start;
                    colorRun = true;
                    //Gdx.app.log("aaaaa","now");
                } else {
                    switch (nowC) {
                        case '\n':
                            // End of line.
                            runEnd = start - 1;
                            break;
//                        case '[':
//                            if (freefont.isEmoji()) {
//                                int length = parseColorMarkup(str, start, end, colorPool);
//                                if (length >= 0) {
//                                    runEnd = start - 1;
//                                    start += length + 1;
//                                    nextColor = colorStack.peek();
//                                    colorRun = true;
//                                } else if (length == -2) {
//                                    start++; // Skip first of "[[" escape sequence.
//                                    continue outer;
//                                }
//                            }
//                            break;
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
                    freeData.getGlyphsForVTexField(run, str, runStart, runEnd, colorRun);
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


    public void setText(BitmapFont font, CharSequence str) {
        setText2(font, str, 0, str.length(), font.getColor(), 0, Align.left, false, null);
    }

    private float getSpaceWidth(BitmapFont font){
        float paddingX;
        try {
            paddingX= (float) Reflex.invokeMethod("getSpaceWidth",font);
        }catch (Exception ex){
            paddingX= (float) Reflex.invokeMethod("getSpaceXadvance", font);
        }
        return paddingX;
    }

    public void setText2(BitmapFont font, CharSequence str, int start, int end, Color color
            , float targetWidth, int halign, boolean wrap, String truncate) {
        if (truncate != null)
            wrap = true; // Causes truncate code to run, doesn't actually cause wrapping.
        //else if (targetWidth <= font.getSpaceXadvance()) //
        else if (targetWidth <= getSpaceWidth(font)) //
            wrap = false; // Avoid one line per character, which is very inefficient.

        BitmapFont.BitmapFontData fontData = font.getData();

        Pool<GlyphRun> glyphRunPool = Pools.get(GlyphRun.class);
        Array<GlyphRun> runs = this.runs;
        glyphRunPool.freeAll(runs);
        runs.clear();

        float x = 0, y = 0, width = 0;
        int lines = 0, blankLines = 0;

        Array<Color> colorStack = this.colorStack;
        Color nextColor = color;
        colorStack.add(color);
        Pool<Color> colorPool = Pools.get(Color.class);

        int runStart = start;
        outer:
        while (true) {
            // Each run is delimited by newline or left square bracket.
            int runEnd = -1;
            boolean newline = false, colorRun = false;
            if (start == end) {
                if (runStart == end) break; // End of string with no run to process, we're done.
                runEnd = end; // End of string, process last run.
            } else {
                switch (str.charAt(start++)) {
                    case '\n':
                        // End of line.
                        runEnd = start - 1;
                        newline = true;
                        break;
//                    case '[':
//                        // Possible color tag.
//                        if (markupEnabled) {
//                            int length = parseColorMarkup(str, start, end, colorPool);
//                            if (length >= 0) {
//                                runEnd = start - 1;
//                                start += length + 1;
//                                nextColor = colorStack.peek();
//                                colorRun = true;
//                            } else if (length == -2) {
//                                start++; // Skip first of "[[" escape sequence.
//                                continue outer;
//                            }
//                        }
//                        break;
                }
            }

            if (runEnd != -1) {
                if (runEnd != runStart) { // Can happen (eg) when a color tag is at text start or a line is "\n".
                    // Store the run that has ended.
                    GlyphRun run = glyphRunPool.obtain();
                    run.color.set(color);
                    run.x = x;
                    run.y = y;
                    //fontData.getGlyphs(run, str, runStart, runEnd, null);
                    //fontData.getGlyphs(run, str, runStart, runEnd, true);
                    try{
                        Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,null);
                    }catch (Exception e){
                        Reflex.invokeMethod("getGlyphs",fontData,run,str,start-1,true);
                    }
                    if (run.glyphs.size == 0)
                        glyphRunPool.free(run);
                    else {
                        runs.add(run);

                        // Compute the run width, wrap if necessary, and position the run.
                        float[] xAdvances = run.xAdvances.items;
                        for (int i = 0, n = run.xAdvances.size; i < n; i++) {
                            float xAdvance = xAdvances[i];
                            x += xAdvance;

                            // Don't wrap if the glyph would fit with just its width (no xadvance or kerning).
                            if (wrap && x > targetWidth && i > 1 && x - xAdvance + (run.glyphs.get(i - 1)
                                    .xoffset + run.glyphs.get(i - 1).width) * fontData.scaleX - 0.0001f > targetWidth) {

                                if (truncate != null) {
                                    truncate(fontData, run, targetWidth, truncate, i, glyphRunPool);
                                    x = run.x + run.width;
                                    break outer;
                                }

                                int wrapIndex = fontData.getWrapIndex(run.glyphs, i);
                                if ((run.x == 0 && wrapIndex == 0) // Require at least one glyph per line.
                                        || wrapIndex >= run.glyphs.size) { // Wrap at least the glyph that didn't fit.
                                    wrapIndex = i - 1;
                                }
                                GlyphRun next;
                                if (wrapIndex == 0) { // No wrap index, move entire run to next line.
                                    next = run;
                                    run.width = 0;
                                    width = Math.max(width, run.x);
                                } else {
                                    next = wrap(fontData, run, glyphRunPool, wrapIndex, i);
                                    runs.add(next);
                                    width = Math.max(width, run.x + run.width);
                                }

                                // Start the loop over with the new run on the next line.
                                x = 0;
                                y += fontData.down;
                                lines++;
                                next.x = 0;
                                next.y = y;
                                i = -1;
                                n = next.xAdvances.size;
                                xAdvances = next.xAdvances.items;
                                run = next;
                            } else
                                run.width += xAdvance;
                        }
                    }
                }

                if (newline) {
                    // Next run will be on the next line.
                    width = Math.max(width, x);
                    x = 0;
                    float down = fontData.down;
                    if (runEnd == runStart) { // Blank line.
                        down *= fontData.blankLineScale;
                        blankLines++;
                    } else
                        lines++;
                    y += down;
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
            while (lineStart < n)
                runs.get(lineStart++).x += shift;
        }

        this.width = width;
        this.height = fontData.capHeight + lines * fontData.lineHeight + blankLines * fontData.lineHeight * fontData.blankLineScale;
    }

    private void truncate(BitmapFont.BitmapFontData fontData, GlyphRun run, float targetWidth, String truncate, int widthIndex,
                          Pool<GlyphRun> glyphRunPool) {

        // Determine truncate string size.
        GlyphRun truncateRun = glyphRunPool.obtain();
        //fontData.getGlyphs(truncateRun, truncate, 0, truncate.length(), null);
        //fontData.getGlyphs(truncateRun, truncate, 0, truncate.length(), true);
        try{
            Reflex.invokeMethod("getGlyphs",fontData,truncateRun, truncate, 0, truncate.length(), null);
        }catch (Exception e){
            Reflex.invokeMethod("getGlyphs",fontData,truncateRun, truncate, 0, truncate.length(), true);
        }
        float truncateWidth = 0;
        for (int i = 1, n = truncateRun.xAdvances.size; i < n; i++)
            truncateWidth += truncateRun.xAdvances.get(i);
        targetWidth -= truncateWidth;

        // Determine visible glyphs.
        int count = 0;
        float width = run.x;
        while (count < run.xAdvances.size) {
            float xAdvance = run.xAdvances.get(count);
            width += xAdvance;
            if (width > targetWidth) {
                run.width = width - run.x - xAdvance;
                break;
            }
            count++;
        }

        if (count > 1) {
            // Some run glyphs fit, append truncate glyphs.
            run.glyphs.truncate(count - 1);
            run.xAdvances.truncate(count);
            adjustLastGlyph(fontData, run);
            if (truncateRun.xAdvances.size > 0)
                run.xAdvances.addAll(truncateRun.xAdvances, 1, truncateRun.xAdvances.size - 1);
        } else {
            // No run glyphs fit, use only truncate glyphs.
            run.glyphs.clear();
            run.xAdvances.clear();
            run.xAdvances.addAll(truncateRun.xAdvances);
            if (truncateRun.xAdvances.size > 0) run.width += truncateRun.xAdvances.get(0);
        }
        run.glyphs.addAll(truncateRun.glyphs);
        run.width += truncateWidth;

        glyphRunPool.free(truncateRun);
    }

    private GlyphRun wrap(BitmapFont.BitmapFontData fontData, GlyphRun first, Pool<GlyphRun> glyphRunPool, int wrapIndex, int widthIndex) {
        GlyphRun second = glyphRunPool.obtain();
        second.color.set(first.color);
        int glyphCount = first.glyphs.size;

        // Increase first run width up to the end index.
        while (widthIndex < wrapIndex)
            first.width += first.xAdvances.get(widthIndex++);

        // Reduce first run width by the wrapped glyphs that have contributed to the width.
        while (widthIndex > wrapIndex + 1)
            first.width -= first.xAdvances.get(--widthIndex);

        // Copy wrapped glyphs and xAdvances to second run.
        // The second run will contain the remaining glyph data, so swap instances rather than copying to reduce large allocations.
        if (wrapIndex < glyphCount) {
            Array<BitmapFont.Glyph> glyphs1 = second.glyphs; // Starts empty.
            Array<BitmapFont.Glyph> glyphs2 = first.glyphs; // Starts with all the glyphs.
            glyphs1.addAll(glyphs2, 0, wrapIndex);
            glyphs2.removeRange(0, wrapIndex - 1);
            first.glyphs = glyphs1;
            second.glyphs = glyphs2;
            // Equivalent to:
            // second.glyphs.addAll(first.glyphs, wrapIndex, glyphCount - wrapIndex);
            // first.glyphs.truncate(wrapIndex);

            FloatArray xAdvances1 = second.xAdvances; // Starts empty.
            FloatArray xAdvances2 = first.xAdvances; // Starts with all the xAdvances.
            xAdvances1.addAll(xAdvances2, 0, wrapIndex + 1);
            xAdvances2.removeRange(1, wrapIndex); // Leave first entry to be overwritten by next line.
            xAdvances2.set(0, -glyphs2.first().xoffset * fontData.scaleX - fontData.padLeft);
            first.xAdvances = xAdvances1;
            second.xAdvances = xAdvances2;
            // Equivalent to:
            // second.xAdvances.add(-second.glyphs.first().xoffset * fontData.scaleX - fontData.padLeft);
            // second.xAdvances.addAll(first.xAdvances, wrapIndex + 1, first.xAdvances.size - (wrapIndex + 1));
            // first.xAdvances.truncate(wrapIndex + 1);
        }

        if (wrapIndex == 0) {
            // If the first run is now empty, remove it.
            glyphRunPool.free(first);
            runs.pop();
        } else
            adjustLastGlyph(fontData, first);

        return second;
    }

    /**
     * Adjusts the xadvance of the last glyph to use its width instead of xadvance.
     */
    private void adjustLastGlyph(BitmapFont.BitmapFontData fontData, GlyphRun run) {
        BitmapFont.Glyph last = run.glyphs.peek();
        if (fontData.isWhitespace((char) last.id)) return; // Can happen when doing truncate.
        float width = (last.xoffset + last.width) * fontData.scaleX - fontData.padRight;
        run.width += width - run.xAdvances.peek(); // Can cause the run width to be > targetWidth, but the problem is minimal.
        run.xAdvances.set(run.xAdvances.size - 1, width);
    }

}
