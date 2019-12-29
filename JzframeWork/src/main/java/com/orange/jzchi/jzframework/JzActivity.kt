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
import com.orange.jzchi.jzframework.callback.*
import com.orange.jzchi.jzframework.tool.LanguageUtil
import java.util.*

abstract class JzActivity : AppCompatActivity(),
    FragmentManager.OnBackStackChangedListener {
    companion object {
        private lateinit var Switch_Instance: control
        val Orientation_Vertical = 0;
        val Orientation_Horizontal = 1;
        val Orientation_Default = 2;
        private fun setSwitchInstance(instance: control) {
            Switch_Instance = instance
        }

        fun getControlInstance(): control {
            return Switch_Instance
        }
    }

    private lateinit var rootshare: RootShare
    val LayoutId = R.layout.activity_root
    val FragId = R.id.frag_root
    var handler = Handler()
    lateinit var mOrientationListener: OrientationEventListener;
    private var permissionRequestCode = 10
    var Fraging: Fragment? = null
    var FragName = ""
    lateinit var permissionCaller: permission_C
    lateinit var rootview: View
    lateinit var NavagationRoot: DrawerLayout
    lateinit var NavaGationFrag: JzFragement
    override fun onBackStackChanged() {
        Fraging = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]
        if(Fraging!=null){
            FragName = Fraging!!.tag!!
            changePageListener(FragName, Fraging!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutId)
        rootshare = RootShare(this)
        NavagationRoot = findViewById(R.id.drawer)
        supportFragmentManager.addOnBackStackChangedListener(this)
        rootview = findViewById<View>(android.R.id.content).rootView
        setSwitchInstance(object : control {
            override fun hideKeyBoard() {
                HideKeyBoard()
            }

            override fun setLanguage(local: Locale) {
                this@JzActivity.setLanguage(local)
            }

            override fun findFragByTag(a: String): Fragment? {
                return FindfragByTag(a)
            }

            override fun getRootActivity(): JzActivity {
                return this@JzActivity
            }

            override fun showCustomDaiLog(Layout: Int, cancelable: Boolean, style: Int, caller: SetupDialog) {
                ShowDaiLog(Layout, cancelable, style, caller)
            }

            override fun toast(a: String) {
                Toast(a)
            }

            override fun toast(a: Int) {
                Toast(a)
            }

            override fun refreshDrawer() {
                RefreshNavaGation()
            }

            override fun setDrawer(frag: JzFragement) {
                SetNavaGation(frag)
            }

            override fun openDrawer() {
                OpenNavaGation()
            }

            override fun closeDrawer() {
                CloseDrawer()
            }

            override fun setOrientation(a: Int) {
                SetOrientation(a)
            }

            override fun closeApp() {
                CloseApp()
            }

            override fun permissionRequest(Permissions: Array<String>, caller: permission_C, RequestCode: Int) {
                GetPermission(Permissions, caller, RequestCode)
            }

            override fun showDiaLog(Layout: Int, touchCancel: Boolean, swip: Boolean, caller: SetupDialog) {
                ShowDaiLog(Layout, touchCancel, swip, caller)
            }

            override fun closeDiaLog() {
                DaiLogDismiss()
            }

            override fun setPro(key: String, value: Boolean) {
                rootshare.SetPro(key, value)
            }

            override fun setPro(key: String, value: String) {
                rootshare.SetPro(key, value)
            }

            override fun setPro(key: String, value: Int) {
                rootshare.SetPro(key, value)
            }

            override fun getPro(key: String, value: String): String {
                return rootshare.GetPro(key, value)
            }

            override fun getPro(key: String, value: Boolean): Boolean {
                return rootshare.GetPro(key, value)
            }

            override fun getPro(key: String, value: Int): Int {
                return rootshare.GetPro(key, value)
            }

            override fun goBack(a: String) {
                GoBack(a)
            }

            override fun goBack(a: Int) {
                GoBack(a)
            }

            override fun goBack() {
                GoBack()
            }

            override fun goMenu() {
                GoMenu()
            }

            override fun setHome(Translation: Fragment, tag: String) {
                SetHome(Translation, tag)
            }

            override fun changePage(Translation: Fragment, tag: String, goback: Boolean) {
                ChangePage(Translation, tag, goback)
            }

            override fun changeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
                ChangeFrag(Translation, id, tag, goback)
            }
        })
        NavagationRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        viewInit(rootview)
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

    private fun GoBack() {
        supportFragmentManager.popBackStack()
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    private fun GoBack(a: Int) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    private fun GoBack(a: String) {
        supportFragmentManager.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun HideKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0)
    }

    private fun CloseDrawer() {
        NavagationRoot.closeDrawer(GravityCompat.START)
    }


    private fun SetNavaGation(frag: JzFragement) {
        NavagationRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        NavaGationFrag = frag
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navigation_view, frag)
            .commit()
    }

    private fun RefreshNavaGation() {
        if (::NavaGationFrag.isInitialized) {
            NavaGationFrag.viewInit()
        }
    }

    private fun OpenNavaGation() {
        HideKeyBoard()
        NavagationRoot.openDrawer(GravityCompat.START)
    }

    private fun ChangeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(id, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            changePageListener(tag, Translation)
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation, tag)
                .commit()
        }
    }

    private fun FindfragByTag(a: String): Fragment? {
        val a = supportFragmentManager.findFragmentByTag(a)
        return a
    }

    private fun SetHome(Translation: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ChangePage(Translation, tag, false)
    }

    private fun ChangePage(Translation: Fragment, tag: String, goback: Boolean) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            changePageListener(tag, Translation)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(FragId, Translation, tag)
                .commit()
        }
    }

    private fun Toast(a: String) {
        handler.post { android.widget.Toast.makeText(this, a, android.widget.Toast.LENGTH_SHORT).show() }
    }

    private fun Toast(id: Int) {
        handler.post { android.widget.Toast.makeText(this, getString(id), android.widget.Toast.LENGTH_SHORT).show() }
    }

    var mDialog: Dialog? = null
    lateinit var DiaCaller: SetupDialog
    private fun ShowDaiLog(Layout: Int, cancelable: Boolean, style: Int, caller: SetupDialog) {
        try {
            if (mDialog == null) {
                mDialog = object : Dialog(this, style) {
                    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                        if (caller.keyevent(event)) {
                            return super.dispatchKeyEvent(event)
                        } else {
                            return false
                        }
                    }

                    override fun dismiss() {
                        super.dismiss()
                        caller.dismess()
                    }
                }
                mDialog!!.setContentView(Layout)
                mDialog!!.window!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(cancelable)
                mDialog!!.show()
                if (cancelable) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = object : Dialog(this, style) {
                        override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                            if (caller.keyevent(event)) {
                                return super.dispatchKeyEvent(event)
                            } else {
                                return false
                            }
                        }

                        override fun dismiss() {
                            super.dismiss()
                            caller.dismess()
                        }
                    }
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(cancelable)
                    mDialog!!.show()
                    if (cancelable) {
                        getAllChildViews(mDialog!!.getWindow().getDecorView())
                    }
                }
            }
            caller.setup(mDialog!!)
            DiaCaller = caller
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    private fun ShowDaiLog(Layout: Int, cancelable: Boolean, swip: Boolean, caller: SetupDialog) {
        try {
            if (mDialog == null) {
                mDialog = object : Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog) {
                    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                        if (caller.keyevent(event)) {
                            return super.dispatchKeyEvent(event)
                        } else {
                            return false
                        }
                    }

                    override fun dismiss() {
                        super.dismiss()
                        caller.dismess()
                    }
                }
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(cancelable)
                mDialog!!.show()
                if (cancelable) {
                    getAllChildViews(mDialog!!.getWindow().getDecorView())
                }
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = object : Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog) {
                        override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                            if (caller.keyevent(event)) {
                                return super.dispatchKeyEvent(event)
                            } else {
                                return false
                            }
                        }

                        override fun dismiss() {
                            super.dismiss()
                            caller.dismess()
                        }
                    }
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(cancelable)
                    mDialog!!.show()
                    if (cancelable) {
                        getAllChildViews(mDialog!!.window.decorView)
                    }
                }
            }
            caller.setup(mDialog!!)
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

    private fun DaiLogDismiss() {
        try {
            DiaCaller.dismess()
            mDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    private fun setLanguage(local: Locale) {
        LanguageUtil.updateLocale(this, local);

    }

    private fun CloseApp() {
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    private fun GoMenu() {
        //返回首页,清除栈顶
        supportFragmentManager.popBackStack(null, 1)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        Log.e("event", "" + event)
        if (Fraging != null) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        keyEventListener(event)
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("event", "" + event)
        if (Fraging != null) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        keyEventListener(event)
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
                            permissionCaller.requestSuccess(permissions[i])
                        } else {
                            permissionCaller.requestFalse(permissions[i])
                        }
                    }
                }
        }
    }

    /**
     * 權限請求
     */
    private fun GetPermission(Permissions: Array<String>, caller: permission_C, RequestCode: Int) {
        permissionCaller = caller
        permissionRequestCode = RequestCode
        val permissionDeniedList = ArrayList<String>()
        for (permission in Permissions) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                caller.requestSuccess(permission)
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
    abstract fun viewInit(rootview: View)

    /**
     * 頁面切換監聽
     */
    abstract fun changePageListener(tag: String, frag: Fragment);

    /**
     * 按鍵的監聽
     */
    abstract fun keyEventListener(event: KeyEvent)
}
