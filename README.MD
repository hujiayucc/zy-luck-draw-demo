### 参与名单

**1668240681 3496107181 2842367998 2606841932 2122724179 1760680439 
845591552 2753864080 1041237580 2529179483 1835533860 2069159116 2401179920 514922356 2997097361 3102519042 
2962981492 227231996 2556359099 1634131462 2738153160 1746196846 2659271648 1405332684 508288093 1920170936 
1531646225 2715383229 3250271197**

### 代码块
- 抽奖名单变量
````kotlin
//QQ号
private val array = arrayOf(
    1668240681L,3496107181L,2842367998L,2606841932L,2122724179L,
    1760680439L,845591552L,2753864080L,1041237580L,2529179483L,
    1835533860L,2069159116L,2401179920L,514922356L,2997097361L,
    3102519042L,2962981492L,227231996L,2556359099L,1634131462L,
    2738153160L,1746196846L,2659271648L,1405332684L,508288093L,
    1920170936L,1531646225L,2715383229L,3250271197L
)
````

- 抽奖名单加载
````kotlin
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
````

- 开奖代码
````kotlin
while (i < 19) {
    //开奖延迟
    if (i != 18) Thread.sleep(3000)
    val name = list.get(radom(list.size))
    winning(name,i)
    remove(name)
    i++
}
````

- 抽奖算法
````kotlin
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
````