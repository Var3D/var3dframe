package var3d.net.center.iosmoe;

import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;

import apple.uikit.ITargetAction;

@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("UITextListener")
@RegisterOnStartup
public class   UITextListener implements ITargetAction {

    static {
        NatJ.register();
    }
    public long event = 0;


    public UITextListener(long event){
        this.event = event;
    }

    @Generated
    @Selector("onEvent")
    @Override
    public void onEvent(Object source, long event) {
    }
}
