package cn.teach.equip.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cn.teach.equip.R
import cn.teach.equip.api.HttpResultSubscriber
import cn.teach.equip.api.HttpServerImpl
import cn.teach.equip.base.BaseActivity
import com.blankj.utilcode.util.StringUtils

/**
 * 反馈界面
 */
class FanKuiActivity : BaseActivity(),View.OnClickListener {

    var etFankui: EditText? = null
    var commit: TextView? = null
    var cancle: TextView? = null

    override fun getLayout(): Int {
        return R.layout.act_fankui
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goBack()
        setTitleText("建议反馈")

        etFankui = findViewById(R.id.et_fankui)
        commit = findViewById(R.id.commit)
        cancle = findViewById(R.id.cancle)
        commit?.setOnClickListener (this)
        cancle?.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.commit -> {
                val fankui = etFankui?.text.toString().trim()
                if (StringUtils.isEmpty(fankui)) {
                    showToast2("请填写反馈！")
                    return
                }
                HttpServerImpl.feedback(fankui).subscribe(object : HttpResultSubscriber<String?>() {
                    override fun onSuccess(t: String?) {
                        showToast2("提交成功！")
                    }

                    override fun onFiled(message: String) {
                        showToast2(message)
                    }
                })
            }
            R.id.cancle -> finish()
        }
    }
}