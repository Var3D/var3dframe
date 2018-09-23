package var3d.net.center.iosmoe;

import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSArray;
import apple.foundation.NSError;
import apple.uikit.UIActivityViewController;


@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ShareCompleteionListener")
@RegisterOnStartup
public class ShareCompleteionListener implements UIActivityViewController.Block_setCompletionWithItemsHandler {

    static {
        NatJ.register();
    }

    @Generated
    @Selector("call_setCompletionWithItemsHandler")
    @Override
    public void call_setCompletionWithItemsHandler(String arg0, boolean arg1, NSArray<?> arg2, NSError arg3) {

    }
}
