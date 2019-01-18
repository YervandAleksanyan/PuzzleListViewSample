package com.example.yervand.puzzlelistviewsample.view.managers

import com.example.yervand.puzzlelistviewsample.db.model.CodexEntity
import com.example.yervand.puzzlelistviewsample.db.model.TextEntity
import io.realm.Realm
import java.util.*
import kotlin.collections.ArrayList

class TextEntityManager {
    private var items: ArrayList<TextEntity> = ArrayList()
    private val realm = Realm.getDefaultInstance()

    private val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    init {
        initializeItems()
    }

    fun getAll(): List<TextEntity> {
        return items
    }

    fun getTextEntitiesInSameParagraph(textEntityId: Int): List<TextEntity> {
        val result = ArrayList<TextEntity>()
        var index = textEntityId

        while (index >= 0) {
            val item = items[index]
            result.add(0, items[index--])
            if (item.isParagraphStart) break
        }

        index = textEntityId + 1
        while (index < items.count() && !items[index].isParagraphStart) {
            result.add(0, items[index++])
        }

        return result
    }

    private fun initializeItems() {
        val realmResults = realm
            .where(CodexEntity::class.java)
            .findAll()
        repeat(realmResults.size) {
            val paragraphPos = (it % 5 == 0)
            val color = if (paragraphPos) {
                "red"
            } else {
                "blue"
            }
            val codexEntity = realmResults[it]
            items.add(
                TextEntity(
                    it,
                    "<font color=\"$color\">${codexEntity?.NumberString}</font>${codexEntity?.Text}",
                    paragraphPos
                )
            )
        }
    }

    object GenerateRandomString {
        val DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var RANDOM = Random()

        fun randomString(len: Int): String {
            val sb = StringBuilder(len)

            for (i in 0 until len) {
                if (i == 0) {
                    sb.append("$<b>START</b>")
                }
                sb.append(
                    "${DATA[RANDOM.nextInt(
                        DATA.length
                    )]}"
                )
                if (i == len - 1) {
                    sb.append("<b>END</b>")
                }
            }

            return sb.toString()
        }
    }
}
