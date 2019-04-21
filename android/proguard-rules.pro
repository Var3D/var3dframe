# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontpreverify #不做预校验
-ignorewarning #忽略警告

-verbose

-dontwarn android.support.**
-dontwarn com.badlogic.gdx.backends.android.AndroidFragmentApplication
-dontwarn com.badlogic.gdx.utils.GdxBuild
-dontwarn com.badlogic.gdx.physics.box2d.utils.Box2DBuild
-dontwarn com.badlogic.gdx.jnigen.BuildTarget*
-dontwarn com.badlogic.gdx.graphics.g2d.freetype.FreetypeBuild

-keep class com.badlogic.gdx.controllers.android.AndroidControllers

-keepclassmembers class com.badlogic.gdx.backends.android.AndroidInput* {
   <init>(com.badlogic.gdx.Application, android.content.Context, java.lang.Object, com.badlogic.gdx.backends.android.AndroidApplicationConfiguration);
}

-keepclassmembers class com.badlogic.gdx.physics.box2d.World {
   boolean contactFilter(long, long);
   void    beginContact(long);
   void    endContact(long);
   void    preSolve(long, long);
   void    postSolve(long, long);
   boolean reportFixture(long);
   float   reportRayFixture(long, float, float, float, float, float);
}

#保持Var3D框架
#-dontwarn var3d.net.center.android.VAndroidLauncher
#-dontwarn com.badlogic.gdx.physics.bullet.**
#-dontwarn net.java.games.input.**
#-dontwarn org.lwjgl.opengl.**
#-dontwarn java.awt.**
#-dontwarn javax.swing.**
#-dontwarn com.apple.eio.**
#-dontwarn sun.reflect.**
#-dontwarn sun.misc.**
#
#-dontwarn com.badlogic.gdx.**
#-keep class com.badlogic.**{ *; }
#-dontwarn var3d.net.center.**
#-keep class var3d.net.center.**{*;}
#-dontwarn com.tapegg.followertown.**
#-keep class com.tapegg.followertown.**{*;}
#
#-obfuscationdictionary proguard-o0O.txt
#-classobfuscationdictionary proguard-o0O.txt
#-packageobfuscationdictionary proguard-o0O.txt

#第三方库的混淆
##okhttp3混淆
#-keep public class var3d.net.demo.R
#-dontwarn var3d.net.demo.R
#
#-keep public class var3d.net.center.freefont.**{*;}
#-dontwarn var3d.net.center.freefont.**
#
#-keep class var3d.net.center.android.**{*;}
#-dontwarn var3d.net.center.android.**

#-keep class var3d.net.center.**{*;}
#-dontwarn var3d.net.center.**

#-dontwarn com.badlogic.gdx.**
#-keep class com.badlogic.**{ *; }

-keep public class var3d.net.demo.**{*;}

