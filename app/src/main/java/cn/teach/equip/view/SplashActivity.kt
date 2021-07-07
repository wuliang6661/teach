package cn.teach.equip.view

import android.os.Bundle
import android.os.Handler
import cn.teach.equip.R
import cn.teach.equip.api.HttpResultSubscriber
import cn.teach.equip.api.HttpServerImpl
import cn.teach.equip.base.BaseActivity
import cn.teach.equip.base.MyApplication
import cn.teach.equip.bean.pojo.UserBO
import cn.teach.equip.view.main.MainActivity
import cn.teach.equip.view.splash.SplashPage1
import com.blankj.utilcode.util.StringUtils

class SplashActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val isFirst = MyApplication.spUtils.getBoolean("isFirst", true)
            if (!isFirst) {
                val token = MyApplication.spUtils.getString("token")
                if (StringUtils.isEmpty(token)) {
                    gotoActivity(MainActivity::class.java, true)
                } else {
                    MyApplication.token = token
                    login(null, null)
                }
            } else {
                MyApplication.spUtils.put("isFirst", false)
                gotoActivity(SplashPage1::class.java, true)
            }
        }, 3000)
    }

    fun login(phone: String?, smsCode: String?) {
        HttpServerImpl.login(phone, smsCode).subscribe(object : HttpResultSubscriber<UserBO?>() {
            override fun onSuccess(t: UserBO?) {
                MyApplication.token = t?.userToken
                MyApplication.userBO = t
                MyApplication.spUtils.put("token", MyApplication.token)
                gotoActivity(MainActivity::class.java, true)
            }

            override fun onFiled(message: String) {
                MyApplication.token = null
                gotoActivity(MainActivity::class.java, true)
            }
        })
    }
}