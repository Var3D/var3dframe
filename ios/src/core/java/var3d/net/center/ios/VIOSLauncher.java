package var3d.net.center.ios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSInput;
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

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAttributedString;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSLocale;
import org.robovm.apple.foundation.NSMutableAttributedString;
import org.robovm.apple.foundation.NSNotification;
import org.robovm.apple.foundation.NSNotificationCenter;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.foundation.NSValue;
import org.robovm.apple.storekit.SKStoreReviewController;
import org.robovm.apple.uikit.NSAttributedStringAttribute;
import org.robovm.apple.uikit.NSTextAlignment;
import org.robovm.apple.uikit.NSValueExtensions;
import org.robovm.apple.uikit.UIActivityType;
import org.robovm.apple.uikit.UIActivityViewController;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIControl;
import org.robovm.apple.uikit.UIDevice;
import org.robovm.apple.uikit.UIEdgeInsets;
import org.robovm.apple.uikit.UIFont;
import org.robovm.apple.uikit.UIGraphics;
import org.robovm.apple.uikit.UIImage;
import org.robovm.apple.uikit.UIKeyboardAnimation;
import org.robovm.apple.uikit.UIKeyboardType;
import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIPopoverArrowDirection;
import org.robovm.apple.uikit.UIPopoverController;
import org.robovm.apple.uikit.UIReturnKeyType;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UITextAutocapitalizationType;
import org.robovm.apple.uikit.UITextAutocorrectionType;
import org.robovm.apple.uikit.UITextBorderStyle;
import org.robovm.apple.uikit.UITextField;
import org.robovm.apple.uikit.UITextFieldDelegate;
import org.robovm.apple.uikit.UITextFieldDelegateAdapter;
import org.robovm.apple.uikit.UITextFieldDidEndEditingReason;
import org.robovm.apple.uikit.UITextSpellCheckingType;
import org.robovm.apple.uikit.UIUserInterfaceIdiom;
import org.robovm.apple.uikit.UIView;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.Property;
import org.robovm.objc.block.VoidBlock4;
import org.robovm.rt.bro.annotation.ByVal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import var3d.net.center.NativeTextField;
import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VListenerOnKeyboardChange;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.VTextField;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;
import var3d.net.center.freefont.TTFParser;

import static org.robovm.apple.uikit.UIControl.*;

public abstract class VIOSLauncher extends IOSApplication.Delegate implements
        VListener {
    @SuppressWarnings("unused")
    private VGame game;

    public void setGame(VGame game) {
        this.game = game;
    }

    public VGame getGame(){
        return game;
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
            UILabel label = new UILabel(new CGRect(0, 0, dim.getWidth(), dim.getHeight()));
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
//                mutableString.addAttribute(
//                        NSAttributedStringAttribute.UnderlineStyle,
//                        NSNumber.valueOf(NSUnderlineStyle.StyleSingle.value()),
//                        range);
            } else if (vpaint.getStrikeThruText() == true) {
//                mutableString
//                        .addAttribute(
//                                NSAttributedStringAttribute.StrikethroughStyle,
//                                NSNumber.valueOf(NSUnderlineStyle.StyleSingle
//                                        .value()
//                                        | NSUnderlineStyle.PatternSolid.value()),
//                                range);
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
    public void sayGood() {

    }

    public void showFiveStarDialog(){
        SKStoreReviewController.requestReview();
    }

    @Override
    public void getTopList() {

    }

    public void getTopList(String id) {
        // TODO Auto-generated method stub

    }

    //显示成就
    public void showAchievements(){

    }

    //上传成就
    public void updataAchievements(String identifier, double percentComplete){

    }

    //显示挑战
    public void showChallenges(){

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
        return NSBundle.getMainBundle().getInfoDictionary().getString("CFBundleShortVersionString");
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
        return new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    //返回安全区域
    private Rectangle rectangle = new Rectangle();

    public Rectangle getSafeAreaInsets() {
       if (Foundation.getMajorSystemVersion() < 11) {
            return rectangle;
        } else {
            UIView view = UIApplication.getSharedApplication().getKeyWindow().getRootViewController().getView();
            UIEdgeInsets edgeInsets = view.getSafeAreaInsets();
            double top = edgeInsets.getTop() * view.getContentScaleFactor();
            double bottom = edgeInsets.getBottom() * view.getContentScaleFactor();
            double left = edgeInsets.getLeft() * view.getContentScaleFactor();
            double right = edgeInsets.getRight() * view.getContentScaleFactor();
            rectangle.set((float) left, (float) bottom, (float) right, (float) top);
            return rectangle;
        }
    }

    public Pixmap getIphoneXPixmap(String name) {
        return null;
    }


    //原生输入框
    private HashMap<NativeTextField,VUITextField> textFieldHashMap;
    private CGRect cgRect=new CGRect();
    private CGSize screenSize;

    public CGSize getScreenSize(){
        if(screenSize==null)screenSize=UIScreen.getMainScreen().getBounds().getSize();
        return screenSize;
    }

    public class VUITextField extends UITextField implements Pool.Poolable{
        private NativeTextField nativeTextField;

        public VUITextField(CGRect frame){
            super(frame);
        }

        public void setLibgdxTextField(NativeTextField nativeTextField){
            this.nativeTextField=nativeTextField;
        }


        @Property(
                selector = "changeTextFieldFrame"
        )
        public void changeTextFieldFrame(final NSNotification aNotification){
            final NSDictionary userInfo = aNotification.getUserInfo();
            //NSValue fValue = (NSValue) userInfo.get("UIKeyboardAnimationDurationUserInfoKey");
            NSValue aValue = (NSValue) userInfo.get("UIKeyboardFrameEndUserInfoKey");
            CGRect keyboardRect =aValue.rectValue();
            CGRect frame = getFrame();
            double keyboardHeight= getScreenSize().getHeight()-keyboardRect.getHeight();
            final Stage stage = nativeTextField.getStage();
            //键盘高度转换为 libgdx 坐标系
            final float libgdxKeyboardHeight,bly;
            if(stage!=null) {
                float fullHeight = stage instanceof VStage ? ((VStage) stage).getFullHeight() : stage.getHeight();
                bly= (float) (1f/getScreenSize().getHeight() * fullHeight);
                libgdxKeyboardHeight = (float) (keyboardRect.getHeight() *bly);
            }else{
                return;
            }

            if(frame.getY()+frame.getHeight()> keyboardHeight) {
             switch (nativeTextField.getAdaptKeyboardType()){
                    case Self:
                        frame.setY(keyboardHeight-frame.getHeight());
                        setFrame(frame);
                        break;
             }
            }
            if(nativeTextField.getTextFieldListener()!=null) {
                nativeTextField.getTextFieldListener().keyboardWillShow(nativeTextField, libgdxKeyboardHeight);
            }
        }


        private void synchronizeAllForStage(Group root){
            SnapshotArray<Actor> children= root.getChildren();
            for(Actor actor:children){
                if(actor instanceof NativeTextField){
                    NativeTextField son=(NativeTextField)actor;
                    linkNativeTextField(son, NativeTextField.Method.positionChanged);
                }
                if(actor instanceof Group)synchronizeAllForStage((Group)actor);
            }
        }

        @Property(
                selector = "keyboardWillHide"
        )
        public void keyboardWillHide(NSNotification aNotification) {
            switch (nativeTextField.getAdaptKeyboardType()){
                case Self:
                    synchronousPosition();
                    break;
            }
        }


        //注册通知，以便获取键盘高度
        private boolean isRegistered=false;
        public void registered(){
            if(isRegistered)return;
            isRegistered=true;
            NSNotificationCenter defaultCenter=NSNotificationCenter.getDefaultCenter();

            defaultCenter.addObserver(this, Selector.register("changeTextFieldFrame")
                    , "UIKeyboardWillChangeFrameNotification", null);

            //注册当关闭键盘时执行
            defaultCenter.addObserver(this, Selector.register("keyboardWillHide")
                    , "UIKeyboardWillHideNotification", null);
        }

        //移出注册
        public void removeRegistered(){
            isRegistered=false;
            NSNotificationCenter defaultCenter=NSNotificationCenter.getDefaultCenter();
            defaultCenter.removeObserver(this);
        }


        //本地输入框同步为 libgdx 端坐标
        public void synchronousPosition(){
            if(nativeTextField.getStage()!=null) {
                Stage stage = nativeTextField.getStage();
                float w,h,x,y,blx,bly;
                float fullWidth,fullHeight,cutWidth=0,cutHeight=0;
                if(stage instanceof VStage){
                    VStage vStage= (VStage) stage;
                    fullWidth=vStage.getFullWidth();
                    fullHeight=vStage.getFullHeight();
                    cutWidth=vStage.getCutWidth();
                    cutHeight=vStage.getCutHeight();
                }else {
                    fullWidth=stage.getWidth();
                    fullHeight=stage.getHeight();
                }
                blx= (float) (1f/fullWidth*getScreenSize().getWidth());
                bly= (float) (1f/fullHeight*getScreenSize().getHeight());
                w=(float) getFrame().getWidth();
                h= (float) getFrame().getHeight();
                float fx=nativeTextField.getX();
                float fy=nativeTextField.getY();
                Group father=nativeTextField.getParent();
                Group root=stage.getRoot();
                //float dx=root.getX()-cutWidth;
                float dx=root.getX()/root.getScaleX()-cutWidth;//此处存有疑问，待测
                float dy=root.getY()/root.getScaleY()-cutHeight;
                fx+=dx;
                fy+=dy;
                while(father!=root){
                    Group nextFather=father.getParent();
                    fx+=father.getX();
                    fy+=father.getY();
                    father=nextFather;
                    if(father==null){
                        setHidden(true);
                        return;
                    }
                }
                x=(cutWidth+fx)*blx;
                float my=(cutHeight+fy)*bly;
                y= (float) (getScreenSize().getHeight()-h)-my;
                cgRect.setWidth(w);
                cgRect.setHeight(h);
                cgRect.setX(x);
                cgRect.setY(y);
                setFrame(cgRect);
            }else setHidden(true);
        }

        @Override
        public void reset() {
            if(isRegistered) {
                removeRegistered();
            }
        }
    }

    private Pool<VUITextField> pool_textFields=new Pool<VUITextField>() {
        @Override
        protected VUITextField newObject() {
            cgRect.setX(0);
            cgRect.setY(0);
            cgRect.setWidth(100);
            cgRect.setHeight(100);
            VUITextField textfield = new VUITextField(cgRect);
            textfield.setKeyboardType(UIKeyboardType.Default);
            textfield.setReturnKeyType(UIReturnKeyType.Done);
            textfield.setAutocapitalizationType(UITextAutocapitalizationType.None);//设置自动大写类型
            textfield.setAutocorrectionType(UITextAutocorrectionType.No);//设置自动更正类型
            textfield.setSpellCheckingType(UITextSpellCheckingType.No);//设置拼写检查类型
            textfield.setBorderStyle(UITextBorderStyle.RoundedRect);
            return textfield;
        }
    };


    public void linkNativeTextField(final NativeTextField nativeTextField, NativeTextField.Method method){
        switch (method){
            case newObject:
                if(textFieldHashMap==null){
                    textFieldHashMap=new HashMap<>();
                }
                VUITextField textfield = pool_textFields.obtain();
                textfield.setLibgdxTextField(nativeTextField);
                textFieldHashMap.put(nativeTextField,textfield);
                textfield.setText("");
                textfield.setSecureTextEntry(false);
                ((IOSApplication)Gdx.app).getUIViewController().getView().addSubview(textfield);
                break;
            case setTextFieldListener:
                textfield = textFieldHashMap.get(nativeTextField);
                final VUITextField finalTextfield = textfield;
                OnEditingDidBeginListener onEditingDidBeginListener;
                textfield.addOnEditingDidBegin(onEditingDidBeginListener=new OnEditingDidBeginListener() {
                    @Override
                    public void onEditingDidBegin(UIControl uiControl) {
                        //System.out.println("onEditingDidBegin");
                        finalTextfield.setText(nativeTextField.getText());
                        nativeTextField.getTextFieldListener().didBeginEditing(nativeTextField);

                        finalTextfield.registered();
                    }
                });
                nativeTextField.addNativeListener(onEditingDidBeginListener);
                OnEditingDidEndListener onEditingDidEndListener;
                textfield.addOnEditingDidEndListener(onEditingDidEndListener=new OnEditingDidEndListener() {
                    @Override
                    public void onEditingDidEnd(UIControl uiControl) {
                        //System.out.println("onEditingDidEnd");
                        nativeTextField.setText(finalTextfield.getText());
                        nativeTextField.getTextFieldListener().didEndEditing(nativeTextField);

                        finalTextfield.synchronousPosition();

                        finalTextfield.removeRegistered();
                    }
                });
                nativeTextField.addNativeListener(onEditingDidEndListener);
                OnPrimaryActionTriggeredListener onPrimaryActionTriggeredListener;
                textfield.addOnPrimaryActionTriggeredListener(onPrimaryActionTriggeredListener=new OnPrimaryActionTriggeredListener() {
                    @Override
                    public void onPrimaryActionTriggered(UIControl uiControl) {
                        //System.out.println("onPrimaryActionTriggered");
                        boolean isResignFirstResponder=nativeTextField.getTextFieldListener()
                                .shouldReturn(nativeTextField);
                        if(isResignFirstResponder){
                            finalTextfield.resignFirstResponder();
                            finalTextfield.synchronousPosition();
                            finalTextfield.removeRegistered();
                        }
                    }
                });
                nativeTextField.addNativeListener(onPrimaryActionTriggeredListener);
                OnEditingChangedListener onEditingChangedListener;
                textfield.addOnEditingChangedListener(onEditingChangedListener=new OnEditingChangedListener() {
                    @Override
                    public void onEditingChanged(UIControl uiControl) {
                        //System.out.println("onEditingChanged");
                        nativeTextField.setText(finalTextfield.getText());
                        String text=nativeTextField.getTextFieldListener().onEditingChanged(nativeTextField);
                        if(text!=null) {
                            finalTextfield.setText(text);
                            nativeTextField.setText(text);
                        }
                    }
                });
                nativeTextField.addNativeListener(onEditingChangedListener);
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
                textfield.setBorderStyle(UITextBorderStyle.valueOf(nativeTextField.getBorderStyle().value()));
                break;
            case setBackgroundColor:
                textfield = textFieldHashMap.get(nativeTextField);
                Color color=nativeTextField.getColor();
                textfield.setBackgroundColor(new UIColor(color.r,color.g,color.b,color.a));
                break;
            case setReturnKeyType:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setReturnKeyType(UIReturnKeyType.valueOf(nativeTextField.getReturnKeyType().value()));
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
                textfield.setTintColor(new UIColor(color.r,color.g,color.b,color.a));
                break;
            case setMessageColor:
                textfield = textFieldHashMap.get(nativeTextField);
                color=nativeTextField.getMessageColor();
                NSDictionary<NSString, ?> arr=new NSDictionary<>(NSAttributedStringAttribute.ForegroundColor.value()
                        ,new UIColor(color.r,color.g,color.b,color.a),NSAttributedStringAttribute.Font.value()
                        ,textfield.getFont());
                NSAttributedString attrString =new NSAttributedString(nativeTextField.getMessageText(),arr);
                textfield.setAttributedPlaceholder(attrString);
                break;
            case setFontColor:
                textfield = textFieldHashMap.get(nativeTextField);
                color=nativeTextField.getFontColor();
                textfield.setTextColor(new UIColor(color.r,color.g,color.b,color.a));
                break;
            case setKeyboardType:
                textfield = textFieldHashMap.get(nativeTextField);
                textfield.setKeyboardType(UIKeyboardType.valueOf(nativeTextField.getKeyboardType().value()));
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
                    blx = (float) (1f / fullWidth * getScreenSize().getWidth());
                    textfield.setFont(UIFont.getSystemFont(nativeTextField.getFontSize()*blx));
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
                    blx= (float) (1f/fullWidth*getScreenSize().getWidth());
                    bly= (float) (1f/fullHeight*getScreenSize().getHeight());
                    w=nativeTextField.getWidth()*blx;
                    h=nativeTextField.getHeight()*bly;
                    x= (float) textfield.getFrame().getX();
                    y= (float) textfield.getFrame().getY();
                    cgRect.setWidth(w);
                    cgRect.setHeight(h);
                    cgRect.setX(x);
                    cgRect.setY(y);
                    textfield.setFrame(cgRect);
                }else {
                    textfield.setHidden(true);
                }
                break;
            case remove:
                textfield = textFieldHashMap.get(nativeTextField);
                Array<Object> nativeAllListener=nativeTextField.getAllNativeListener();
                for(Object object:nativeAllListener){
                    if(object instanceof OnEditingDidBeginListener)
                        textfield.removeListener((OnEditingDidBeginListener) object);
                    else if(object instanceof OnEditingDidEndListener)
                        textfield.removeListener((OnEditingDidEndListener) object);
                    else if(object instanceof OnPrimaryActionTriggeredListener)
                        textfield.removeListener((OnPrimaryActionTriggeredListener) object);
                    else if(object instanceof OnEditingChangedListener)
                        textfield.removeListener((OnEditingChangedListener) object);
                }
                nativeAllListener.clear();
                textfield.removeFromSuperview();
                pool_textFields.free(textfield);
                textFieldHashMap.remove(textfield);
                break;
        }
    }

    public void goToShare(String title, String context, String url, byte[] imgByte,
                          final Runnable success, final Runnable failure) {
        List activityItems = new ArrayList();
        activityItems.add(title);
        activityItems.add(context);
        NSURL nsurl = new NSURL(url);
        activityItems.add(nsurl);
        UIActivityViewController activityShare = new UIActivityViewController(activityItems, null);
        List<String> exs = new ArrayList();
        exs.add(UIActivityType.AirDrop());
        exs.add(UIActivityType.Mail());
        exs.add(UIActivityType.OpenInIBooks());
        exs.add(UIActivityType.AssignToContact());
        exs.add(UIActivityType.CopyToPasteboard());
        exs.add(UIActivityType.Print());
        exs.add(UIActivityType.SaveToCameraRoll());
        exs.add(UIActivityType.AddToReadingList());
        exs.add(UIActivityType.PostToFlickr());
        exs.add(UIActivityType.PostToVimeo());
        exs.add(UIActivityType.Message());
        activityShare.setExcludedActivityTypes(exs);
        activityShare.setCompletionWithItemsHandler(new VoidBlock4<String, Boolean, NSArray<NSObject>, NSError>() {
            public void invoke(String s, Boolean completed, NSArray<NSObject> nsObjects, NSError nsError) {
                if (completed) {
                    success.run();
                } else {
                    failure.run();
                }
            }
        });
        IOSApplication iosApplication = (IOSApplication) Gdx.app;
        if (UIDevice.getCurrentDevice().getUserInterfaceIdiom() == UIUserInterfaceIdiom.Phone) {
            //iPhone
            iosApplication.getUIViewController().presentViewController(activityShare, true, null);
        } else {
            //iPad
            UIPopoverController popover = new UIPopoverController(activityShare);
            UIView view = iosApplication.getUIViewController().getView();
            CGRect rect = new CGRect(view.getFrame().getWidth() / 2, view.getFrame().getHeight() / 4, 0, 0);
            popover.presentFromRectInView(rect, view, UIPopoverArrowDirection.Any, true);
        }
    }

    private boolean keyBoardVisible;
    private VListenerOnKeyboardChange listeners;
    private VStage stage;
    private float keyboardHeight;
    private NSNotificationCenter center;
    private UITextField uiTextField;
    private VTextField vTextField;

    public void setListenerOnKeyboardChange(VStage stage,VListenerOnKeyboardChange listener){
        this.listeners = listener;
        this.stage=stage;
        //在平台接口的初始化代码中调用下面代码
        if (center == null) {
            center = NSNotificationCenter.getDefaultCenter();
            center.addObserver(this, Selector.register("keyboardWillShow:"), UIWindow.KeyboardWillShowNotification(), null);
            center.addObserver(this, Selector.register("keyboardWillHide:"), UIWindow.KeyboardWillHideNotification(), null);
        }
    }


    public void removeListenerOnKeyboardChange(){
        this.listeners=null;
        this.stage=null;
        this.vTextField=null;
        //center.removeObserver(this);
    }


    //对应两个Selector方法实现
    @Method(selector="keyboardWillShow:")
    public void keyboardWillShow(NSNotification notification){

        keyBoardVisible = true;
        //获取键盘的高度
        NSDictionary<NSString, NSObject> userInfo = (NSDictionary<NSString, NSObject>) notification.getUserInfo();
        NSValue value = (NSValue) userInfo.get(UIKeyboardAnimation.Keys.FrameEnd());
        CGRect keyboardRect = NSValueExtensions.getRectValue(value);
        keyboardHeight= (float) (keyboardRect.getHeight());
        if(listeners!=null){
            float bly= (float) (1f/getScreenSize().getHeight() * stage.getFullHeight());
            listeners.onKeyboardChange(keyBoardVisible, keyboardHeight*bly);
        }
    }

    @Method(selector="keyboardWillHide:")
    public void keyboardWillHide(NSNotification notification){
        keyBoardVisible = false;
        keyboardHeight = 0;
        if(listeners!=null)listeners.onKeyboardChange(keyBoardVisible, keyboardHeight);
    }

    private UITextField getDefaultUiTextField(){
        if(uiTextField==null){
            IOSInput iosInput= (IOSInput) Gdx.input;
            //iosInput.setKeyboardCloseOnReturnKey(false);
            uiTextField=iosInput.getKeyboardTextField();
        }
        return uiTextField;
    }

    public void linkVTextField(final VTextField vTextField){
        this.vTextField=vTextField;
        UITextField uiTextField=getDefaultUiTextField();
        uiTextField.setKeyboardType(UIKeyboardType.valueOf(vTextField.getKeyboardType().value()));
        uiTextField.setReturnKeyType(UIReturnKeyType.valueOf(vTextField.getReturnKeyType().value()));
    }

    private final UITextFieldDelegate textDelegate = new UITextFieldDelegateAdapter() {

        public boolean shouldChangeCharacters (UITextField textField, NSRange range, String string) {
            for (int i = 0; i < range.getLength(); i++) {
                Gdx.app.getInput().getInputProcessor().keyTyped(VTextField.BACKSPACE );
            }

            if (string.isEmpty()||string.equals("")) {
                if (range.getLength() > 0) Gdx.graphics.requestRendering();
                return false;
            }

            if(vTextField!=null){
                FreeBitmapFont font= (FreeBitmapFont) vTextField.getStyle().font;
                string=font.appendTextPro(string);
            }

            for (int i = 0, len = string.length(); i < len; i++) {
                char newchar = string.charAt(i);
                Gdx.app.getInput().getInputProcessor().keyTyped(newchar);
            }

            Gdx.graphics.requestRendering();

            return true;
        }

        @Override
        public boolean shouldEndEditing (UITextField textField) {
            textField.setText("x");
            Gdx.graphics.requestRendering();

            return true;
        }

        @Override
        public boolean shouldReturn (UITextField textField) {
            Gdx.app.getInput().getInputProcessor().keyDown(Input.Keys.ENTER);
            Gdx.app.getInput().getInputProcessor().keyTyped(VTextField.ENTER);
            Gdx.graphics.requestRendering();
            return false;
        }
    };

    public void setOnscreenKeyboardVisible(boolean isvisibe){
        uiTextField=getDefaultUiTextField();
        if (isvisibe) {
            uiTextField.becomeFirstResponder();
            uiTextField.setDelegate(textDelegate);
        } else {
            uiTextField.resignFirstResponder();
        }
    }
}
