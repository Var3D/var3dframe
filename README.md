# var3dframe
[![Build Status](https://travis-ci.org/Var3D/var3dframe.svg?branch=master)](https://travis-ci.org/Var3D/var3dframe)

A game framework base on LibGDX 1.9.8

一个基于Libgdx的轻量级游戏框架，使Libgdx非常方便的支持中文以及任意字符，更方便使用的UI控件以及界面对话框的管理，低耦合的特性使你可以混合使用原生Libgdx的API
当前支持的Libgdx版本为1.9.8

[加入QQ群获得技术支持](https://jq.qq.com/?_wv=1027&k=51c4s6D)

# Getting Started
Include var3dframe via Gradle

*Step 1. Add the JitPack repository to your build file*
```
 allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
*Step 2. Add a version to allprojects*
```
    allprojects {
        ext {
            ...
            var3d = "1.0.1"
            ...
        }
    }
    
```
*Step 3. Add the dependency*
+ core
```
dependencies {
	   compile "com.github.Var3D.var3dframe:core:$var3d"
}
```
+ desktop
```
dependencies {
	   compile "com.github.Var3D.var3dframe:desktop:$var3d"
}
```
+ android
```
dependencies {
	   compile "com.github.Var3D.var3dframe:android:$var3d"
}
```
+ ios
```
dependencies {
	   compile "com.github.Var3D.var3dframe:ios:$var3d"
}
```
+ ios-moe
```
dependencies {
	   compile "com.github.Var3D.var3dframe:ios-moe:$var3d"
}
```

# Authors & Contributors
+ [Var3D](https://github.com/Var3D)
+ [whitecostume](https://github.com/whitecostume)
+ [HuangBoHong](https://github.com/HuangBoHong)
+ [Huang YunKun](https://github.com/htynkn)

# Game case based on var3dframe
![](https://www.tapegg.com/games/snake3d-ad.jpg)![](https://www.tapegg.com/games/hero-ad-en.jpg)
+ [Our Craft](https://itunes.apple.com/cn/app/id1144041654)
+ [King Greedy Snake](https://itunes.apple.com/cn/app/id1249822516)
+ [Snake Kill Brick](https://itunes.apple.com/cn/app/id1403252096)
+ [Hero Fighting](https://itunes.apple.com/cn/app/id1344510227)
+ [Hot Chariot](https://itunes.apple.com/cn/app/id1280455730)
+ [More](https://www.var3d.net/)

# License
TBC

# Acknowledgments
![LibGDX](http://libgdx.badlogicgames.com/img/logo.png)
