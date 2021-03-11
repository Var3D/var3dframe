package var3d.net.center;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;

public class VButton extends Button {

    public VButton(@Null Drawable up) {
        super(up);
    }

    public VButton(@Null Drawable up, @Null Drawable down) {
        super(up, down);
    }

    public VButton(@Null Drawable up, @Null Drawable down, @Null Drawable checked) {
        super(up, down, checked);
    }

    public <T extends Actor> Cell<T> add(@Null T actor) {
        if (actor instanceof Label) {
            Label lab_font = (Label) actor;
            float labelWidth = lab_font.getPrefWidth();
            float prefFontScale = lab_font.getFontScaleX();
            String string = String.valueOf(lab_font.getText());
            if (labelWidth > getWidth()) {//如果字符串比按钮宽
                if (string.indexOf(" ") == -1) {//如果字符串里不含空格
                    lab_font.setFontScale((getWidth() * 0.95f * prefFontScale) / labelWidth);
                } else {//如果字符串里含有空格，则将空格替换成换行符
                    String[] strings = string.split(" ");
                    int lenthMax = 0;//计算单词最大字符数
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (s.length() > lenthMax) lenthMax = s.length();
                    }
                    String out = "";
                    int len = 0;
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i].trim();
                        len += s.length();
                        int nextLen = 0;
                        if (i < strings.length - 1) {
                            nextLen = strings[i + 1].length();
                        }
                        if (len + nextLen >= lenthMax) {
                            if (i < strings.length - 1) {
                                out += s + "\n";
                                len = 0;
                            } else {
                                out += s;
                            }
                        } else {
                            out += s + " ";
                        }
                    }
                    lab_font.setAlignment(Align.center);
                    lab_font.setText(out);
                    labelWidth = lab_font.getPrefWidth();
                    float labelHeight = lab_font.getPrefHeight();
                    float sx = (getWidth() * 0.95f * prefFontScale) / labelWidth;
                    float sy = (getHeight() * 0.95f * prefFontScale) / labelHeight;
                    if (sx < sy) {
                        lab_font.setFontScale(sx);
                    } else {
                        lab_font.setFontScale(sy);
                    }
                }
            }
            return (Cell<T>) super.add(lab_font);
        } else
            return super.add(actor);
    }

}
