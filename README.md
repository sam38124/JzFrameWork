[![](https://jitpack.io/v/sam38124/JzFrameWork.svg)](https://jitpack.io/#sam38124/JzFrameWork)
[![Platform](https://img.shields.io/badge/platform-%20Android%20-brightgreen.svg)](https://github.com/sam38124)
[![characteristic](https://img.shields.io/badge/特點-%20輕量級%20%7C%20簡單易用%20%20%7C%20穩定%20-brightgreen.svg)](https://github.com/sam38124)
# JzFrameWork
This is a high-performance Android development framework that achieves almost zero-delay screen transitions. It adopts single activity and multiple fragment architecture  The framework uses kotlin and androidx for development to support all versions of android projects. Can help developers in deploy your application in the shortest time. This framework will continue to be updated, but it can only meet its own development needs. If there are other customized requirements, please clone it and modify it!<br><br>
App developed based on this framework:<br><br>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/Obdicon.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/SQCHECK 1024X1024.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/btn_icon.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/coffee.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/icon.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/tsporticon.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/tpms_logo.jpg?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/icon_default_logo.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<img src="https://github.com/sam38124/JzFrameWork/blob/master/App%20icon/gotit_icon.png?raw=true" width = "70"  alt="i1" /><a name="Use"></a>
<br><br>
Also support ios version:[Click to view](https://github.com/sam38124/JzOsFrameWork)
## List
* [Import to project](#Import)
* [Quick Start](#Use)
* [All function](#All)
* [About me](#About)

<a name="Import"></a>
## Import to project
> Support jcenter。 <br/>

### jcenter
Add into build.gradle 
```kotlin
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add into dependencies

```kotlin
dependencies {
implementation 'com.github.sam38124:JzFrameWork:10.2.5'}
```
<a name="Use"></a>
## Quick Start
#### 1.Create Activity and extend JzActivity(Only can use one Activity what extend JzActivity)

```kotlin
class MainActivity : JzActivity() {
    /*Code handling for the first load*/
    override fun viewInit(rootview: View) {
        //Set home page
        JzActivity.getControlInstance().setHome(Page_Home(), "Page_Home")
        //Can set swipe menu if you want
        JzActivity.getControlInstance().setDrawer(sampledrawer())
    }

    /*Page switch linstener*/
    override fun ChangePageListener(tag: String, frag: Fragment) {
    }

    /*Keyboard linstener*/
    override fun keyEventListener(event: KeyEvent):Boolean {
    return true
    }
     /*Dialog linstener*/
    override fun dialogLinstener(dialog: Dialog, tag: String) {

    }
}
```
#### 2.Create Fragment and extend JzFragement(All fragements need  extend JzFragement)
```kotlin
/*R.layout.activity_main replace to your layout id*/
class Frag_Home : JzFragement(R.layout.activity_main) {

        /* Code handling for the first load*/
    override fun viewInit() {
        /*
        Refresh default value is false
        When Refresh is true next time will reload viewInit function when you go back to the same page，Otherwise will keep retain page state
        */
        refresh=Boolean

    }
}


```
### Use in anywhere
#### Change page 
```kotlin
//Nomal
JzActivity.getControlInstance().changePage(Page_Third(), "Page_Third", true)
//With Animator
JzActivity.getControlInstance().changePage(Page_Third(), "Page_Third", true,Animator.translation)
```
#### Go back 
```kotlin
 JzActivity.getControlInstance().goBack()
```
#### Set drawer
```kotlin
//sampledrawer replace to your fragment
JzActivity.getControlInstance().setDrawer(sampledrawer())
```
#### Go menu
```kotlin
JzActivity.getControlInstance().goMenu()
```
#### Show custom dialog
```kotlin
 /*

            cancelable->Can close dialog by click
            swipe ->Set background is transparent or not
            */
 JzActivity.getControlInstance().showDiaLog( true, false, object : SetupDialog(R.layout.sampledialog) {
                override fun keyevent(event: KeyEvent): Boolean {
                    return true
                }

                override fun setup(rootview: Dialog) {
                    //Set up your dialog
                    rootview.findViewById<Button>(R.id.button).setOnClickListener {
                        rootview.dismiss()
                    }
                }

                override fun dismess() {
                    //Close listener
                }
            },"sampledialog")
```
<a name="All"></a>
### All function
```kotlin


interface control {
   /* All function*/


    //Set home page
    fun setHome(Translation: Fragment, tag: String)
    //Page switch
    fun changePage(Translation: Fragment, tag: String, goback: Boolean)
    //Page switching and custom transition animation
    fun changePage(Translation: Fragment, tag: String, goback: Boolean,animator:Array<Int>)
    //Fragment switching in the page
    fun changeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean)
    //Fragment switching on the page and custom transition animation
    fun changeFrag(Translation: Fragment, id: Int,tag: String, goback: Boolean,animator:Array<Int>)
    //Fragment replacement in the page
    fun replaceFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean)
    //Replace fragment in the page and customize transition animation
    fun replaceFrag(Translation: Fragment, id: Int,tag: String, goback: Boolean,animator:Array<Int>)
    //Get Fragement in the push stack through tag
    fun findFragByTag(a:String):Fragment?
    //Get the page currently displayed
    fun getNowPage():Fragment?
    //Get the tag name of the page currently displayed
    fun getNowPageTag():String
    //Back to homepage
    fun goMenu()
    //Back to previous page
    fun goBack()
    //Return the page where the tag is the input value
    fun goBack(tag: String)
    //Go back to the page at a certain location
    fun goBack(a: Int)
    //Request access
    fun permissionRequest(Permissions: Array<String>, caller: permission_C, RequestCode: Int)
    //Display customized Dialog
    fun showDiaLog(layoutid:Int ,cancelable: Boolean, swip: Boolean,tag:String)
    //Display customized Dialog
    fun showDiaLog( cancelable: Boolean, swip: Boolean, caller: SetupDialog,tag:String)
    //Display customized Dialog, and customize style
    fun showCustomDaiLog( cancelable: Boolean, style: Int, caller: SetupDialog,tag:String)
    //Dialog pops up below the display, and can be dragged to close
    fun showBottomSheetDialog(cancelable: Boolean,swip:Boolean,caller: SetupDialog, tag: String)
    //Get the Dialog whose tag is the input value
    fun getDialog(tag:String): Dialog?
    //Close the Dialog whose tag is the input value
    fun closeDiaLog(tag:String)
    //Close all Dialog
    fun closeDiaLog()
    //Save SharedPreferences record
    fun setPro(key: String, value: Boolean)
    fun setPro(key: String, value: String)
    fun setPro(key: String, value: Int)
    fun getPro(key: String, value: String): String
    fun getPro(key: String, value: Boolean): Boolean
    fun getPro(key: String, value: Int): Int
    //Clear History
    fun clearPro()

    //Close the entire app
    fun closeApp()
    //Set screen orientation
    fun setOrientation(a: Int)
    //Set side drawer
    fun setDrawer(frag: JzFragement)
    //Open the side drawer
    fun openDrawer()
    //Close the side drawer
    fun closeDrawer()
    //Refresh the side drawer (will run the viewInit method again)
    fun refreshDrawer()
    //Toast display
    fun toast(a:String)
    //Toast display
    fun toast(a:Int)
    //Get JzActivity
    fun getRootActivity(): JzActivity
    //Multi-language setting Sample:setLanuage(Locale("en"))
    fun setLanguage(local: Locale)
    //Get the set multi-language
    fun getLanguage():Locale
    //Keyboard hide
    fun hideKeyBoard()
    //Download apk
    fun apkDownload(url:String,callback:DownloadCallback)
    //Open apk
    fun openAPK()
    //Whether the app is in the foreground
    fun isFrontDesk():Boolean
    //The screen is always on
    fun screenAlwaysOn()
    //Turn off the screen is always on
    fun cancelAlwaysOn()
    //Get app information
    fun getAppInformation():PackageInformation
    //Restart app
    fun restart(a:Class<*>)
    //Check for updates and return to the version number true to jump to the store, otherwise not to jump
    fun checkUpdate(a:Boolean):String?
    //Go to google play store
    fun goStore()
    //Get handler
    fun getHandler(): Handler
    //Set activity result callback
    fun setOnActivityResultCallback(callback: onActivityResultCallback)
}
```

<a name="About"></a>
# About me
#### <font color="#0000dd"> Work for: </font><br /> 
+ ##### <font color="#660000">【Orange Electronic】</font><br /> 
#### <font color="#0000dd"> Position: </font><br /> 
+ ##### Full stack engineer<br/>  
#### <font color="#0000dd"> Main defense range: </font><br /> 
+ ##### Android and IOS(4 years)<br/>  
+ ##### Jsp(2 years)<br/> 
+ ##### Javascript and Jquery and Ktor(1 years)<br /> 
#### <font color="#0000dd"> Contact information: </font><br /> 
+  ##### line:sam38124<br /> 

+  ##### gmail:sam38124@gmail.com
