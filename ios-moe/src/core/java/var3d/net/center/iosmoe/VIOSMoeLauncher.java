package var3d.net.center.iosmoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import org.moe.natj.general.Pointer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSBundle;
import apple.foundation.NSData;
import apple.foundation.NSLocale;
import apple.foundation.NSMutableAttributedString;
import apple.foundation.NSNumber;
import apple.foundation.NSString;
import apple.foundation.c.Foundation;
import apple.foundation.struct.NSRange;
import apple.uikit.UIApplication;
import apple.uikit.UIColor;
import apple.uikit.UIFont;
import apple.uikit.UIImage;
import apple.uikit.UILabel;
import apple.uikit.UIView;
import apple.uikit.c.UIKit;
import apple.uikit.enums.NSUnderlineStyle;
import apple.uikit.struct.UIEdgeInsets;
import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.freefont.FreePaint;
import var3d.net.center.freefont.TTFParser;

/**
 * Created by white on 2018/2/19.
 */

public abstract class VIOSMoeLauncher extends IOSApplication.Delegate implements VListener {
    protected VIOSMoeLauncher(Pointer peer) {
        super(peer);
    }

    @SuppressWarnings("unused")
    private VGame game;

    public void setGame(VGame game) {
        this.game = game;
    }

    private UIColor getColor(Color color) {
        return UIColor.colorWithDisplayP3RedGreenBlueAlpha(color.r, color.g, color.b, color.a);
    }

    private HashMap<String, UIFont> fonts;

    public Pixmap getFontPixmap(String strings, FreePaint vpaint) {
        if (fonts == null)
            fonts = new HashMap<String, UIFont>();
        UIFont font = fonts.get(vpaint.getName());
        if (font == null) {
            if (vpaint.getTTFName().equals("")) {
                if (vpaint.getFakeBoldText() || vpaint.getStrokeColor() != null) {
                    font = UIFont.boldSystemFontOfSize(vpaint.getTextSize());
                } else {
                    font = UIFont.systemFontOfSize(vpaint.getTextSize());
                }
            } else {
                TTFParser parser = new TTFParser();
                try {
                    parser.parse(NSBundle.mainBundle().resourcePath()
                            + "/"
                            + vpaint.getTTFName()
                            + (vpaint.getTTFName().endsWith(".ttf") ? ""
                            : ".ttf"));
                    font = UIFont.fontWithNameSize(parser.getFontPSName(),
                            vpaint.getTextSize());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fonts.put(vpaint.getName(), font);
        }
        try {
            NSString string = NSString.stringWithString(strings);
            CGSize dim = string.sizeWithFont(font);
            UILabel label = UILabel.alloc()
                    .initWithFrame(new CGRect(new CGPoint(0, 0), new CGSize(dim.width(), dim.height())));
            UILabel label2 = null;// 描边层
            label.setText(strings);
            label.setBackgroundColor(UIColor.colorWithRedGreenBlueAlpha(1, 1, 1, 0));
            label.setTextColor(getColor(vpaint.getColor()));
            label.setFont(font);
            label.setOpaque(false);
            label.setAlpha(1);
            NSRange range = new NSRange(0, strings.length());
            NSMutableAttributedString mutableString = NSMutableAttributedString.alloc().initWithString(
                    strings);

            mutableString.addAttributeValueRange("ForegroundColor", getColor(vpaint.getColor()), range);
            if (vpaint.getStrokeColor() != null) {
                label2 = UILabel.alloc()
                        .initWithFrame(new CGRect(new CGPoint(0, 0), new CGSize(dim.width(), dim.height())));
                label2.setText(strings);
                label2.setBackgroundColor(UIColor.colorWithRedGreenBlueAlpha(1, 1, 1, 0));
                label2.setTextColor(getColor(vpaint.getColor()));
                label2.setFont(font);
                label2.setOpaque(false);
                label2.setAlpha(1);
                NSMutableAttributedString mutableString2 = NSMutableAttributedString.alloc().initWithString(
                        strings);
                mutableString2.addAttributeValueRange("StrokeColor", getColor(vpaint.getStrokeColor()), range);
                mutableString2.addAttributeValueRange("StrokeWidth", NSNumber.numberWithInt(vpaint.getStrokeWidth()), range);
                label2.setAttributedText(mutableString2);
            } else if (vpaint.getUnderlineText() == true) {
                mutableString.addAttributeValueRange("UnderlineStyle", NSNumber.numberWithLong(NSUnderlineStyle.StyleSingle), range);
            } else if (vpaint.getStrikeThruText() == true) {
                mutableString.
                        addAttributeValueRange("StrikethroughStyle",
                                NSNumber.numberWithLong(NSUnderlineStyle.StyleSingle | NSUnderlineStyle.PatternSolid), range);

            }
            label.setAttributedText(mutableString);
            UIKit.UIGraphicsBeginImageContext(dim);
            label.layer().renderInContext(UIKit.UIGraphicsGetCurrentContext());
            if (vpaint.getStrokeColor() != null) {
                label2.layer().renderInContext(UIKit.UIGraphicsGetCurrentContext());
            }
            UIImage image = UIKit.UIGraphicsGetImageFromCurrentImageContext();
            UIKit.UIGraphicsEndImageContext();
            NSData nsData = UIKit.UIImagePNGRepresentation(image);
            byte[] types = nsData.bytes().getBytePtr().toByteArray((int) nsData.length());

            return new Pixmap(types, 0, types.length);
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
        return new Locale(NSLocale.systemLocale().toString());
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
        return NSBundle.mainBundle().localizedStringForKeyValueTable(key, "", (String) null);
    }

    public Array<Object> intelligentMethod(Object... obj) {
        return null;
    }

    public String getVersionName() {
        return (String) NSBundle.mainBundle().infoDictionary().get("CFBundleShortVersionString");
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

    public Vector2 getAppScreenSize() {
        return null;
    }

    //返回安全区域
    private Rectangle rectangle = new Rectangle();

    public Rectangle getSafeAreaInsets() {
        if (Foundation.NSFoundationVersionNumber() < 11) {
            return rectangle;
        } else {
            UIView view = UIApplication.sharedApplication().keyWindow().rootViewController().view();
            UIEdgeInsets edgeInsets = view.safeAreaInsets();
            double top = edgeInsets.top() * view.contentScaleFactor();
            double bottom = edgeInsets.bottom() * view.contentScaleFactor();
            double left = edgeInsets.left() * view.contentScaleFactor();
            double right = edgeInsets.right() * view.contentScaleFactor();
            rectangle.set((float) left, (float) bottom, (float) right, (float) top);
            return rectangle;
        }
    }
}
