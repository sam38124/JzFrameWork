package com.orange.jzchi.jzframework

import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.orange.jzchi.R
import com.orange.jzchi.jzframework.CallBack.Dailog_SetUp_C
import com.orange.jzchi.jzframework.CallBack.DiapathKey
import com.orange.jzchi.jzframework.CallBack.Permission_C
import com.orange.jzchi.jzframework.CallBack.RootShare
import com.orange.jzchi.jzframework.tool.LanguageUtil
import kotlinx.android.synthetic.main.activity_root.view.*
import java.security.MessageDigest
import java.util.*

abstract class RootActivity : AppCompatActivity(),
    FragmentManager.OnBackStackChangedListener {
    companion object {
        val Orientation_Vertical = 0;
        val Orientation_Horizontal = 1;
        val Orientation_Default = 2;
    }
    lateinit var rootshare:RootShare
    val LayoutId = R.layout.activity_root
    val FragId = R.id.frag_root
    var handler = Handler()
    lateinit var mOrientationListener: OrientationEventListener;
    private var permissionRequestCode = 10
    var Fraging: Fragment? = null
    var FragName = ""
    lateinit var PermissionCaller: Permission_C
    lateinit var rootview: View
    lateinit var NavagationRoot: DrawerLayout
    lateinit var NavaGationFrag: RootFragement
    override fun onBackStackChanged() {
        Fraging = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]
        FragName = Fraging!!.tag!!
        ChangePageListener(FragName, Fraging!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutId)
        rootshare= RootShare(this)
        NavagationRoot = findViewById(R.id.drawer)
        supportFragmentManager.addOnBackStackChangedListener(this)
        rootview = findViewById<View>(android.R.id.content).rootView
        ViewInit(rootview)
        NavagationRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    fun SetOrientation(a: Int) {
        when (a) {
            Orientation_Vertical -> {
                if (::mOrientationListener.isInitialized) {
                    mOrientationListener.disable();
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            Orientation_Horizontal -> {
                if (::mOrientationListener.isInitialized) {
                    mOrientationListener.disable();
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            Orientation_Default -> {
                mOrientationListener = object : OrientationEventListener(
                    this,
                    SensorManager.SENSOR_DELAY_NORMAL
                ) {
                    override fun onOrientationChanged(orientation: Int) {
                        Log.v(
                            "changeor",
                            "Orientation changed to " + orientation
                        );
                        if (orientation < 180) {
                            if (CanChangeOr) {
                                CanChangeOr = false
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                handler.postDelayed({
                                    CanChangeOr = true
                                }, 2000)
                            }
                        } else {
                            if (CanChangeOr) {
                                CanChangeOr = false
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                handler.postDelayed({
                                    CanChangeOr = true
                                }, 2000)
                            }
                        }
                    }

                };
                if (mOrientationListener.canDetectOrientation()) {
                    Log.v("changeor", "Can detect orientation");
                    mOrientationListener.enable();
                } else {
                    Log.v("changeor", "Cannot detect orientation");
                    mOrientationListener.disable();
                }
            }
        }
    }

    var CanChangeOr = true

    fun SCREEN_ON() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    fun SCREEN_CLOSE() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    fun GoBack() {
        supportFragmentManager.popBackStack()
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun GoBack(a: Int) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun GoBack(a: String) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun HideKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0)
    }

    open fun CloseDrawer() {
        NavagationRoot.closeDrawer(GravityCompat.START)
    }


    open fun SetNavaGation(frag: RootFragement) {
        NavagationRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        NavaGationFrag = frag
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navigation_view, frag)
            .commit()
    }

    open fun RefreshNavaGation() {
        if(::NavaGationFrag.isInitialized){
            NavaGationFrag.ViewInit()
        }
    }

    open fun OpenNavaGation() {
        HideKeyBoard()
        NavagationRoot.openDrawer(GravityCompat.START)
    }

    open fun ChangePage(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            ChangePageListener(tag, Translation)
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation, tag)
                .commit()
        }
    }

    fun findFragmentByTag(a: String): Fragment? {
        val a = supportFragmentManager.findFragmentByTag(a)
        return a
    }

    open fun SetHome(Translation: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ChangePage(Translation, tag, false)
    }

    open fun ChangePage(Translation: Fragment, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            ChangePageListener(tag, Translation)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                .commit()
        }
    }

    open fun Toast(a: String) {
        handler.post { android.widget.Toast.makeText(this, a, android.widget.Toast.LENGTH_SHORT).show() }
    }

    open fun Toast(id: Int) {
        handler.post { android.widget.Toast.makeText(this, getString(id), android.widget.Toast.LENGTH_SHORT).show() }
    }

    var mDialog: Dialog? = null
    var nowDia = -1
    lateinit var DiaCaller: Dailog_SetUp_C
    fun ShowDaiLog(Layout: Int, touchcancel: Boolean, style: Int, caller: Dailog_SetUp_C) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, style)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if (touchcancel) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this, style)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if (touchcancel) {
                        getAllChildViews(mDialog!!.getWindow().getDecorView())
                    }
                } else {
                    if (nowDia != Layout) {
                        DaiLogDismiss()
                        mDialog = Dialog(this, style)
                        mDialog!!.setContentView(Layout)
                        mDialog!!.getWindow()!!.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                        )
                        mDialog!!.setCancelable(true)
                        mDialog!!.setCanceledOnTouchOutside(touchcancel)
                        mDialog!!.show()
                        if (touchcancel) {
                            getAllChildViews(mDialog!!.getWindow().getDecorView())
                        }
                    }
                }
            }
            nowDia = Layout
            caller.SetUP(mDialog!!, this)
            DiaCaller = caller
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    fun ShowDaiLog(Layout: Int, touchcancel: Boolean, swip: Boolean, caller: Dailog_SetUp_C) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if (touchcancel) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if (touchcancel) {
                        getAllChildViews(mDialog!!.getWindow().getDecorView())
                    }
                } else {
                    if (nowDia != Layout) {
                        DaiLogDismiss()
                        mDialog = Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog)
                        mDialog!!.setContentView(Layout)
                        mDialog!!.getWindow()!!.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                        )
                        mDialog!!.setCancelable(true)
                        mDialog!!.setCanceledOnTouchOutside(touchcancel)
                        mDialog!!.show()
                        if (touchcancel) {
                            getAllChildViews(mDialog!!.getWindow().getDecorView())
                        }
                    }
                }
            }
            nowDia = Layout
            caller.SetUP(mDialog!!, this)
            DiaCaller = caller
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    private fun getAllChildViews(view: View): List<View> {
        val allchildren = ArrayList<View>()
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val viewchild = view.getChildAt(i)
                allchildren.add(viewchild)
                Log.d("ChildView", "$viewchild")
                allchildren.addAll(getAllChildViews(viewchild))
                if ("$viewchild".contains("RelativeLayout")) {
                    viewchild.setOnClickListener { mDialog!!.dismiss() }
                    return allchildren
                }
            }
        }
        return allchildren
    }

    fun DaiLogDismiss() {
        try {
            DiaCaller.Dismiss()
            mDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    val LOCALE_ENGLISH = "en"
    val LOCALE_CHINESE = "zh"
    val LOCALE_TAIWAIN = "tw"
    val LOCALE_ITALIANO = "it"
    val LOCALE_DE = "de"
    open fun Laninit() {
        val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        when (profilePreferences.getString("Lan", LOCALE_ENGLISH)) {
            LOCALE_ENGLISH -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH);
            }
            LOCALE_CHINESE -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE);
            }
            LOCALE_TAIWAIN -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_TAIWAIN);
            }
            LOCALE_ITALIANO -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ITALIANO);
            }
            LOCALE_DE -> {
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_DE);
            }
        }
    }

    open fun GoMenu() {
        //返回首页,清除栈顶
        supportFragmentManager.popBackStack(null, 1)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        Log.e("event", "" + event)
        if (Fraging != null) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        KeyLinsten(event)
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("event", "" + event)
        if (Fraging != null) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        KeyLinsten(event)
        return super.onKeyDown(keyCode, event)
    }


    /**
     * 請求成功
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequestCode ->
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            PermissionCaller.RequestSuccess(permissions[i])
                        } else {
                            PermissionCaller.RequestFalse(permissions[i])
                        }
                    }
                }
        }
    }

    /**
     * 權限請求
     */
    open fun GetPermission(Permissions: Array<String>, caller: Permission_C, RequestCode: Int) {
        PermissionCaller = caller
        permissionRequestCode = RequestCode
        val permissionDeniedList = ArrayList<String>()
        for (permission in Permissions) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                caller.RequestSuccess(permission)
            } else {
                permissionDeniedList.add(permission)
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            val deniedPermissions = permissionDeniedList.toTypedArray()
            ActivityCompat.requestPermissions(this, deniedPermissions, permissionRequestCode)
        }
    }

    //===============================Abstract Function===============================
    /**
     * 父頁面的載入
     */
    abstract fun ViewInit(rootview: View)

    /**
     * 頁面切換監聽
     */
    abstract fun ChangePageListener(tag: String, frag: Fragment);

    /**
     * 按鍵的監聽
     */
    abstract fun KeyLinsten(event: KeyEvent)
}
