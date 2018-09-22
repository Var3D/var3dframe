package var3d.net.center.iosmoe;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import org.moe.natj.objc.ann.Selector;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSDictionary;
import apple.foundation.NSNotification;
import apple.foundation.NSNotificationCenter;
import apple.foundation.NSValue;
import apple.uikit.UIScreen;
import apple.uikit.UITextField;
import apple.uikit.c.UIKit;
import var3d.net.center.NativeTextField;
import var3d.net.center.VListener;
import var3d.net.center.VStage;

@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("VMoeUITextField")
@RegisterOnStartup
public class VMoeUITextField  extends UITextField implements Pool.Poolable {
    private NativeTextField nativeTextField;

    private CGSize screenSize;

    private VListener listener;

    static {
        NatJ.register();
    }

    @Generated
    protected VMoeUITextField(Pointer peer) {

        super(peer);
        screenSize= UIScreen.mainScreen().bounds().size();
    }


    public void setLibgdxTextField(NativeTextField nativeTextField){
        this.nativeTextField =nativeTextField;
    }

    @Owned
    @Selector("alloc")
    public static native VMoeUITextField alloc();

    @Selector("initWithFrame:")
    public native VMoeUITextField initWithFrame(@ByValue CGRect frame);

    public static VMoeUITextField initWithListener(VListener listener,CGRect frame)
    {
        VMoeUITextField textField =  alloc().initWithFrame(frame);
        textField.listener = listener;
        return  textField;
    }

    @Selector("changeTextFieldFrame")
    public void changeTextFieldFrame(NSNotification aNotification){
        final NSDictionary userInfo = aNotification.userInfo();
        NSValue aValue = (NSValue) userInfo.get("UIKeyboardFrameEndUserInfoKey");
        CGRect keyboardRect =aValue.CGRectValue();
        CGRect frame = this.frame();
        double keyboardHeight=screenSize.height()-keyboardRect.size().height();
        final Stage stage = nativeTextField.getStage();
        //键盘高度转换为 libgdx 坐标系
        final float libgdxKeyboardHeight,bly;
        if(stage!=null) {
            float fullHeight = stage instanceof VStage ? ((VStage) stage).getFullHeight() : stage.getHeight();
            bly= (float) (1f/screenSize.height() * fullHeight);
            libgdxKeyboardHeight = (float) (keyboardRect.size().height() *bly);
        }else{
            return;
        }

        if(frame.origin().y()+frame.size().height()> keyboardHeight) {
            switch (nativeTextField.getAdaptKeyboardType()){
                case Self:
                    CGPoint point = frame.origin();
                    point.setY(keyboardHeight-frame.size().height());
                    frame.setOrigin(point);
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

                listener.linkNativeTextField(son, NativeTextField.Method.positionChanged);
            }
            if(actor instanceof Group)synchronizeAllForStage((Group)actor);
        }
    }

    @Selector("keyboardWillHide")
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
        NSNotificationCenter defaultCenter= NSNotificationCenter.defaultCenter();
        defaultCenter.addObserverSelectorNameObject(this,new SEL("changeTextFieldFrame")
                , UIKit.UIKeyboardWillChangeFrameNotification()
        ,null);
        defaultCenter.addObserverSelectorNameObject(this,
                new SEL("keyboardWillHide"),
                UIKit.UIKeyboardWillHideNotification()
        ,null);
    }

    //移出注册
    public void removeRegistered(){
        isRegistered=false;
        NSNotificationCenter defaultCenter=NSNotificationCenter.defaultCenter();
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
            blx= (float) (1f/fullWidth*screenSize.width());
            bly= (float) (1f/fullHeight*screenSize.height());
            w=(float) frame().size().width();
            h= (float) frame().size().height();
            float fx=nativeTextField.getX();
            float fy=nativeTextField.getY();
            Group father=nativeTextField.getParent();
            Group root=stage.getRoot();
            float dx=root.getX()-cutWidth;
            float dy=root.getY()/root.getScaleY()-cutHeight;
            //float dy=root.getY()-cutHeight;
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
            //y= (cutAndHeight-fy)*bly-h;
            float my=(cutHeight+fy)*bly;
            CGRect cgRect = frame();
            y= (float) (screenSize.height()-h)-my;
            CGSize size = frame().size();
            size.setWidth(w);
            size.setHeight(h);
            frame().setSize(size);

            CGPoint point = cgRect.origin();
            point.setX(x);
            point.setY(y);
            cgRect.setOrigin(point);
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