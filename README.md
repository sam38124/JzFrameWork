# JzFrameWork
這是一套高效能的Android開發框架，實現了近乎零延遲的畫面轉場，採用一個Activity多個Fragment的架構，為了支持所有版本的android Project，框架採用kotlin以及androidx進行開發，幫助開發者在最短的時間內部署好你的應用．
## 目錄
* [如何導入到專案](#Import)
* [快速使用](#Use)
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
implementation 'com.github.sam38124:JzFrameWork:v2.0'
}
```
<a name="Use"></a>
## 如何使用

### 第一步：創建一個Activity並且繼承RootActivity

```kotlin
class MainActivity : RootActivity() {
    /*初次載入的代碼處理*/
    override fun ViewInit(rootview: View) {
        //設定首頁
        SetHome(Frag_Home(), "Frag_Home")
    }

    /*頁面切換監聽*/
    override fun ChangePageListener(tag: String, frag: Fragment) {
        //SetHome完會返回"Frag_Home"以及Frag_Home()的值
    }

    /*按鈕事件監聽*/
    override fun KeyLinsten(event: KeyEvent) {

    }
}

```
### 第二步：創建要成為首頁的Fragment，並且於Activty中的ViewInit設定首頁
```kotlin
/*R.layout.activity_main替換為你的layout id*/
class Frag_Home : RootFragement(R.layout.activity_main) {

        /*初次載入的代碼處理*/
    override fun ViewInit() {
        /*
        Refresh預設值為false
        當Refresh為true時，下次載入會重新刷新頁面，並且重跑ViewInit的方法
        當Refresh為false時，下次載入時會保留上次的操作動作和頁面
        */
        Refresh=Boolean
        //使用下面其中一種方式取得layout元件
        rootview.findViewById<TextView>(R.id.sampletext).text= "method1"
        //或者
        rootview.sampletext.text = "method2"

    }
}

```
##### 1.於Fragment中做切換(必需繼承RootFragment)
```kotlin
    act.SetHome(Frag_Home(), "Frag_Home")
```
##### 2.於Activity中做切換(必需繼承RootActivity)
```kotlin
    SetHome(Frag_Home(), "Frag_Home")
```
<a name="About"></a>
### 第三步：在任何地方執行下方代碼進行頁面的切換

##### 1.於Fragment中做切換(必需繼承RootFragment)
```kotlin
//true會將現在顯示的fragment加入可返回的推棧，按下返回鍵則會返回現在的頁面，反之false則不能返回現在的頁面
  act.ChangePage(Frag_Sec(),"Frag_Sec",Boolean)
```
##### 2.於Activity中做切換(必需繼承RootActivity)
```kotlin
//true會將現在顯示的fragment加入可返回的推棧，按下返回鍵則會返回現在的頁面，反之false則不能返回現在的頁面
  ChangePage(Frag_Sec(),"Frag_Sec",Boolean)
```
<a name="About"></a>
### 關於我
現任橙的電子全端app開發工程師

*line:sam38124

*gmail:sam38124@gmail.com
