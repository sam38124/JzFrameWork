package com.orange.jzchi.jzframework.MtLanguage

import android.content.Context
import android.util.Log
import com.example.jztaskhandler.TaskHandler
import com.jzsql.lib.mmySql.JzSqlHelper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class FixLanguage {
    companion object{
        private var instance:FixLanguage? = null
        val newInstance:FixLanguage
            get() {
                if(instance==null){
                    instance=FixLanguage()
                }
                return instance!!
            }

    }
    fun  setUP(context: Context, url:String, result:callback){

//        sqlHelper= JzSqlHelper(context,"language").create()
//        sqlHelper.exsql("CREATE TABLE if not exists fixlan (\n" +
//                "    repl   VARCHAR PRIMARY KEY,\n" +
//                "    replto VARCHAR\n" +
//                ");\n")
//
//        val profilePreferences =context.getSharedPreferences("Setting", Context.MODE_PRIVATE)
//        val versionNow=profilePreferences.getString("fixLanVersion","nodata")
//        Log.e("versionNow",versionNow)
//        Thread{
//
//            TaskHandler.newInstance.runTaskOnce("getFix", runner {
//                try{
//                    val url: HttpURLConnection =(URL(url).openConnection()) as HttpURLConnection
////                    url.connectTimeout=10*1000
////                    val dataStram = InputStreamReader(url.inputStream,"utf-8")
////                    var reader= BufferedReader(dataStram)
////                    var first=true
////                    var version=""
////                    var a=reader.readLine()
////                    while (a != null){
////                        if(first){
////                            first=false
////                            if(versionNow==converFix.Data(a)[0].value){
////                                break
////                            }else{
////                                sqlHelper.dropTb("fixlan")
////                                sqlHelper.exsql("CREATE TABLE if not exists fixlan (\n" +
////                                        "    repl   VARCHAR PRIMARY KEY,\n" +
////                                        "    replto VARCHAR\n" +
////                                        ");\n")
////                                version=converFix.Data(a)[0].value
////                            }
////                        }else{
////                            val s=converFix.Data(a)
////                            for(data in s){
////                                sqlHelper.exsql("insert or replace into fixlan(repl,replto) values ('${data.name.replace("'","''")}','${data.value.replace("'","''")}')")
////                            }
////                        }
////                        a=reader.readLine()
////                    }
////                    profilePreferences.edit().putString("fixLanVersion",version).commit()
////                    result.result(true)
//                }catch (e: Exception){e.printStackTrace()
//                    result.result(false)
//                }
//            })
//        }.start()

    }
    lateinit var sqlHelper: JzSqlHelper
}
interface callback{
    fun result(a:Boolean)
}