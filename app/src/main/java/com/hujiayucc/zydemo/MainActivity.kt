package com.hujiayucc.zydemo

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.hujiayucc.zydemo.databinding.ActivityMainBinding
import java.util.*

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView

    private val array = arrayOf(
        1668240681L,3496107181L,2842367998L,2606841932L,2122724179L,
        1760680439L,845591552L,2753864080L,1041237580L,2529179483L,
        1835533860L,2069159116L,2401179920L,514922356L,2997097361L,
        3102519042L,2962981492L,227231996L,2556359099L,1634131462L,
        2738153160L,1746196846L,2659271648L,1405332684L,508288093L,
        1920170936L,1531646225L,2715383229L,3250271197L
    )
    private val list = ArrayList<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        initView()

        Thread {
            var i = 0
            for (qq in array) {
                list.add(qq)
                if (textView.text.isEmpty())
                    textView.text = "参与者名单：\n$qq"
                else
                    textView.text = "${textView.text}、$qq"
                i++
                if (i == array.size) textView.text = "${textView.text}\n共计：${i}人"
            }
        }.start()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        textView = binding.textView
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.start -> {
                Toast.makeText(applicationContext,"开始开奖",Toast.LENGTH_LONG).show()
                var i = 0
                Thread {
                    while (i < 19) {
                        if (i != 18) Thread.sleep(5000)
                        val name = list.get(radom(list.size))
                        winning(name,i)
                        remove(name)
                        i++
                    }
                }.start()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 中奖者
     * @param qq 中奖者名字
     * @param rank 中奖名次
     */
    private fun winning(qq: Long, rank: Int) {
        when (rank) {
            //一等奖一位
            0 -> runOnUiThread { textView.text = "${textView.text}\n\n一等奖：$qq" }
            //二等奖三位
            1,2,3 -> runOnUiThread { textView.text = "${textView.text}\n二等奖：$qq" }
            //三等奖五位
            4,5,6,7,8 -> runOnUiThread { textView.text = "${textView.text}\n三等奖：$qq" }
            //参与奖十位
            9,10,11,12,13,14,15,16,17,18 -> runOnUiThread { textView.text = "${textView.text}\n参与奖：$qq" }

            else -> {
                runOnUiThread { textView.text = "${textView.text}\n\n开奖完成" }
                copyText(textView.text)
            }
        }
    }

    /**
     *  获取随机数
     *  @param bound 数字范围
     *  @return 0-bound 之间的随机数
     */
    private fun radom(bound: Int): Int {
        val random = Random()
        return random.nextInt(bound)
    }

    /** 防止重复中奖 */
    private fun remove(qq: Long) {
        while (list.indexOf(qq) != -1) {
            list.remove(qq)
        }
    }

    /** 复制文本 */
    private fun copyText(text: CharSequence) {
        val cmb: ClipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.setPrimaryClip(ClipData.newPlainText(null, text))
    }
}