[![](https://jitpack.io/v/sam38124/JzFrameWork.svg)](https://jitpack.io/#sam38124/JzFrameWork)
[![Platform](https://img.shields.io/badge/平台-%20Android%20-brightgreen.svg)](https://github.com/sam38124)
[![characteristic](https://img.shields.io/badge/特點-%20輕量級%20%7C%20簡單易用%20%20%7C%20穩定%20-brightgreen.svg)](https://github.com/sam38124)
# JzFrameWork
這是一套高效能的Android開發框架，實現了近乎零延遲的畫面轉場，採用一個Activity多個Fragment的架構，為了支持所有版本的android Project，框架採用kotlin以及androidx進行開發，幫助開發者在最短的時間內部署好你的應用．
此框架會不斷進行更新但也只能剛好滿足自身開發需求，如有其他客製化要求，歡迎克隆下來去修改!<br><br>
基於此框架開發的APP:https://play.google.com/store/apps/details?id=com.orange.tsport<br>
另外支持ios版本:[點我查看](https://github.com/sam38124/JzOsFrameWork)
## 目錄
* [如何導入到專案](#Import)
* [快速使用](#Use)
* [所有對外暴露方法](#All)
* [關於我](#About)

<a name="Import"></a>
## 如何導入到項目
> 支持jcenter。 <br/>

### jcenter導入方式
在app專案包的build.gradle中添加
```kotlin
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

在需要用到這個庫的module中的build.gradle中的dependencies中加入
```kotlin
dependencies {
implementation 'com.github.sam38124:JzFrameWork:v7.1'
```
<a name="Use"></a>
## 如何使用

### 第一步：於Mainfest中做初次設定，之後創建Mainactivity和要成為首頁的Fragment，並且於Activty中的ViewInit設定首頁
#### 1.在Manifest中添加
```kotlin
android:theme="@style/SwipTheme" //添加在Application
android:configChanges="keyboardHidden|orientation|screenSize"//添加在Activity
```
#### 2.創建Activity並且繼承JzActivity(只能有一個Activity)

```kotlin
class MainActivity : JzActivity() {
    /*初次載入的代碼處理*/
    override fun viewInit(rootview: View) {
        //設定首頁
        JzActivity.getControlInstance().setHome(Page_Home(), "Page_Home")
        //設定抽屜(也可以不要)
        JzActivity.getControlInstance().setDrawer(sampledrawer())
    }

    /*頁面切換監聽*/
    override fun ChangePageListener(tag: String, frag: Fragment) {
        //SetHome完會返回"Frag_Home"以及Frag_Home()的值
    }

    /*按鈕事件監聽*/
    override fun keyEventListener(event: KeyEvent):Boolean {
    //return true繼續執行父類別的dispathKeyEvent方法，反之攔截按鈕事件
return true
    }
}
```
#### 3.創建Fragment並且繼承JzFragement(所有Fragement皆需繼承JzFragement)
```kotlin
/*R.layout.activity_main替換為你的layout id*/
class Frag_Home : JzFragement(R.layout.activity_main) {

        /*初次載入的代碼處理*/
    override fun viewInit() {
        /*
        Refresh預設值為false
        當Refresh為true時，下次載入會重新刷新頁面，並且重跑ViewInit的方法
        當Refresh為false時，下次載入時會保留上次的操作動作和頁面
        */
        refresh=Boolean
        //使用下面其中一種方式取得layout元件
        rootview.findViewById<TextView>(R.id.sampletext).text= "method1"
        //或者
        rootview.sampletext.text = "method2"

    }
}


```
### 第二步:在任何地方進行使用
#### 頁面的切換
```kotlin
JzActivity.getControlInstance().changePage(Page_Third(), "Page_Third", true)
```
#### 返回上一頁
```kotlin
 JzActivity.getControlInstance().goBack()
```
#### 設定測邊抽屜
```kotlin
//sampledrawer替換成要成為抽屜的Fragment
JzActivity.getControlInstance().setDrawer(sampledrawer())
```
#### 返回首頁
```kotlin
JzActivity.getControlInstance().goMenu()
```
#### 顯示客製化Dialog
```kotlin
 /*
            使用 ShowDaiLog 的方法顯示客製化Dialog
            cancelable 決定Dialog是否可以被點擊消失
            swipe 決定Dialog背景是否透明反之為不透明
            R.layout.sampledialog 換成你的Dialog layout
            */
 JzActivity.getControlInstance().showDiaLog(R.layout.sampledialog, true, false, object : SetupDialog {
                override fun keyevent(event: KeyEvent): Boolean {
                    //按鈕事件監聽
                    // return true後會繼續執行父類別的dispathKeyevent方法，反之攔截按鈕事件
                    return true
                }

                override fun setup(rootview: Dialog) {
                    //Dialog的載入設定
                    rootview.findViewById<Button>(R.id.button).setOnClickListener {
                        rootview.dismiss()
                    }
                }

                override fun dismess() {
                    //Dialog關閉的監聽
                }
            },"sampledialog")
```
<a name="All"></a>
### 所有對外暴露的方法
```kotlin

interface control {
        /*所有對外暴露的方法*/

    //頁面切換
    fun changePage(Translation: Fragment, tag: String, goback: Boolean)
    //設定首頁
    fun setHome(Translation: Fragment, tag: String)
    //頁面中的fragment切換
    fun changeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean)
    //透過tag取得推棧中的Fragement
    fun findFragByTag(a:String):Fragment?
    //取得現在顯示的頁面
    fun getNowPage():Fragment?
    //取得現在顯示的頁面的Tag名稱
    fun getNowPageTag():String
    //返回首頁
    fun goMenu()
    //回上一頁
    fun goBack()
    //反回tag為輸入值的頁面
    fun goBack(tag: String)
    //反回某個位置的頁面
    fun goBack(a: Int)
    //要求存取權限
    fun permissionRequest(Permissions: Array<String>, caller: permission_C, RequestCode: Int)
    //顯示客製化Dialog
    fun showDiaLog(Layout: Int, cancelable: Boolean, swip: Boolean,tag:String)
    //顯示客製化Dialog
    fun showDiaLog(Layout: Int, cancelable: Boolean, swip: Boolean, caller: SetupDialog,tag:String)
    //顯示客製化Dialog，並且自定義style
    fun showCustomDaiLog(Layout: Int, cancelable: Boolean, style: Int, caller: SetupDialog,tag:String)
    //取得tag為輸入值的Dialog
    fun getDialog(tag:String): Dialog?
    //關閉tag為輸入值的Dialog
    fun closeDiaLog(tag:String)
    //關閉所有Dialog
    fun closeDiaLog()
    //保存SharedPreferences紀錄
    fun setPro(key: String, value: Boolean)
    fun setPro(key: String, value: String)
    fun setPro(key: String, value: Int)
    fun getPro(key: String, value: String): String
    fun getPro(key: String, value: Boolean): Boolean
    fun getPro(key: String, value: Int): Int
    //清除記錄
    fun clearPro()

    //關閉整個app
    fun closeApp()
    //設定螢幕方向
    fun setOrientation(a: Int)
    //設定側邊抽屜
    fun setDrawer(frag: JzFragement)
    //打開側邊抽屜
    fun openDrawer()
    //關閉側邊抽屜
    fun closeDrawer()
    //刷新側邊抽屜(會重新跑一次viewInit方法)
    fun refreshDrawer()
    //吐司的顯示
    fun toast(a:String)
    //吐司的顯示(R.string.a)
    fun toast(a:Int)
    //取得Activity
    fun getRootActivity(): JzActivity
    //多國語言設定 範例:setLanuage(Locale("en"))
    fun setLanguage(local: Locale)
    //鍵盤隱藏
    fun hideKeyBoard()
    //下載apk
    fun apkDownload(url:String,callback:DownloadCallback)
    //打開apk
    fun openAPK()
    //app是否處於前台
    fun isFrontDesk():Boolean
    //螢幕常亮
    fun screenAlwaysOn()
    //關閉螢幕常亮
    fun cancelAlwaysOn()
    //取得app資訊
    fun getAppInformation():PackageInformation
    //重啟app
    fun restart()
    //檢查更新並返回版本號true跳轉至商店反之不跳轉，
    fun checkUpdate(a:Boolean):String?
}
```

<a name="About"></a>
### 關於我
橙的電子android and ios developer

*line:sam38124

*gmail:sam38124@gmail.com
