package com.example.yervand.puzzlelistviewsample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yervand.puzzlelistviewsample.R
import com.example.yervand.puzzlelistviewsample.view.managers.TextEntityManager
import com.example.yervand.puzzlelistviewsample.view.managers.TextLayoutManager
import io.realm.Realm


class MainActivity : AppCompatActivity() {

    private var textFirst: TextView? = null
    private var textSecond: TextView? = null
    private var concatBtn: TextView? = null
    private var parent: LinearLayout? = null
    private lateinit var recyclerView: RecyclerView
    private var adapter: SpannableMergeAdapter? = null
    private var textEntityManager = TextEntityManager()

    private lateinit var dataSet: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        recyclerView.addItemDecoration(CustomItemDecoration(textEntityManager.getAll()))
        recyclerView.layoutManager = chipsLayoutManager
        parent!!.viewTreeObserver.addOnGlobalLayoutListener {
            setupAdapter()
        }
    }

    private fun setupAdapter() {
        var width = -1
        adapter?.let {
            width = it.textLayoutManager.width
        }
        if (width != parent!!.width) {
            width = parent!!.width
            adapter = SpannableMergeAdapter(
                TextLayoutManager(
                    width,
                    textEntityManager
                )
            )
            recyclerView.adapter = adapter
        }
    }
}





