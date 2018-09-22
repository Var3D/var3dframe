package var3d.net.center.iosmoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;

import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.ByValue;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Property;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSAttributedString;
import apple.foundation.NSBundle;
import apple.foundation.NSData;
import apple.foundation.NSDictionary;
import apple.foundation.NSLocale;
import apple.foundation.NSMutableAttributedString;
import apple.foundation.NSNotification;
import apple.foundation.NSNotificationCenter;
import apple.foundation.NSNumber;
import apple.foundation.NSString;
import apple.foundation.NSValue;
import apple.foundation.c.Foundation;
import apple.foundation.struct.NSRange;
import apple.uikit.ITargetAction;
import apple.uikit.UIApplication;
import apple.uikit.UIColor;
import apple.uikit.UIControl;
import apple.uikit.UIDevice;
import apple.uikit.UIFont;
import apple.uikit.UIImage;
import apple.uikit.UILabel;
import apple.uikit.UIScreen;
import apple.uikit.UITextField;
import apple.uikit.UIView;
import apple.uikit.c.UIKit;
import apple.uikit.enums.NSTextAlignment;
import apple.uikit.enums.NSUnderlineStyle;
import apple.uikit.enums.UIControlEvents;
import apple.uikit.enums.UIKeyboardType;
import apple.uikit.enums.UIReturnKeyType;
import apple.uikit.enums.UITextAutocapitalizationType;
import apple.uikit.enums.UITextAutocorrectionType;
import apple.uikit.enums.UITextBorderStyle;
import apple.uikit.enums.UITextSpellCheckingType;
import apple.uikit.struct.UIEdgeInsets;
import var3d.net.center.NativeTextField;
import var3d.net.center.UI;
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

    public void showAchievements(){};

    public void updataAchievements(String identifier, double percentComplete){};

    public void showChallenges(){};

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

    public void openAd(String str, Object... objects){

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
        return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //返回安全区域
    private Rectangle rectangle = new Rectangle();

    public Rectangle getSafeAreaInsets() {
        if (getMajorSystemVersion() < 11) {
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

    /**
     * Retrieve and store the device system version.
     */
    public int getMajorSystemVersion() {
        String version = UIDevice.currentDevice().systemVersion();
        int majorSystemVersion = 0;
        if (version != null) {
            String[] parts = version.split("\\.");
            if (parts.length > 0) majorSystemVersion = Integer.valueOf(parts[0]);
        } else {
            majorSystemVersion = 6;
        }
        return majorSystemVersion;
    }

    public Pixmap getIphoneXPixmap(String name) {
        return null;
    }

    private HashMap<NativeTextField,VMoeUITextField> textFieldHashMap;
    private CGRect cgRect=new CGRect();
    private CGSize screenSize;


    private Pool<VMoeUITextField> pool_textFields=new Pool<VMoeUITextField>() {
        @Override
        protected VMoeUITextField newObject() {
            cgRect.origin().setX(0);
            cgRect.origin().setY(0);
            cgRect.size().setWidth(100);
            cgRect.size().setHeight(100);
            VMoeUITextField textfield = VMoeUITextField.initWithListener(VIOSMoeLauncher.this,cgRect);
            textfield.setKeyboardType(UIKeyboardType.Default);
            textfield.setReturnKeyType(UIReturnKeyType.Done);
            textfield.setAutocapitalizationType(UITextAutocapitalizationType.None);
            textfield.setAutocorrectionType(UITextAutocorrectionType.No);
            textfield.setSpellCheckingType(UITextSpellCheckingType.No);
            textfield.setBorderStyle(UITextBorderStyle.RoundedRect);
            return textfield;
        }
    };


    public void linkNativeTextField(final NativeTextField nativeTextField, NativeTextField.Method method){
        switch (method){
            case newObject:
                if(textFieldHashMap==null){
                    textFieldHashMap=new HashMap<>();
                    screenSize= UIScreen.mainScreen().bounds().size();
                }
                VMoeUITextField textfield = pool_textFields.obtain();
                textfield.setLibgdxTextField(nativeTextField);
                textFieldHashMap.put(nativeTextField,textfield);
                textfield.setText("");
                textfield.setSecureTextEntry(false);
                ((IOSApplication)Gdx.app).getUIViewController().view().addSubview(textfield);
                break;
            case setTextFieldListener:
                textfield = textFieldHashMap.get(nativeTextField);
                final VMoeUITextField finalTextfield = textfield;
                UITextListener editBeginAction = new UITextListener(UIControlEvents.EditingDidBegin) {
                    @Override
                    public void onEvent(Object source, long event) {
                            finalTextfield.setText(nativeTextField.getText());
                            nativeTextField.getTextFieldListener().didBeginEditing(nativeTextField);

                            finalTextfield.registered();
                    }
                };
                textfield.addTargetActionForControlEvents(editBeginAction, UIControlEvents.EditingDidBegin);
                nativeTextField.addNativeListener(editBeginAction);


                UITextListener editEndAction = new UITextListener(UIControlEvents.EditingDidEnd) {
                    @Override
                    public void onEvent(Object source, long event) {
                        nativeTextField.setText(finalTextfield.text());
                        nativeTextField.getTextFieldListener().didEndEditing(nativeTextField);

                        finalTextfield.synchronousPosition();

                        finalTextfield.removeRegistered();
                    }
                };
                textfield.addTargetActionForControlEvents(editEndAction, UIControlEvents.EditingDidEnd);
                nativeTextField.addNativeListener(editEndAction);



                UITextListener primaryAction = new UITextListener(UIControlEvents.PrimaryActionTriggered) {
                    @Override
                    public void onEvent(Object source, long event) {
                        boolean isResignFirstResponder=nativeTextField.getTextFieldListener()
                                .shouldReturn(nativeTextField);
                        if(isResignFirstResponder){
                            finalTextfield.resignFirstResponder();
                            finalTextfield.synchronousPosition();
                            finalTextfield.removeRegistered();
                        }
                    }
                };
                textfield.addTargetActionForControlEvents(primaryAction, UIControlEvents.PrimaryActionTriggered);
                nativeTextField.addNativeListener(primaryAction);



                UITextListener EditingChangedAction = new UITextListener(UIControlEvents.EditingChanged) {
                    @Override
                    public void onEvent(Object source, long event) {
                        nativeTextField.setText(finalTextfield.text());
                        String text=nativeTextField.getTextFieldListener().onEditingChanged(nativeTextField);
                        if(text!=null) {
                            finalTextfield.setText(text);
                            nativeTextField.setText(text);
                        }
                    }
                };
                textfield.addTargetActionForControlEvents(EditingChangedAction, UIControlEvents.EditingChanged);
                nativeTextField.addNativeListener(EditingChangedAction);
                break;
            case becomeFirstResponder:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.registered();
                textfield.becomeFirstResponder();
                break;
            case resignFirstResponder:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.removeRegistered();
                textfield.resignFirstResponder();
                break;
            case setText:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setText(nativeTextField.getText());
                break;
            case setVisible:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setHidden(!nativeTextField.isVisible());
                break;
            case setHidden:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setHidden(nativeTextField.isHidden());
                break;
            case setBorderStyle:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setBorderStyle((nativeTextField.getBorderStyle().value()));
                break;
            case setBackgroundColor:
                textfield = textFieldHashMap.get(nativeTextField);
                Color color=nativeTextField.getColor();
                textfield.setBackgroundColor(UIColor.colorWithRedGreenBlueAlpha(color.r,color.g,color.b,color.a));
                break;
            case setReturnKeyType:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setReturnKeyType((nativeTextField.getReturnKeyType().value()));
                break;
            case setPasswordMode:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setSecureTextEntry(nativeTextField.isPasswordMode());
                break;
            case setMessageText:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setPlaceholder(nativeTextField.getMessageText());
                break;
            case setTintColor:
                textfield = textFieldHashMap.get(nativeTextField);
                color=nativeTextField.getTintColor();
                textfield.setTintColor(UIColor.colorWithRedGreenBlueAlpha(color.r,color.g,color.b,color.a));
                break;
            case setMessageColor:
                textfield = textFieldHashMap.get(nativeTextField);
                color=nativeTextField.getMessageColor();

                NSDictionary<?,?> arr = NSDictionary.dictionaryWithObjectsAndKeys(
                        UIColor.colorWithRedGreenBlueAlpha(color.r,color.g,color.b,color.a),
                        "NSForegroundColorAttributeName",
                        textfield.font(),
                        "NSFontAttributeName"

                );

                NSAttributedString attrString =NSAttributedString.alloc()
                        .initWithStringAttributes(nativeTextField.getMessageText(), (NSDictionary<String, ?>) arr);
                textfield.setAttributedPlaceholder(attrString);
                break;
            case setFontColor:
                textfield = textFieldHashMap.get(nativeTextField);
                color=nativeTextField.getFontColor();
                textfield.setTextColor(UIColor.colorWithRedGreenBlueAlpha(color.r,color.g,color.b,color.a));
                break;
            case setKeyboardType:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setKeyboardType((nativeTextField.getKeyboardType().value()));
                break;
            case setAlignment:
                textfield = textFieldHashMap.get(nativeTextField);
                switch (nativeTextField.getAlignment()){
                    case Align.left:
                        textfield.setTextAlignment(NSTextAlignment.Left);
                        break;
                    case Align.bottomLeft:
                        textfield.setTextAlignment(NSTextAlignment.Left);
                        break;
                    case Align.topLeft:
                        textfield.setTextAlignment(NSTextAlignment.Left);
                        break;
                    case Align.right:
                        textfield.setTextAlignment(NSTextAlignment.Right);
                        break;
                    case Align.bottomRight:
                        textfield.setTextAlignment(NSTextAlignment.Right);
                        break;
                    case Align.topRight:
                        textfield.setTextAlignment(NSTextAlignment.Right);
                        break;
                    case Align.top:
                        textfield.setTextAlignment(NSTextAlignment.Center);
                        break;
                    case Align.center:
                        textfield.setTextAlignment(NSTextAlignment.Center);
                        break;
                    case Align.bottom:
                        textfield.setTextAlignment(NSTextAlignment.Center);
                        break;
                }
                break;
            case setFontSize:
                textfield = textFieldHashMap.get(nativeTextField);
                float fontSize=nativeTextField.getFontSize();
                if(fontSize==0)return;
                if(nativeTextField.getStage()!=null) {
                    Stage stage = nativeTextField.getStage();
                    float blx;
                    float fullWidth;
                    if (stage instanceof VStage) {
                        VStage vStage = (VStage) stage;
                        fullWidth = vStage.getFullWidth();
                    } else {
                        fullWidth = stage.getWidth();
                    }
                    blx = (float) (1f / fullWidth * screenSize.width());
                    textfield.setFont(UIFont.boldSystemFontOfSize(nativeTextField.getFontSize()*blx));
                }
                break;
            case positionChanged:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.synchronousPosition();
                break;
            case sizeChanged:
                textfield = textFieldHashMap.get(nativeTextField);
                if(nativeTextField.getStage()!=null) {
                    Stage stage = nativeTextField.getStage();
                    float w,h,x,y,blx,bly;
                    float fullWidth,fullHeight;
                    if(stage instanceof VStage){
                        VStage vStage= (VStage) stage;
                        fullWidth=vStage.getFullWidth();
                        fullHeight=vStage.getFullHeight();
                    }else {
                        fullWidth=stage.getWidth();
                        fullHeight=stage.getHeight();
                    }
                    blx= (float) (1f/fullWidth*screenSize.width());
                    bly= (float) (1f/fullHeight*screenSize.height());
                    w=nativeTextField.getWidth()*blx;
                    h=nativeTextField.getHeight()*bly;
                    System.out.print("w " + w + "  h  " + h);
                    x= (float) textfield.frame().origin().x();
                    y= (float) textfield.frame().origin().y();
                    CGSize size = cgRect.size();
                    size.setWidth(w);
                    size.setHeight(h);
                    cgRect.setSize(size);

                    CGPoint point = cgRect.origin();
                    point.setY(y);
                    point.setX(x);
                    cgRect.setOrigin(point);
                    textfield.setFrame(cgRect);
                }else {
                    textfield.setHidden(true);
                }
                break;
            case remove:
                textfield = textFieldHashMap.get(nativeTextField);
                Array<Object> nativeAllListener=nativeTextField.getAllNativeListener();
                for(Object object:nativeAllListener){
                    if(object instanceof UITextListener){
                        UITextListener textListener = (UITextListener) object;
                        textfield.removeTargetActionForControlEvents(textListener,textListener.event);
                    }



                }
                nativeAllListener.clear();
                textfield.removeFromSuperview();
                pool_textFields.free(textfield);
                textFieldHashMap.remove(textfield);
                break;
        }
    }


}
