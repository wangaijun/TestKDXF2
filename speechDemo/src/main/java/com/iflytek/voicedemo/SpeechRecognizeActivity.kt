package com.iflytek.voicedemo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.widget.Toast
import com.iflytek.cloud.*
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import com.iflytek.speech.setting.SpeechRecognizeSettings
import com.iflytek.speech.util.JsonParser
import kotlinx.android.synthetic.main.activity_speech_recognize.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SpeechRecognizeActivity : Activity(), OnClickListener {
    // 语音听写对象
    private var speechRecognizer: SpeechRecognizer? = null
    // 语音听写UI
    private var recognizerDialog: RecognizerDialog? = null
    // 用HashMap存储听写结果
    private val recognizeResults = LinkedHashMap<String, String>()

    private var toast: Toast? = null
    // 获取设置结果
    private var sharedPreferences: SharedPreferences? = null
    // 引擎类型
    private val engineType = SpeechConstant.TYPE_CLOUD

    private var ret = 0 // 函数调用返回值

    /**
     * 初始化监听器。
     */
    private val mInitListener = InitListener { code ->
        Log.d(TAG, "SpeechRecognizer init() code = $code")
        if (code != ErrorCode.SUCCESS) {
            showTip("初始化失败，错误码：$code")
        }
    }

    /**
     * 听写监听器。
     */
    private val mRecognizerListener = object : RecognizerListener {
        override fun onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话")
        }

        override fun onError(error: SpeechError) {
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            showTip(error.getPlainDescription(true))
        }

        override fun onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话")
        }

        override fun onResult(results: RecognizerResult, isLast: Boolean) {
            Log.d(TAG, results.resultString)
            printResult(results)

            if (isLast) {
                // TODO 最后的结果
            }
        }

        override fun onVolumeChanged(volume: Int, data: ByteArray) {
            showTip("当前正在说话，音量大小：$volume")
            Log.d(TAG, "返回音频数据：${data.size}")
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    }

    /**
     * 听写UI监听器
     */
    private val mRecognizerDialogListener = object : RecognizerDialogListener {
        override fun onResult(results: RecognizerResult, isLast: Boolean) {
            printResult(results)
        }

        /**
         * 识别回调错误.
         */
        override fun onError(error: SpeechError) {
            showTip(error.getPlainDescription(true))
        }

    }


    @SuppressLint("ShowToast")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_speech_recognize)

        setViewListener()
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        speechRecognizer = SpeechRecognizer.createRecognizer(this, mInitListener)

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        recognizerDialog = RecognizerDialog(this, mInitListener)

        sharedPreferences = getSharedPreferences(SpeechRecognizeSettings.PREFER_NAME,
                Activity.MODE_PRIVATE)
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
    }

    /**
     * 设置控件的事件处理程序
     */
    private fun setViewListener() {
        iat_recognize.setOnClickListener(this)
        iat_cancel.setOnClickListener(this)
        image_iat_set.setOnClickListener(this)
        btnPlayVoice.setOnClickListener(this)
        save.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (null == speechRecognizer) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化")
            return
        }

        when (view.id) {
        // 进入参数设置页面
            R.id.image_iat_set -> {
                val intents = Intent(this, SpeechRecognizeSettings::class.java)
                startActivity(intents)
            }
        // 开始听写
        // 如何判断一次听写结束：OnResult isLast=true 或者 onError
            R.id.iat_recognize -> {
                etResult.text = null// 清空显示内容
                recognizeResults.clear()
                // 设置参数
                setParam()
                val isShowDialog = sharedPreferences!!.getBoolean(
                        getString(R.string.pref_key_iat_show), true)
                if (isShowDialog) {
                    // 显示听写对话框
                    recognizerDialog!!.setListener(mRecognizerDialogListener)
                    recognizerDialog!!.show()
                    showTip(getString(R.string.text_begin))
                } else {
                    // 不显示听写对话框
                    ret = speechRecognizer!!.startListening(mRecognizerListener)
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：$ret")
                    } else {
                        showTip(getString(R.string.text_begin))
                    }
                }
            }
         // 取消听写
            R.id.iat_cancel -> {
                finish()
            }
            R.id.btnPlayVoice->{
                val mp = MediaPlayer()
                mp.setDataSource(Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
                mp.prepare()
                mp.start()
            }
            R.id.save->{
                val intent = Intent()
                intent.putExtra("txt",etResult.text.toString())
                intent.putExtra("path",Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
                setResult(RESULT_OK,intent)
                finish()
            }
            else -> {
            }
        }
    }

    private fun printResult(results: RecognizerResult) {
        val text = JsonParser.parseIatResult(results.resultString)

        var sn: String? = null
        // 读取json结果中的sn字段
        try {
            val resultJson = JSONObject(results.resultString)
            sn = resultJson.optString("sn")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        recognizeResults.put(sn!!, text)

        val resultBuffer = StringBuffer()
        for (key in recognizeResults.keys) {
            resultBuffer.append(recognizeResults[key])
        }

        etResult.setText(resultBuffer.toString())
        etResult.setSelection(etResult.length())
    }

    private fun showTip(str: String) {
        toast!!.setText(str)
        toast!!.show()
    }

    /**
     * 参数设置
     *
     * @return
     */
    fun setParam() {
        // 清空参数
        speechRecognizer!!.setParameter(SpeechConstant.PARAMS, null)

        // 设置听写引擎
        speechRecognizer!!.setParameter(SpeechConstant.ENGINE_TYPE, engineType)
        // 设置返回结果格式
        speechRecognizer!!.setParameter(SpeechConstant.RESULT_TYPE, "json")

        val lag = sharedPreferences!!.getString("iat_language_preference",
                "mandarin")
        if (lag == "en_us") {
            // 设置语言
            speechRecognizer!!.setParameter(SpeechConstant.LANGUAGE, "en_us")
            speechRecognizer!!.setParameter(SpeechConstant.ACCENT, null)

        } else {
            // 设置语言
            speechRecognizer!!.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
            // 设置语言区域
            speechRecognizer!!.setParameter(SpeechConstant.ACCENT, lag)

        }
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        speechRecognizer!!.setParameter(SpeechConstant.VAD_BOS, sharedPreferences!!.getString("iat_vadbos_preference", "4000"))

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        speechRecognizer!!.setParameter(SpeechConstant.VAD_EOS, sharedPreferences!!.getString("iat_vadeos_preference", "1000"))

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        speechRecognizer!!.setParameter(SpeechConstant.ASR_PTT, sharedPreferences!!.getString("iat_punc_preference", "1"))

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        speechRecognizer!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        speechRecognizer!!.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (null != speechRecognizer) {
            // 退出时释放连接
            speechRecognizer!!.cancel()
            speechRecognizer!!.destroy()
        }
    }

    companion object {
        private val TAG = SpeechRecognizeActivity::class.simpleName
    }
}
