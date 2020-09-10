package com.orange.jzchi.jzframework

import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.app.Instrumentation
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jzsql.lib.mmySql.JzSqlHelper
import com.orange.jzchi.R
import com.orange.jzchi.jzframework.callback.*
import com.orange.jzchi.jzframework.tool.LanguageUtil
import com.orange.jzchi.jzframework.tool.VersionCheck
import com.orange.jzchi.jzframework.util.Download
import com.orange.jzchi.jzframework.util.PackageInformation
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

abstract class JzActivity : AppCompatActivity(),
    FragmentManager.OnBackStackChangedListener {
    companion object {
        var fragid = 0
        private lateinit var Switch_Instance: control
        const val Orientation_Vertical = 0;
        const val Orientation_Horizontal = 1;
        const val Orientation_Default = 2;
        private fun setSwitchInstance(instance: control) {
            Switch_Instance = instance
        }

        fun getControlInstance(): control {
            return Switch_Instance
        }
    }

    var onActivityResultCallback: onActivityResultCallback? = null
    private lateinit var rootshare: RootShare
    val LayoutId = R.layout.activity_root
    val FragId = R.id.frag_root
    var handler = Handler()
    lateinit var mOrientationListener: OrientationEventListener;
    private var permissionRequestCode = 10
    var Fraging: Fragment? = null
    var FragName = ""
    var mDialog = ArrayList<DiaClass>()
    lateinit var permissionCaller: permission_C
    lateinit var rootview: View
    lateinit var NavagationRoot: DrawerLayout
    lateinit var NavaGationFrag: JzFragement
    override fun onBackStackChanged() {
        Fraging = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]
        if (Fraging != null) {
            FragName = Fraging!!.tag!!
            changePageListener(FragName, Fraging!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceAble()) {
            super.onCreate(savedInstanceState)
        } else {
            super.onCreate(null)
        }
        setSwitchInstance(object : control {
            override fun showDiaLog(Layout: Int, cancelable: Boolean, swip: Boolean, tag: String) {
                screenAlwaysOn()
                ShowDaiLog(cancelable, swip, object : SetupDialog(Layout) {
                    override fun setup(rootview: Dialog) {

                    }

                    override fun dismess() {

                    }

                    override fun keyevent(event: KeyEvent): Boolean {
                        return cancelable
                    }
                }, tag, false)
            }

            override fun openAPK() {
                handler.post {
                    val file = File("/sdcard/update/beta.apk");
                    val intent = Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    var data: Uri? = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
                        // "sven.com.fileprovider.fileprovider"即是在清单文件中配置的authorities
                        // 通过FileProvider创建一个content类型的Uri
                        data =
                            FileProvider.getUriForFile(this@JzActivity, "abc.fileprovider", file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
                    } else {
                        data = Uri.fromFile(file);
                    }
                    intent.setDataAndType(data, "application/vnd.android.package-archive");
                    startActivity(intent);
                    closeApp()
                }
            }

            override fun isFrontDesk(): Boolean {
                return appOnForeground()
            }

            override fun screenAlwaysOn() {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

            override fun cancelAlwaysOn() {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

            override fun getAppInformation(): PackageInformation {
                return PackageInformation()
            }

            override fun restart(a: Class<*>) {
                getControlInstance().closeDiaLog()
                val intent = Intent(applicationContext, a)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this@JzActivity.startActivity(intent)
                android.os.Process.killProcess(android.os.Process.myPid())
            }

            override fun checkUpdate(a: Boolean): String? {
                val versionChecker = VersionCheck()
                versionChecker.packagename = applicationContext.packageName
                try {
                    if (!a) {
                        return versionChecker.execute().get()
                    }
                    val mLatestVersionName = versionChecker.execute().get()
                    if (PackageInformation().getVersionName().toDouble() != java.lang.Double.parseDouble(
                            mLatestVersionName
                        )
                    ) {
                        goStore()

                    }
                    return mLatestVersionName
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
            }

            override fun goStore() {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("https://play.google.com/store/apps/details?id=${applicationContext.packageName}")
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun getHandler(): Handler {
                return handler
            }

            override fun setOnActivityResultCallback(callback: onActivityResultCallback) {
                onActivityResultCallback = callback
            }

            override fun getNowPage(): Fragment? {
                return Fraging
            }

            override fun getNowPageTag(): String {
                return FragName
            }

            override fun hideKeyBoard() {
                HideKeyBoard()
            }

            override fun setLanguage(local: Locale) {
                this@JzActivity.setLanguage(local)
            }

            override fun getLanguage(): Locale {
                val local = getControlInstance().getPro("Language_Local", "nodata")
                val country = getControlInstance().getPro("Language_Country", "nodata")
                if (getControlInstance().getPro("Language_Local", "nodata") != "nodata") {
                    return Locale(local, country)
                } else {
                    return Locale.getDefault()
                }
            }

            override fun findFragByTag(a: String): Fragment? {
                return FindfragByTag(a)
            }

            override fun getRootActivity(): JzActivity {
                return this@JzActivity
            }

            override fun showCustomDaiLog(
                cancelable: Boolean,
                style: Int,
                caller: SetupDialog,
                tag: String
            ) {
                screenAlwaysOn()
                ShowDaiLog(cancelable, style, caller, tag, false)
            }

            override fun showBottomSheetDialog(
                cancelable: Boolean,
                swip: Boolean,
                caller: SetupDialog,
                tag: String
            ) {
                ShowDaiLog(cancelable, swip, caller, tag, true)
                return
            }

            override fun getDialog(tag: String): DiaClass? {
                for (i in mDialog) {
                    if (i.tag == tag) {
                        return i
                    }
                }
                return null
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

            override fun permissionRequest(
                Permissions: Array<String>,
                caller: permission_C,
                RequestCode: Int
            ) {
                GetPermission(Permissions, caller, RequestCode)
            }


            override fun showDiaLog(
                cancelable: Boolean,
                swip: Boolean,
                caller: SetupDialog,
                tag: String
            ) {
                screenAlwaysOn()
                ShowDaiLog(cancelable, swip, caller, tag, false)
            }

            override fun closeDiaLog(tag: String) {
                cancelAlwaysOn()
                DaiLogDismiss(tag)
            }

            override fun closeDiaLog() {
                try {
                    for (i in mDialog) {
                        i.dialog.dismiss()
                    }
                    mDialog.clear()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
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

            override fun clearPro() {
                getSharedPreferences("Setting", Context.MODE_PRIVATE).edit().clear().commit()
            }

            override fun goBack(tag: String) {
                GoBack(tag)
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
                ChangePage(Translation, tag, goback, null)
            }

            override fun changePage(
                Translation: Fragment,
                tag: String,
                goback: Boolean,
                animator: Array<Int>
            ) {
                ChangePage(Translation, tag, goback, animator)
            }

            override fun changeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
                ChangeFrag(Translation, id, tag, goback, null)
            }

            override fun changeFrag(
                Translation: Fragment,
                id: Int,
                tag: String,
                goback: Boolean,
                animator: Array<Int>
            ) {
                ChangeFrag(Translation, id, tag, goback, animator)
            }

            override fun replaceFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean) {
                ReplaceFrag(Translation, id, tag, goback, null)
            }

            override fun replaceFrag(
                Translation: Fragment,
                id: Int,
                tag: String,
                goback: Boolean,
                animator: Array<Int>
            ) {
                ReplaceFrag(Translation, id, tag, goback, animator)
            }

            override fun apkDownload(url: String, callback: DownloadCallback) {
                Download.apkDownload(url, callback)
            }
        })
        rootshare = RootShare(this)
        getControlInstance().setLanguage(getControlInstance().getLanguage())
        setContentView(LayoutId)
        NavagationRoot = findViewById(R.id.drawer)
        supportFragmentManager.addOnBackStackChangedListener(this)
        rootview = findViewById<View>(android.R.id.content).rootView
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
            .commitAllowingStateLoss()
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

    private fun ReplaceFrag(
        Translation: Fragment,
        id: Int,
        tag: String,
        goback: Boolean,
        anim: Array<Int>?
    ) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.replace(id, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            changePageListener(tag, Translation)
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.replace(id, Translation, tag)
                .commit()
        }
    }

    private fun ChangeFrag(
        Translation: Fragment,
        id: Int,
        tag: String,
        goback: Boolean,
        anim: Array<Int>?
    ) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.add(id, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            changePageListener(tag, Translation)
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.add(id, Translation, tag)
                .commit()
        }
    }

    private fun ChangePage(Translation: Fragment, tag: String, goback: Boolean, anim: Array<Int>?) {
        if (goback) {
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.replace(FragId, Translation, tag)
                .addToBackStack(FragName)
                .commit()
        } else {
            Fraging = Translation
            FragName = tag
            Log.d("switch", tag)
            changePageListener(tag, Translation)
            val transaction = supportFragmentManager.beginTransaction()
            if (anim != null) {
                transaction.setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }
            transaction.replace(FragId, Translation, tag)
                .commit()
        }
    }

    private fun FindfragByTag(a: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(a)
    }

    private fun SetHome(Translation: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ChangePage(Translation, tag, false, null)
    }


    private fun Toast(a: String) {
        handler.post {
            android.widget.Toast.makeText(this, a, android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun Toast(id: Int) {
        handler.post {
            android.widget.Toast.makeText(
                this,
                getString(id),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun ShowDaiLog(
        cancelable: Boolean,
        style: Int,
        caller: SetupDialog,
        tag: String, buttomSheet: Boolean
    ) {
        try {

            val showing = getShowing(tag)
            if (showing != null) {
                caller.dialog = showing.callback.dialog
                showing.callback = caller
                showing.callback.setup(showing.dialog)
                return
            }
            val dialog = if (!buttomSheet) object : Dialog(this, style) {
                override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                    if (caller.keyevent(event)) {
                        return super.dispatchKeyEvent(event)
                    } else {
                        return false
                    }
                }

                override fun dismiss() {
                    super.dismiss()
                    clearDialog(tag)
                    caller.dismess()
                }
            } else object : BottomSheetDialog(this, style) {
                override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                    if (caller.keyevent(event)) {
                        return super.dispatchKeyEvent(event)
                    } else {
                        return false
                    }
                }

                override fun dismiss() {
                    super.dismiss()
                    clearDialog(tag)
                    caller.dismess()
                }
            }
            caller.dialog = dialog
            dialog.setContentView(caller.layoutId)
            if (buttomSheet) {
                dialog.setOnShowListener {
                    setupFullHeight(dialog as BottomSheetDialog);
                }
            }
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(cancelable)
            dialog.show()
            if (cancelable) {
                getAllChildViews(dialog.window!!.getDecorView(), dialog)
            }
            caller.setup(dialog)
            dialog.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            val addclass = DiaClass()
            addclass.dialog = dialog
            addclass.tag = tag
            addclass.callback = caller
            mDialog.add(addclass)
            dialogLinstener(dialog, tag)
            if (buttomSheet) {
                dialog.setOnShowListener {
                    setupFullHeight(dialog as BottomSheetDialog);
                }
                BottomSheetBehavior.from(dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet))
                    .setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        var isopen = false
                        override fun onStateChanged(p0: View, p1: Int) {
                            Log.e("onStateChanged",""+p1)
                            if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                                if (isopen) {
                                    dialog.dismiss()
                                }
                            } else if (p1 == BottomSheetBehavior.STATE_EXPANDED) {
                                isopen = true
                            } else if (p1 != BottomSheetBehavior.STATE_DRAGGING&&p1!=BottomSheetBehavior.STATE_SETTLING) {
                                if (isopen) {
                                    dialog.dismiss()
                                }
                            }
                        }

                        override fun onSlide(p0: View, p1: Float) {

                        }
                    })
            }
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    private fun ShowDaiLog(
        cancelable: Boolean,
        swip: Boolean,
        caller: SetupDialog,
        tag: String, buttomSheet: Boolean
    ) {
        try {

            val showing = getShowing(tag)
            if (showing != null) {
                caller.dialog = showing.callback.dialog
                showing.callback = caller
                showing.callback.setup(showing.dialog)
                return
            }
            val dialog = if (!buttomSheet) object :
                Dialog(this, if (swip) R.style.SwipTheme else R.style.MyDialog) {
                override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                    if (caller.keyevent(event)) {
                        return super.dispatchKeyEvent(event)
                    } else {
                        return false
                    }
                }

                override fun dismiss() {
                    super.dismiss()
                    clearDialog(tag)
                    caller.dismess()
                }
            } else object :
                BottomSheetDialog(this, if (swip) R.style.DownToUPSwip else R.style.DownToUP) {
                override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                    if (caller.keyevent(event)) {
                        return super.dispatchKeyEvent(event)
                    } else {
                        return false
                    }
                }

                override fun dismiss() {
                    super.dismiss()
                    clearDialog(tag)
                    caller.dismess()
                }
            }
            caller.dialog = dialog
            dialog.setContentView(caller.layoutId)
            if (buttomSheet) {
                dialog.setOnShowListener {
                    setupFullHeight(dialog as BottomSheetDialog);
                }
            }

            dialog.getWindow()!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(cancelable)
            dialog.show()
            if (cancelable) {
                getAllChildViews(dialog.window!!.decorView, dialog)
            }
            caller.setup(dialog)
            dialog.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            val addclass = DiaClass()
            addclass.dialog = dialog
            addclass.tag = tag
            addclass.callback = caller
            mDialog.add(addclass)
            dialogLinstener(dialog, tag)
            if (buttomSheet) {
                BottomSheetBehavior.from(dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet))
                    .setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        var isopen = false
                        override fun onStateChanged(p0: View, p1: Int) {
                            Log.e("onStateChanged",""+p1)
                            if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                                if (isopen) {
                                    dialog.dismiss()
                                }
                            } else if (p1 == BottomSheetBehavior.STATE_EXPANDED) {
                                isopen = true
                            } else if (p1 != BottomSheetBehavior.STATE_DRAGGING&&p1!=BottomSheetBehavior.STATE_SETTLING) {
                                if (isopen) {
                                    dialog.dismiss()
                                }
                            }
                        }

                        override fun onSlide(p0: View, p1: Float) {
                            Log.e("ondlide", "$p1")
                        }
                    })
            }
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    fun setupFullHeight(dia: Dialog) {
        val bottomSheetDialog = dia as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private fun getAllChildViews(view: View, dia: Dialog): List<View> {
        val allchildren = ArrayList<View>()
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val viewchild = view.getChildAt(i)
                allchildren.add(viewchild)
                Log.d("ChildView", "$viewchild")
                allchildren.addAll(getAllChildViews(viewchild, dia))
                if ("$viewchild".contains("RelativeLayout")) {
                    viewchild.setOnClickListener { dia.dismiss() }
                    return allchildren
                }
            }
        }
        return allchildren
    }

    private fun clearDialog(tag: String) {
        val a = ArrayList<DiaClass>()
        for (i in mDialog) {
            if (i.tag != tag) {
                a.add(i)
            }
        }
        mDialog = a
    }

    private fun DaiLogDismiss(tag: String) {
        try {
            val a = ArrayList<DiaClass>()
            for (i in mDialog) {
                if (i.tag == tag) {
                    i.callback.dismess()
                    i.dialog.dismiss()
                } else {
                    a.add(i)
                }
            }
            mDialog = a
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    private fun setLanguage(local: Locale) {
        getControlInstance().setPro("Language_Local", local.language)
        getControlInstance().setPro("Language_Country", local.country)
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
        if (Fraging != null && Fraging is DiapathKey) {
            (Fraging as DiapathKey).dispatchKeyEvent(event)
        }//按鍵分發
        return if (keyEventListener(event)) {
            super.dispatchKeyEvent(event)
        } else {
            false
        }
    }

    fun getShowing(tag: String): DiaClass? {
        for (i in mDialog) {
            if (i.tag == tag) {
                return i
            }
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (onActivityResultCallback == null) {
            return
        }
        onActivityResultCallback!!.setonResultLinstner(requestCode, resultCode, data)
    }
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        Log.e("event", "" + event)
//        if (Fraging != null) {
//            (Fraging as DiapathKey).dispatchKeyEvent(event)
//        }//按鍵分發
//        return if(keyEventListener(event)){
//            super.dispatchKeyEvent(event)
//        }else{
//            false
//        }
//    }
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

    open fun appOnForeground(): Boolean {
        val appProcesses: List<ActivityManager.RunningAppProcessInfo> =
            (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
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
    abstract fun keyEventListener(event: KeyEvent): Boolean

    /**
     * 是否saveinstance
     */
    abstract fun savedInstanceAble(): Boolean

    /**
     * Dialog監聽
     */
    abstract fun dialogLinstener(dialog: Dialog, tag: String)
}

class DiaClass {
    var tag = ""
    lateinit var dialog: Dialog
    lateinit var callback: SetupDialog
}

interface onActivityResultCallback {
    fun setonResultLinstner(requestCode: Int, resultCode: Int, data: Intent?)
}