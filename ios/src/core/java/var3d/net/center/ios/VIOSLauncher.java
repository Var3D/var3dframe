package var3d.net.center.ios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSLocale;
import org.robovm.apple.foundation.NSMutableAttributedString;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.NSAttributedStringAttribute;
import org.robovm.apple.uikit.NSUnderlineStyle;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIEdgeInsets;
import org.robovm.apple.uikit.UIFont;
import org.robovm.apple.uikit.UIGraphics;
import org.robovm.apple.uikit.UIImage;
import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.freefont.FreePaint;
import var3d.net.center.freefont.TTFParser;

public abstract class VIOSLauncher extends IOSApplication.Delegate implements
        VListener {
    @SuppressWarnings("unused")
    private VGame game;

    public void setGame(VGame game) {
        this.game = game;
    }

    private UIColor getColor(Color color) {
        return UIColor.fromRGBA(color.r, color.g, color.b, color.a);
    }

    private HashMap<String, UIFont> fonts;

    public Pixmap getFontPixmap(String strings, FreePaint vpaint) {
        if (fonts == null)
            fonts = new HashMap<String, UIFont>();
        UIFont font = fonts.get(vpaint.getName());
        if (font == null) {
            if (vpaint.getTTFName().equals("")) {
                if (vpaint.getFakeBoldText() || vpaint.getStrokeColor() != null) {
                    font = UIFont.getBoldSystemFont(vpaint.getTextSize());
                } else {
                    font = UIFont.getSystemFont(vpaint.getTextSize());
                }
            } else {
                TTFParser parser = new TTFParser();
                try {
                    parser.parse(NSBundle.getMainBundle().getResourcePath()
                            + "/"
                            + vpaint.getTTFName()
                            + (vpaint.getTTFName().endsWith(".ttf") ? ""
                            : ".ttf"));
                    font = UIFont.getFont(parser.getFontPSName(),
                            vpaint.getTextSize());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fonts.put(vpaint.getName(), font);
        }
        try {
            NSString string = new NSString(strings);
            CGSize dim = string.getSize(font);
            UILabel label = new UILabel(new CGRect(0, 0, dim.getWidth(),
                    dim.getHeight()));
            UILabel label2 = null;// 描边层
            label.setText(strings);
            label.setBackgroundColor(UIColor.fromRGBA(1, 1, 1, 0));
            label.setTextColor(getColor(vpaint.getColor()));
            label.setFont(font);
            label.setOpaque(false);
            label.setAlpha(1);
            NSRange range = new NSRange(0, strings.length());
            NSMutableAttributedString mutableString = new NSMutableAttributedString(
                    strings);
            mutableString.addAttribute(
                    NSAttributedStringAttribute.ForegroundColor,
                    getColor(vpaint.getColor()), range);
            if (vpaint.getStrokeColor() != null) {
                label2 = new UILabel(new CGRect(0, 0, dim.getWidth(),
                        dim.getHeight()));
                label2.setText(strings);
                label2.setBackgroundColor(UIColor.fromRGBA(1, 1, 1, 0));
                label2.setTextColor(getColor(vpaint.getColor()));
                label2.setFont(font);
                label2.setOpaque(false);
                label2.setAlpha(1);
                NSMutableAttributedString mutableString2 = new NSMutableAttributedString(
                        strings);
                mutableString2.addAttribute(
                        NSAttributedStringAttribute.StrokeColor,
                        getColor(vpaint.getStrokeColor()), range);
                mutableString2.addAttribute(
                        NSAttributedStringAttribute.StrokeWidth,
                        NSNumber.valueOf(vpaint.getStrokeWidth()), range);
                label2.setAttributedText(mutableString2);
            } else if (vpaint.getUnderlineText() == true) {
                mutableString.addAttribute(
                        NSAttributedStringAttribute.UnderlineStyle,
                        NSNumber.valueOf(NSUnderlineStyle.StyleSingle.value()),
                        range);
            } else if (vpaint.getStrikeThruText() == true) {
                mutableString
                        .addAttribute(
                                NSAttributedStringAttribute.StrikethroughStyle,
                                NSNumber.valueOf(NSUnderlineStyle.StyleSingle
                                        .value()
                                        | NSUnderlineStyle.PatternSolid.value()),
                                range);
            }
            label.setAttributedText(mutableString);
            UIGraphics.beginImageContext(dim, false, 1);
            label.getLayer().render(UIGraphics.getCurrentContext());
            if (vpaint.getStrokeColor() != null) {
                label2.getLayer().render(UIGraphics.getCurrentContext());
            }
            UIImage image = UIGraphics.getImageFromCurrentImageContext();
            UIGraphics.endImageContext();
            NSData nsData = image.toPNGData();
            Pixmap pixmap = new Pixmap(nsData.getBytes(), 0,
                    nsData.getBytes().length);
            return pixmap;
        } catch (Exception ex) {
            return getFontPixmap(" ", vpaint);
        }
    }

    @Override
    public void esc() {
        Gdx.app.exit();
    }

    @Override
    public void getDiaolog(String msg) {

    }

    @Override
    public void goToShare(String title, String context, String url,
                          byte[] imgByte, final Runnable success, final Runnable failure) {
    }

    @Override
    public void sayGood() {

    }

    @Override
    public void getTopList() {

    }

    public void getTopList(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void log(String txt) {

    }

    @Override
    public void Tost(String msg) {

    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public void startLevel(String level) {

    }

    @Override
    public void failLevel(String level) {

    }

    @Override
    public void finishLevel(String level) {

    }

    @Override
    public void getAdDialog() {

    }

    @Override
    public void openVar3dNet() {

    }

    @Override
    public void openAd(String str) {

    }

    public void openAd(int aglin) {
    }

    public void openAdbig(int aglin) {
    }

    @Override
    public void closeAd() {

    }

    @Override
    public void openFullAd() {

    }

    @Override
    public void onIOSResume() {

    }

    @Override
    public void onIOSPause() {

    }

    @Override
    public String getDeviceId() {
        return "iphone";
    }

    @Override
    public void signUp(String name, String pass) {
    }

    public Array<Object> signUp(Object... obj) {
        return null;
    }

    @Override
    public Locale getLocale() {
        return new Locale(NSLocale.getSystemLocale().toString());
    }

    @Override
    public void openAppShop(String url) {

    }

    @Override
    public void updataScore(String userId, int score) {

    }

    @Override
    public void universalMethod(Object... obj) {

    }

    public void pay(double price, String name, VPayListener listen) {

    }

    public void getBuyList(VShopListener listen) {
    }

    @Override
    public boolean isCanShow() {
        return false;
    }

    @Override
    public void openActivity(Class<?> cls, String name, Object value) {

    }

    @Override
    public void openActivity(Class<?> cls, String[] name, Object[] value) {

    }

    @Override
    public void runOnUiThread(Runnable run) {
        run.run();
    }

    public void Screenshot(VGame game) {
    }

    public void createIcon(VGame game, String path) {
    }

    public void editLanguage(VGame game, String path) {
    }

    public void createScreenshot(VGame game, String path) {
    }

    public void editScreenshot(VGame game, String path) {
    }

    public String getString(String key) {
        return NSString.getLocalizedString(key);
    }

    public Array<Object> intelligentMethod(Object... obj) {
        return null;
    }

    public String getVersionName() {
        return NSBundle.getMainBundle().getInfoDictionary()
                .getString("CFBundleShortVersionString");
    }

    public void openProtect(String... names) {
    }

    public void unProtect(String... names) {
    }

    public void create() {
    }

    public void edit(VStage stage) {
    }

    public void saveUI(VStage stage) {
    }

    @Override
    public void getLineNumber(Actor actor) {
    }

    public void keyDown(int key) {

    }

    public void keyUp(int key) {
    }

    public Vector2 getAppScreenSize(){
        return null;
    }

    //返回安全区域
    private Rectangle rectangle = null;

    public Rectangle getSafeAreaInsets() {
        if (rectangle != null) {
            return rectangle;
        } else if (Foundation.getMajorSystemVersion() < 11) {
            rectangle = new Rectangle();
            return rectangle;
        } else {
            UIView view = UIApplication.getSharedApplication().getKeyWindow().getRootViewController().getView();
            UIEdgeInsets edgeInsets = view.getSafeAreaInsets();
            double top = edgeInsets.getTop() * view.getContentScaleFactor();
            double bottom = edgeInsets.getBottom() * view.getContentScaleFactor();
            double left = edgeInsets.getLeft() * view.getContentScaleFactor();
            double right = edgeInsets.getRight() * view.getContentScaleFactor();
            rectangle = new Rectangle((float) left, (float) bottom, (float) right, (float) top);
            return rectangle;
        }
    }
}
