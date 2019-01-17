package com.example.yervand.puzzlelistviewsample.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.db.model.CodexEntity
import com.example.yervand.puzzlelistviewsample.util.TextSurroundSpan
import com.example.yervand.puzzlelistviewsample.util.convertDpToPixel
import io.realm.Realm


class MainActivity : AppCompatActivity() {

    private var textFirst: TextView? = null
    private var textSecond: TextView? = null
    private var concatBtn: TextView? = null
    private var parent: LinearLayout? = null
    private lateinit var recyclerView: RecyclerView
    private var adapter: SpannableMergeAdapter? = null

    private lateinit var dataSet: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataSet = arrayListOf(
            "Him <sup>superscript</sup> rendered may <a href=\"www.google.com\">ourselves up otherwise my.</a>concerns jennings reserved now. Sympathize did now preference unpleasing mrs few. Mrs for hour game room want are fond dare. For detract charmed add talking age. Shy resolution instrument unreserved man few. She did open find pain some out. If we landlord stanhill mr whatever pleasure supplied concerns so. Exquisite by it admitting cordially september newspaper an. Acceptance middletons am it favourable. <font color=\"red\">Unpleasant nor diminution excellence</font>",
            "<a href=\"www.google.com\">ourselves up otherwise my.</a> <b>sincerity behaviour</b> to so do principle mr. As departure at no propriety zealously my. On dear rent if girl view. First on smart there he sense. Earnestly enjoyment her you resources. Brother chamber ten old against. Mr be cottage so related minuter is. Delicate say and blessing ladyship exertion few margaret. Delight herself welcome against smiling its for. Suspected discovery by he affection household of principle perfectly he.",
            "<font color=\"red\">Unpleasant nor diminution excellence</font> apartments imprudence the met new. Draw part them he an to he roof only. Music leave say doors him. Tore bred form if sigh case as do. Staying he no looking if do opinion. Sentiments way understood end partiality and his.",
            "<u>Style</u> never <i>The lightning</i> and those among gr eat. At no or september sportsmen he perfectly happiness attending. Depending listening delivered off new she procuring satisfied sex existence. Person plenty answer to exeter it if. Law use assistance especially resolution cultivated did out sentiments unsatiable. Way necessary had intention happiness but september delighted his curiosity. Furniture furnished or on strangers neglected remainder engrossed.",
            "Carried <img src=\"stack.jpg\" />nothing on am warrant towards. Polite in of in oh needed itself silent course. Assistance travelling so especially do prosperous appearance mr no celebrated. Wanted easily in my called formed suffer. Songs hoped sense as taken ye mirth at. Believe fat how six drawing pursuit minutes far. Same do seen head am part it dear open to. Whatever may scarcely judgment had.",
            "Promotion an ourselves <small>up otherwise my</small   >. High what each snug rich far yet easy. In companions inhabiting mr principles at insensible do. Heard their sex hoped enjoy vexed child for. Prosperous so occasional assistance it discovered especially no. Provision of he residence consisted up in remainder arranging described. Conveying has concealed necessary furnished bed zealously immediate get but. Terminated as middletons or by instrument. Bred do four so your felt with. No shameless principle dependent household do.",
            "<u>Style</u> never <i>The lightning</i> and those among great. At no or september sportsmen he perfectly happiness attending. Depending listening delivered off new she procuring satisfied sex existence. Person plenty answer to exeter it if. Law use assistance especially resolution cultivated did out sentiments unsatiable. Way necessary had intention happiness but september delighted his curiosity. Furniture furnished or on strangers neglected remainder engrossed.",
            "Carried <img src=\"stack.jpg\" />nothing on am warrant towards. Polite in of in oh needed itself silent course. Assistance travelling so especially do prosperous appearance mr no celebrated. Wanted easily in my called formed suffer. Songs hoped sense as taken ye mirth at. Believe fat how six drawing pursuit minutes far. Same do seen head am part it dear open to. Whatever may scarcely judgment had.",
            "Promotion an ourselves <small>up otherwise my</small   >. High what each snug rich far yet easy. In companions inhabiting mr principles at insensible do. Heard their sex hoped enjoy vexed child for. Prosperous so occasional assistance it discovered especially no. Provision of he residence consisted up in remainder arranging described. Conveying has concealed necessary furnished bed zealously immediate get but. Terminated as middletons or by instrument. Bred do four so your felt with. No shameless principle dependent household do.",
            "<u>Style</u> never <i>The lightning</i> and those among great. At no or september sportsmen he perfectly happiness attending. Depending listening delivered off new she procuring satisfied sex existence. Person plenty answer to exeter it if. Law use assistance especially resolution cultivated did out sentiments unsatiable. Way necessary had intention happiness but september delighted his curiosity. Furniture furnished or on strangers neglected remainder engrossed.",
            "Carried <img src=\"stack.jpg\" />nothing on am warrant towards. Polite in of in oh needed itself silent course. Assistance travelling so especially do prosperous appearance mr no celebrated. Wanted easily in my called formed suffer. Songs hoped sense as taken ye mirth at. Believe fat how six drawing pursuit minutes far. Same do seen head am part it dear open to. Whatever may scarcely judgment had.",
            "Promotion an ourselves <small>up otherwise my</small   >. High what each snug rich far yet easy. In companions inhabiting mr principles at insensible do. Heard their sex hoped enjoy vexed child for. Prosperous so occasional assistance it discovered especially no. Provision of he residence consisted up in remainder arranging described. Conveying has concealed necessary furnished bed zealously immediate get but. Terminated as middletons or by instrument. Bred do four so your felt with. No shameless principle dependent household do."

        )
        val realm = Realm.getDefaultInstance()
//        dataSet = getDataSet(
//            realm
//                .where(CodexEntity::class.java)
//                .limit(10)
//                .findAll()
//        )
        parent = findViewById(R.id.parent)
        recyclerView = findViewById(R.id.recycler_view)

        val chipsLayoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(CustomItemDecoration())
        recyclerView.layoutManager = chipsLayoutManager
//        generateListView(dataSet)
////        textFirst = findViewById(R.id.textView)
////        textSecond = findViewById(R.id.text_2)
////        concatBtn = findViewById(R.id.concat)
////        concatBtn!!.setOnClickListener {
////            concatenateTexts()
//        }

        parent!!.viewTreeObserver.addOnGlobalLayoutListener {

            SetupAdapter()
        }
    }

    private fun SetupAdapter() {
        var width = -1
        adapter?.let {
            width = it.parentWidth
        }
        if (width != parent!!.width) {
            width = parent!!.width
            adapter = SpannableMergeAdapter(width, dataSet)
            recyclerView.adapter = adapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.concatenate -> {
                concatenateTexts()
            }
        }
        return true
    }


    private fun concatenateTexts() {
        (0 until (parent as ViewGroup).childCount - 1).forEach { index ->
            val currentChild = (((parent as ViewGroup).getChildAt(index)) as TextView)
            val nextChild = (((parent as ViewGroup).getChildAt(index + 1)) as TextView)

            val text = currentChild.text
            val layout = currentChild.layout
            val paint = layout.paint
            var tempLayout: StaticLayout? = null
            var textLineHeight = paint.textSize

            when {
                SDK_INT >= M -> tempLayout = StaticLayout.Builder
                    .obtain(text, 0, text.length, layout.paint, layout.width)
                    .setAlignment(layout.alignment)
                    .setLineSpacing(currentChild.lineSpacingExtra, currentChild.lineSpacingMultiplier)
                    .setIncludePad(currentChild.includeFontPadding)
                    .setBreakStrategy(currentChild.breakStrategy)
                    .setHyphenationFrequency(currentChild.hyphenationFrequency)
                    .build()
                else -> if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tempLayout = StaticLayout(
                        text,
                        paint,
                        text.length,
                        layout.alignment,
                        currentChild.lineSpacingMultiplier,
                        currentChild.lineSpacingExtra,
                        currentChild.includeFontPadding
                    )
                }
            }
            val lineCount = tempLayout!!.lineCount
            val spannableString =
                Html.fromHtml(
                    dataSet[index + 1],
                    ImageGetter(this@MainActivity), null
                ) as SpannableStringBuilder
            Log.i("tag", dataSet[index + 1])
            if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                spannableString.setSpan(
                    TextSurroundSpan(
                        1,
                        (tempLayout.getLineRight(lineCount - 1) + convertDpToPixel(
                            currentChild.lineSpacingMultiplier
                        )).toInt()
                    ),
                    0,
                    spannableString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            Log.i("tag", "${tempLayout.getLineRight(lineCount - 1)}")
            nextChild.text = spannableString

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topMargin = -currentChild.lineHeight
                }
            }
            nextChild.layoutParams = params
        }
    }


    private fun generateListView(dataSet: List<String>) {
        dataSet.forEach {
            parent!!.addView(TextView(this)
                .apply {
                    text = Html.fromHtml(
                        it,
                        ImageGetter(this@MainActivity), null
                    )
                    includeFontPadding = false
                })
            Log.i("tag", it)
        }
    }

    private fun getDataSet(list: List<CodexEntity>): List<String> {
        return list
            .map { it ->
                "${it.NumberString}<sub>${it.Text}</sub>"
            }
    }
}

private class ImageGetter(var context: Context) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable? {
        val id: Int

        if (source == "stack.jpg") {
            id = R.drawable.stack
        } else {
            return null
        }

        val d = (context).resources.getDrawable(id)
        d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
        return d
    }
}



